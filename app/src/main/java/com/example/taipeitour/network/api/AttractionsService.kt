package com.example.taipeitour.network.api

import com.example.taipeitour.module.home.dataModel.recive.AttractionsRec
import retrofit2.http.*

interface AttractionsService {

    @GET("{lang}/Attractions/All")
    @Headers("Accept: application/json")
    suspend fun searchAttractions(@Query("lang") lang: String, @Query("page") page: Int, @Query("per_page") perPage: Int) : AttractionsRec

}