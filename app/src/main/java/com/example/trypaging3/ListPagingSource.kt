package com.example.trypaging3

import androidx.paging.PagingSource
import androidx.paging.PagingState

class ListPagingSource(
    private val limit: Int,
    private val getPagingData: suspend (offset: Int, limit: Int) -> Result<List<CardData>>
) : PagingSource<Int, CardData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CardData> {
        val offset = params.key ?: 0
        val listData = getPagingData(offset, limit)
            .getOrElse { return LoadResult.Error(it) }
        val prevKey = if (offset <= 0) null else offset - limit
        val nextKey = if (listData.isEmpty()) null else offset + limit
        val itemsAfter = if (nextKey == null) 0 else limit
        return LoadResult.Page(
            data = listData,
            prevKey = prevKey,
            nextKey = nextKey,
            itemsBefore = offset,
            itemsAfter = itemsAfter
        )
    }

    override fun getRefreshKey(state: PagingState<Int, CardData>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        return state.closestPageToPosition(anchorPosition)?.let {
            it.prevKey?.plus(limit) ?: it.nextKey?.minus(limit)
        }
    }
}
