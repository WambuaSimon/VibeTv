package com.vibetv.core.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

internal class NetworkResponseAdapter<S : Any, E : Any>(
    private val responseType: Type,
    private val errorConverter: Converter<ResponseBody, E>,
) : CallAdapter<S, Call<NetworkResponse<S, E>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<S>): Call<NetworkResponse<S, E>> =
        NetworkResponseCall(call, errorConverter)
}
