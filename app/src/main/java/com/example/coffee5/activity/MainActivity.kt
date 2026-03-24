package com.example.coffee5.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.coffee5.Adapter.CategoryAdapter
import com.example.coffee5.R
import com.example.coffee5.ViewModel.MainViewModel
import com.example.coffee5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val ViewModel= MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanner()
        initCategory()
    }

    private fun initCategory() {
        binding.progressBarcategory.visibility= View.VISIBLE
        ViewModel.loadCategory().observeForever {
            binding.categoryView
                .layoutManager= LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL,false
            )
            binding.categoryView.adapter= CategoryAdapter(it)
            binding.progressBarcategory.visibility=View.GONE
        }
        ViewModel.loadCategory()
    }

    private fun initBanner() {
        binding.progressbarbanner.visibility = View.VISIBLE
        ViewModel.loadBanner().observeForever {
            Glide.with(this@MainActivity)
                .load(it[0].url)
                .into(binding.banner)
            binding.progressbarbanner.visibility = View.GONE
        }
    }
}