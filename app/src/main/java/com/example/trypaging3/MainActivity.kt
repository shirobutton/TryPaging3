package com.example.trypaging3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trypaging3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = CardListAdapter()
        binding.root.adapter = adapter
        binding.root.layoutManager = LinearLayoutManager(this)
        adapter.submitList(createData())
    }

    private fun createData() =  List(100){CardData(it)}
}