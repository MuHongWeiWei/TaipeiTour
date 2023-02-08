package com.example.taipeitour.util

import com.example.taipeitour.BuildConfig
import com.example.taipeitour.network.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitUtils {

    companion object {
        private const val DEFAULT_TIMEOUT = 30L
        val instance: RetrofitUtils by lazy { RetrofitUtils() }
    }

    fun <T> getService(clazz: Class<T>): T = retrofit.create(clazz)

    private fun getClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        return OkHttpClient.Builder()
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .retryOnConnectionFailure(true).build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://data.taipei/api/v1/dataset/")
        .client(getClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}