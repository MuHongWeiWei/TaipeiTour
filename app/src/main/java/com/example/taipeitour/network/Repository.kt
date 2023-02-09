package com.example.taipeitour.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.taipeitour.Attractions
import com.example.taipeitour.module.home.paging.AttractionsPagingSource
import com.example.taipeitour.network.api.AttractionsService
import com.example.taipeitour.utils.RetrofitUtils
import kotlinx.coroutines.flow.Flow

object Repository {

    private const val PAGE_SIZE = 20

    private val attractionsService = RetrofitUtils.instance.getService(AttractionsService::class.java)

    fun getPagingData(): Flow<PagingData<Attractions>> = Pager(
        config = PagingConfig(PAGE_SIZE),
        pagingSourceFactory = { AttractionsPagingSource(attractionsService) }
    ).flow

}