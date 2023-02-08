package com.example.taipeitour

import retrofit2.http.*

interface AttractionsService {

    @GET("zh-tw/Attractions/All")
    @Headers("Accept: application/json")
    suspend fun searchAttractions(@Query("page") page: Int, @Query("per_page") perPage: Int) : AttractionsRec

    @GET("zh-tw/Attractions/All")
    @Headers("Accept: application/json")
    suspend fun getAttractions() : AttractionsRec

}