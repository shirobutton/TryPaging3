package com.example.trypaging3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.commit
import com.example.trypaging3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.commit {
            replace(R.id.fragment, ListFragment())
        }
        binding.fragmentCount.text = "0"
        binding.fragmentPushButton.setOnClickListener {
            val count =  supportFragmentManager.backStackEntryCount + 1
            binding.fragmentCount.text = count.toString()
            supportFragmentManager.commit {
                addToBackStack(count.toString())
                replace(R.id.fragment, ListFragment())
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.fragmentCount.text = supportFragmentManager.backStackEntryCount.toString()
    }
}