package com.example.taipeitour.utils

import com.example.taipeitour.common.BaseParams
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitUtils {

    companion object {
        val instance: RetrofitUtils by lazy { RetrofitUtils() }
    }

    fun <T> getService(clazz: Class<T>): T = retrofit.create(clazz)

    private val retrofit = Retrofit.Builder()
        .baseUrl(BaseParams.URL_RELEASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}