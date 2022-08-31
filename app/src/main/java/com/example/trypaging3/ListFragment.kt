package com.example.trypaging3

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.trypaging3.databinding.FragmentListBinding
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ListFragment : Fragment(R.layout.fragment_list) {

    private val viewModel: ListViewModel by viewModels()
    private var adapter: CardListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
        viewModel.listData
            .conflate()
            .onEach { adapter?.submitData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        adapter?.refresh()
        adapter = null
        super.onDestroyView()
    }

    private fun bindView(view: View) {
        val binding = FragmentListBinding.bind(view)
        val headerAdapter = LoadingProgressAdapter()
        val footerAdapter = LoadingProgressAdapter()
        val pagingAdapter = CardListAdapter()
        adapter = pagingAdapter

        binding.swipeRefreshLayout.setOnRefreshListener { pagingAdapter.refresh() }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = ConcatAdapter(headerAdapter, pagingAdapter, footerAdapter)
        pagingAdapter.loadStateFlow
            .onEach {
                when (it.refresh) {
                    is LoadState.Loading -> onRefreshLoading(binding, pagingAdapter)
                    is LoadState.NotLoading -> onRefreshSuccess(binding, pagingAdapter)
                    is LoadState.Error -> onRefreshError(binding, pagingAdapter)
                }
                if (it.prepend is LoadState.Error || it.append is LoadState.Error) {
                    headerAdapter.loadState = LoadState.NotLoading(false)
                    footerAdapter.loadState = LoadState.NotLoading(false)
                    return@onEach showErrorToast(binding)
                }
                headerAdapter.loadState = it.prepend
                footerAdapter.loadState = it.append
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onRefreshLoading(
        binding: FragmentListBinding,
        pagingAdapter: PagingDataAdapter<*, *>
    ) {
        if (isAdditionalLoading(pagingAdapter, binding.swipeRefreshLayout)) return
        binding.progressBar.isVisible = true
    }

    private fun onRefreshSuccess(
        binding: FragmentListBinding,
        pagingAdapter: PagingDataAdapter<*, *>
    ) {
        if (pagingAdapter.itemCount == 0) return showEmptyView(binding)
        showContents(binding)
    }

    private fun onRefreshError(
        binding: FragmentListBinding,
        pagingAdapter: PagingDataAdapter<*, *>
    ) {
        if (isAdditionalLoading(pagingAdapter, binding.swipeRefreshLayout)) return showErrorToast(binding)
        showErrorView(binding, "エラーしました。")
    }

    private fun showContents(binding: FragmentListBinding) {
        binding.swipeRefreshLayout.isRefreshing = false
        binding.contents.isVisible = true
        binding.progressBar.isVisible = false
        binding.errorGroup.isVisible = false
    }

    private fun showErrorView(binding: FragmentListBinding, errorMessage: String) {
        binding.swipeRefreshLayout.isRefreshing = false
        binding.contents.isVisible = false
        binding.progressBar.isVisible = false
        binding.errorGroup.isVisible = true
        binding.errorText.text = errorMessage
    }

    private fun showEmptyView(binding: FragmentListBinding) =
        showErrorView(binding, "リストが空です")

    private fun showErrorToast(binding: FragmentListBinding) {
        binding.swipeRefreshLayout.isRefreshing = false
        Toast.makeText(requireContext(), "エラーしました", Toast.LENGTH_LONG).show()
    }

    private fun isAdditionalLoading(
        pagingAdapter: PagingDataAdapter<*, *>,
        swipeRefreshLayout: SwipeRefreshLayout
    ) = pagingAdapter.itemCount != 0 || swipeRefreshLayout.isRefreshing
}
