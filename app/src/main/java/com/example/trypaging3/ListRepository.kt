package com.example.trypaging3

import kotlinx.coroutines.delay

object ListRepository {

    fun getPagingSource(limit: Int) = ListPagingSource(limit, ::getList)

    private suspend fun getList(offset: Int, limit: Int) = kotlin.runCatching {
        delay(1000)
        List(limit) { CardData(offset + it) }
    }
}
