package com.example.taipeitour

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class HomeViewModel : ViewModel() {

    fun getPagingData(): Flow<PagingData<Attractions>> {
        return Repository.getPagingData().cachedIn(viewModelScope)
    }

}