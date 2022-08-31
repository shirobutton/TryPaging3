package com.example.trypaging3

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

class LoadingProgressAdapter : LoadStateAdapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, loadState: LoadState) {
        holder.itemView.isVisible = loadState is LoadState.Loading
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RecyclerView.ViewHolder {
        val progressBar = ProgressBar(parent.context)
        progressBar.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        return object : RecyclerView.ViewHolder(progressBar) {}
    }
}
