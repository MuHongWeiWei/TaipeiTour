package com.example.taipeitour

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

class AttractionsPagingSource(private val attractionsService: AttractionsService) :
    PagingSource<Int, Attractions>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Attractions> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val attractionsRec = attractionsService.searchAttractions(page, pageSize)
            val attractions = attractionsRec.data
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (attractions.isNotEmpty()) page + 1 else null
            LoadResult.Page(attractions, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Attractions>): Int? = null
}