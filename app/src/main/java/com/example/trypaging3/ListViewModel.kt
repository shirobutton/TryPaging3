package com.example.trypaging3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ListViewModel: ViewModel() {
    private val mutableListData = MutableLiveData<List<CardData>>()
    val listData: LiveData<List<CardData>> = mutableListData

    fun fetch() {
        viewModelScope.launch {
            mutableListData.value = ListRepository.getList()
        }
    }
}