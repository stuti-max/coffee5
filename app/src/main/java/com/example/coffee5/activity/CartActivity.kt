package com.example.coffee5.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffee5.Adapter.CartAdapter
import com.example.coffee5.Helper.ChangeNumberItemsListener
import com.example.coffee5.Helper.ManagmentCart
import com.example.coffee5.R
import com.example.coffee5.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    lateinit var binding: ActivityCartBinding
    lateinit var managmentCart: ManagmentCart
    private var tax: Double=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart= ManagmentCart(this)


        calculateCart()
        setVariable()
        intitAdapter()



    }

    private fun intitAdapter() {
        binding.apply {
            listView.layoutManager=
                LinearLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL,false)
            listView.adapter= CartAdapter(
                managmentCart.getListCart(),
                this@CartActivity,
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        calculateCart()
                    }

                })
        }

    }


    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }

    }

    private fun calculateCart() {
        val percentTax=0.02
        val delivery=15
        tax=((managmentCart.getTotalFee()*percentTax)*100/100.0)
        val total=((managmentCart.getTotalFee()+tax+delivery)*100)/100
        val itemtotal=((managmentCart.getTotalFee()+tax+delivery)*100)/100
        binding.apply {
            totalFeeTxt.text="$$itemtotal"
            totalTaxTxt.text="$$tax"
            deliveryTxt.text="$$delivery"
            totalTxt.text="$$total"
        }
    }
        }





