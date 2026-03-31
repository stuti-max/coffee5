package com.example.coffee5.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffee5.domain.BannerModel
import com.example.coffee5.domain.CategoryModel
import com.example.coffee5.domain.ItemsModel
import com.google.firebase.database.*

class MainRepository {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun loadBanner(): LiveData<MutableList<BannerModel>> {

        val listData = MutableLiveData<MutableList<BannerModel>>()
        val ref = firebaseDatabase.getReference("banner")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val list = mutableListOf<BannerModel>()

                for (childSnapshot in p0.children) {
                    val item = childSnapshot.getValue(BannerModel::class.java)
                    item?.let { list.add(it) }
                }

                listData.value = list
            }

            override fun onCancelled(p0: DatabaseError) {
                // removed TODO to avoid error
            }
        })

        return listData
    }
    fun loadCategory(): LiveData<MutableList<CategoryModel>> {

        val listData = MutableLiveData<MutableList<CategoryModel>>()
        val ref = firebaseDatabase.getReference("Category")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val list = mutableListOf<CategoryModel>()

                for (childSnapshot in p0.children) {
                    val item = childSnapshot.getValue(CategoryModel::class.java)
                    item?.let { list.add(it) }
                }

                listData.value = list
            }

            override fun onCancelled(p0: DatabaseError) {
                // removed TODO to avoid error
            }
        })

        return listData
    }


    fun loadPopular(): LiveData<MutableList<ItemsModel>> {

        val listData = MutableLiveData<MutableList<ItemsModel>>()
        val ref = firebaseDatabase.getReference("Popular")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val list = mutableListOf<ItemsModel>()

                for (childSnapshot in p0.children) {
                    val item = childSnapshot.getValue(ItemsModel::class.java)
                    item?.let { list.add(it) }
                }

                listData.value = list
            }

            override fun onCancelled(p0: DatabaseError) {
                // removed TODO to avoid error
            }
        })

        return listData
    }

    fun loadItemCategory(categoryId: String): LiveData<MutableList<ItemsModel>> {

        val itemsLiveData=MutableLiveData<MutableList<ItemsModel>>()
        val ref=firebaseDatabase.getReference("Items")

        val query=ref.orderByChild("categoryId").equalTo(categoryId)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val list = mutableListOf<ItemsModel>()

                for (childSnapshot in p0.children) {
                    val item = childSnapshot.getValue(ItemsModel::class.java)
                    item?.let { list.add(it) }
                }
                itemsLiveData.value = list
            }

            override fun onCancelled(p0: DatabaseError) {
                //removed TODO to avoid error
            }
        })
        return itemsLiveData
        }



}
