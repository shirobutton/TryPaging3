package com.example.trypaging3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class ListViewModel : ViewModel() {

    val listData: Flow<PagingData<CardData>> = Pager(
        config = createPagingConfig(),
        pagingSourceFactory = { ListRepository.getPagingSource(ITEMS_PER_PAGE) }
    )
        .flow
        .cachedIn(viewModelScope)

    private fun createPagingConfig() =
        PagingConfig(
            pageSize = ITEMS_PER_PAGE,
            initialLoadSize = ITEMS_PER_PAGE
        )

    companion object {
        private const val ITEMS_PER_PAGE = 10
    }
}
