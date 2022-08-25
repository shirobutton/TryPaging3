package com.example.trypaging3

object ListRepository {
    suspend fun getList() = List(100){ CardData(it) }
}