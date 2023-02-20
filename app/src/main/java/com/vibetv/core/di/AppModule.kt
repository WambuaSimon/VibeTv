package com.vibetv.core.di

import com.vibetv.common.Constants
import com.vibetv.core.api.VibeApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.vibetv.core.network.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(Interceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url
            // INTERESTING PART to inject query parameters
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", Constants.API_KEY)
                .build()

            // Request customization: add request headers
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        })

        return httpClient.build()
    }
    @Suppress("JSON_FORMAT_REDUNDANT")
    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideSerialization(): Converter.Factory {
        return Json { ignoreUnknownKeys = true }
            .asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return provideRetrofitHelper(client, converterFactory)
    }

    private fun provideRetrofitHelper(
        client: OkHttpClient,
        converter: Converter.Factory,
    ):Retrofit{
        return Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(converter)
            .build()
    }

    @Provides
    @Singleton
    fun provideAPI(retrofit: Retrofit): VibeApi {
        return retrofit.create()
    }


}