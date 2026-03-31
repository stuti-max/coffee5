package com.example.coffee5.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.coffee5.Adapter.CategoryAdapter
import com.example.coffee5.Adapter.PopularAdapter
import com.example.coffee5.ViewModel.MainViewModel
import com.example.coffee5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanner()
        initCategory()
        initPopular()
    }

    private fun initPopular() {
        binding.progressBarpopular.visibility = View.VISIBLE
        viewModel.loadPopular().observe(this) {
            binding.recyclerViewpopular.layoutManager = GridLayoutManager(this, 2)
            binding.recyclerViewpopular.adapter = PopularAdapter(it)
            binding.progressBarpopular.visibility = View.GONE
        }
        viewModel.loadPopular()
    }

    private fun initCategory() {
        binding.progressBarcategory.visibility = View.VISIBLE
        viewModel.loadCategory().observe(this) {
            binding.categoryView.layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL, false
            )
            binding.categoryView.adapter = CategoryAdapter(it)
            binding.progressBarcategory.visibility = View.GONE
        }
        viewModel.loadCategory()
    }

    private fun initBanner() {
        binding.progressbarbanner.visibility = View.VISIBLE

        viewModel.loadBanner().observe(this) {
            if (it != null && it.isNotEmpty()) {
                Glide.with(this@MainActivity)
                    .load(it[0].url)
                    .into(binding.banner)
            }

            binding.progressbarbanner.visibility = View.GONE
        }

        viewModel.loadBanner()
    }
}