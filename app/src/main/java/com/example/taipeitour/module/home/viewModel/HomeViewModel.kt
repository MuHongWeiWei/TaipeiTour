package com.example.taipeitour.module.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.taipeitour.module.home.dataModel.recive.Attractions
import com.example.taipeitour.network.Repository
import kotlinx.coroutines.flow.Flow

class HomeViewModel : ViewModel() {

    fun getPagingData(lang: String): Flow<PagingData<Attractions>> {
        return Repository.getPagingData(lang).cachedIn(viewModelScope)
    }

}