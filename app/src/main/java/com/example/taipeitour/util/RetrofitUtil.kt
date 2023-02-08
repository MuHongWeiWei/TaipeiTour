package com.example.taipeitour.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitUtil {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.travel.taipei/open-api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    companion object {
        val instance : RetrofitUtil by lazy { RetrofitUtil() }
    }

    fun <T> getService(clazz: Class<T>) = retrofit.create(clazz)

}