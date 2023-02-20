package com.vibetv.core.network

import android.util.Log
import com.vibetv.common.utils.Resource
import com.vibetv.common.utils.Resource.Success
import com.vibetv.core.api.VibeCloudResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

//https://github.com/Carrieukie/Candy-Network-Bound-Resource

@OptIn(ExperimentalCoroutinesApi::class)
internal inline fun <Local: Any, Remote : Any> networkBoundResource(
    crossinline fetch: suspend () -> VibeCloudResponse<Remote>,
    networkStatus: Flow<NetworkStatus>,
    crossinline saveRemoteData: suspend (Remote) -> Unit = { },
    crossinline query: () -> Flow<Local?>,
    crossinline onFetchFailed: (error: Throwable?) -> Unit = { },
): Flow<Resource<Local>> = flow {
    emit(Resource.Loading)

    val localData = query().firstOrNull()

    if (localData != null) {
        emit(Success(localData, loading = true))
    }

    val fetchFlow: Flow<Resource<Local>> = flow {
        val flow = when (val apiResponse = fetch()) {
            is NetworkResponse.Success -> {
                saveRemoteData(apiResponse.body)
                query().filterNotNull().map(::Success)

            }

            NetworkResponse.Empty -> {
                query().filterNotNull().map(::Success)
            }

            is NetworkResponse.Error -> {
                val error = when (apiResponse) {
                    is NetworkResponse.Error.NetworkError -> apiResponse.error
                    is NetworkResponse.Error.UnknownError -> apiResponse.error
                }
                onFetchFailed(error)
                query().map {
                    Resource.Error(error, it)
                }
            }

        }

        emitAll(flow)
    }
    emitAll(
        networkStatus
            .flatMapLatest { networkStatus ->
                when (networkStatus) {
                    NetworkStatus.Available -> fetchFlow
                    NetworkStatus.Unavailable -> query().map {
                        Resource.Error(NoNetworkException(), it)
                    }
                }
            },
    )


}.distinctUntilChanged()