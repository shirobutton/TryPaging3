package com.example.trypaging3

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardViewHolder(private val textView: TextView) : RecyclerView.ViewHolder(textView)  {


    fun bind(item: CardData) {
        textView.text = item.id.toString()
    }

    companion object {
        fun create(parent: ViewGroup): CardViewHolder {
            val textView = TextView(parent.context)
            textView.textSize = 60f
            return CardViewHolder(textView)
        }
    }
}