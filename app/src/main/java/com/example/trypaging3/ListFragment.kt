package com.example.trypaging3

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trypaging3.databinding.FragmentListBinding


class ListFragment: Fragment(R.layout.fragment_list) {

    private val viewModel: ListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentListBinding.bind(view)
        val adapter = CardListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.listData.observe(viewLifecycleOwner) {
            it?.let(adapter::submitList)
        }
        viewModel.fetch()
    }
}