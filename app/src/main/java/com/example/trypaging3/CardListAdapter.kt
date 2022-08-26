package com.example.trypaging3

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

class CardListAdapter : PagingDataAdapter<CardData, CardViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CardViewHolder.create(parent)

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CardData>() {
            override fun areItemsTheSame(
                oldItem: CardData,
                newItem: CardData
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CardData,
                newItem: CardData
            ): Boolean = oldItem == newItem
        }
    }
}
