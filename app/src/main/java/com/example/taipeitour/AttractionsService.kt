package com.example.taipeitour

import retrofit2.http.GET
import retrofit2.http.Query

interface AttractionsService {

    @GET("zh-tw/Attractions/All?page=1")
    suspend fun searchAttractions(@Query("page") page: Int, @Query("per_page") perPage: Int) : AttractionsRec

}