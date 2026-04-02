package com.example.coffee5.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.coffee5.Adapter.CategoryAdapter
import com.example.coffee5.Adapter.PopularAdapter
import com.example.coffee5.R
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
        initBottomMenu()
    }

    private fun initBottomMenu() {
        binding.cartbtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun initPopular() {
        binding.progressBarpopular.visibility = View.VISIBLE

        viewModel.loadPopular().observe(this) {
            binding.recyclerViewpopular.layoutManager = GridLayoutManager(this, 2)
            binding.recyclerViewpopular.adapter = PopularAdapter(it)
            binding.progressBarpopular.visibility = View.GONE
        }
    }

    private fun initCategory() {
        binding.progressBarcategory.visibility = View.VISIBLE

        viewModel.loadCategory().observe(this) {
            binding.categoryView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.categoryView.adapter = CategoryAdapter(it)
            binding.progressBarcategory.visibility = View.GONE
        }
    }

    private fun initBanner() {
        binding.progressbarbanner.visibility = View.VISIBLE

        viewModel.loadBanner().observe(this) { list ->

            Log.d("BANNER_DEBUG", "Full list = $list")

            if (!list.isNullOrEmpty()) {

                try {
                    val imageUrl = list[0].url   // we will confirm this

                    Log.d("BANNER_DEBUG", "URL = $imageUrl")

                    Glide.with(this@MainActivity)
                        .load(imageUrl)
                        .into(binding.banner)

                } catch (e: Exception) {
                    Log.e("BANNER_DEBUG", "ERROR: ${e.message}")
                }

            } else {
                Log.d("BANNER_DEBUG", "List is EMPTY")
            }

            binding.progressbarbanner.visibility = View.GONE
        }
    }
}