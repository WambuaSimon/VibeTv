package com.vibetv.core.network

import java.io.IOException

sealed class NetworkResponse<out SuccessBody : Any, out ErrorBody : Any> {
    object Empty : NetworkResponse<Nothing, Nothing>()

    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) :
        NetworkResponse<T, Nothing>()

    sealed class Error<out ErrorBody : Any> : NetworkResponse<Nothing, ErrorBody>() {
        /**
         * Failure response with body
         */

        /**
         * Network error
         */
        data class NetworkError(val error: IOException) : Error<Nothing>()

        /**
         * For example, json parsing error
         */
        data class UnknownError(val error: Throwable?) : Error<Nothing>()
    }

    fun <R : Any> map(transform: (SuccessBody) -> R): NetworkResponse<R, ErrorBody> {
        return when (this) {
            Empty -> Empty
            is Error.NetworkError -> Error.NetworkError(error)
            is Error.UnknownError -> Error.UnknownError(error)
            is Success -> Success(transform(body))
        }
    }
}
