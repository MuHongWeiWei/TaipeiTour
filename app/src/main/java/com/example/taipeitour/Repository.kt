package com.example.taipeitour

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.taipeitour.util.RetrofitUtils
import kotlinx.coroutines.flow.Flow

object Repository {

    private const val PAGE_SIZE = 20

    private val attractionsService = RetrofitUtils.instance.getService(AttractionsService::class.java)

    fun getPagingData(): Flow<PagingData<Attractions>> = Pager(
        config = PagingConfig(PAGE_SIZE),
        pagingSourceFactory = { AttractionsPagingSource(attractionsService) }
    ).flow

}