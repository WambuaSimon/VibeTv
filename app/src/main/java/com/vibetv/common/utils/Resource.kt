package com.vibetv.common.utils

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import com.vibetv.R
import com.vibetv.core.api.VibeCloudResponse
import com.vibetv.core.api.VibeTVErrorDetail
import com.vibetv.core.network.NetworkResponse
import com.vibetv.core.network.NoNetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException

sealed class Resource<out T> {
    abstract val result: T?

    object Loading : Resource<Nothing>() {
        override val result: Nothing? = null
    }

    data class Success<out T : Any>(override val result: T, val loading: Boolean = false) :
        Resource<T>()

    data class Error<out T : Any>(
        val throwable: Throwable? = null,
        override val result: T? = null,
        @StringRes private val stringRes: Int? = null,
        private val formatArgs: List<Any>? = null,
    ) : Resource<T>() {
        init {
            when (throwable) {
                null, is NoNetworkException -> Unit
                else -> Log.e(
                    "A resource failed with a throwable: $throwable",
                    throwable.toString()
                )
            }
        }

        fun errorMessage(context: Context): String? =
            when (stringRes) {
                null -> when (throwable) {
                    is NoNetworkException -> context.getString(R.string.error_no_internet)
                    is HttpException -> throwable.convertErrorBody<VibeTVErrorDetail>()?.status_message
                    else -> null
                }

                else -> when (formatArgs) {
                    null -> context.getString(stringRes)
                    else -> context.getString(stringRes, *formatArgs.toTypedArray())
                }
            }

        fun <R : Any> mapError(transform: (T) -> R): Error<R> =
            Error(throwable, result?.let { transform(it) }, stringRes, formatArgs)
    }

    fun <R : Any> map(transform: (T) -> R): Resource<R> = when (this) {
        Loading -> Loading
        is Success -> Success(transform(result), loading)
        is Error -> mapError(transform)
    }
}

val <T : Any> T.resource: Resource.Success<T>
    get() = Resource.Success(this)

internal fun <T : Any> VibeCloudResponse<T>.resource(emptyValue: () -> T): Resource<T> =
    when (this) {
        NetworkResponse.Empty -> Resource.Success(emptyValue())
        is NetworkResponse.Success -> Resource.Success(body)
        is NetworkResponse.Error -> {
            val error = when (this) {
                is NetworkResponse.Error.NetworkError -> error
                is NetworkResponse.Error.UnknownError -> error
            }
            Resource.Error(error)
        }
    }

val json = Json { ignoreUnknownKeys = true }

inline fun <reified T> HttpException.convertErrorBody(): T? {
    return response()?.errorBody()?.source()?.readString(Charsets.UTF_8)?.runCatching {
        json.decodeFromString<T>(this)
    }?.getOrNull()
}

val <T : Any> Result<T>.resource: Resource<T>
    get() = fold(
        onSuccess = { Resource.Success(it) },
        onFailure = { Resource.Error(it) },
    )

fun <T : Any> Flow<T>.catchResource(): Flow<Resource<T>> =
    map<T, Resource<T>> { it.resource }
        .catch {
            emit(Resource.Error(it))
        }

inline fun <T : Any, R> Flow<Resource<T>>.mapSuccessOrThrow(crossinline transform: (T) -> R) =
    mapNotNull {
        when (it) {
            Resource.Loading -> null
            is Resource.Success -> transform(it.result)
            is Resource.Error ->
                throw it.throwable
                    ?: Exception("${Resource::class.simpleName} failed without a throwable")
        }
    }
