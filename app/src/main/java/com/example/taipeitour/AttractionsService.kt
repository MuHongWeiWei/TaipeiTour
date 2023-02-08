package com.example.taipeitour

import retrofit2.http.GET
import retrofit2.http.Query

interface AttractionsService {

    @GET("zh-tw/Attractions/All")
    suspend fun searchAttractions(@Query("page") page: Int, @Query("per_page") perPage: Int) : AttractionsRec

    @GET("zh-tw/Attractions/All")
    suspend fun getAttractions() : AttractionsRec

    @GET("739db798-e3e5-4687-a761-1e464f201f18?scope=resourceAquire")
    suspend fun aa() : AARec

}