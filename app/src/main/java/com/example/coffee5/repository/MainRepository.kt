package com.example.coffee5.repository

import android.util.Log
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
        val ref = firebaseDatabase.reference.child("Banner")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                val list = mutableListOf<BannerModel>()

                Log.d("BANNER_DEBUG", "Snapshot exists: ${snapshot.exists()}")

                for (childSnapshot in snapshot.children) {

                    // Try to read BOTH "url" and "image"
                    val url = childSnapshot.child("url").getValue(String::class.java)
                    val image = childSnapshot.child("image").getValue(String::class.java)

                    val finalUrl = url ?: image

                    Log.d("BANNER_DEBUG", "url = $url , image = $image")

                    if (!finalUrl.isNullOrEmpty()) {
                        list.add(BannerModel(finalUrl))
                    }
                }

                Log.d("BANNER_DEBUG", "Final list size = ${list.size}")

                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("BANNER_DEBUG", "Error: ${error.message}")
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

            override fun onCancelled(p0: DatabaseError) {}
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

            override fun onCancelled(p0: DatabaseError) {}
        })

        return listData
    }

    fun loadItemCategory(categoryId: String): LiveData<MutableList<ItemsModel>> {

        val itemsLiveData = MutableLiveData<MutableList<ItemsModel>>()
        val ref = firebaseDatabase.getReference("Items")

        val query = ref.orderByChild("categoryId").equalTo(categoryId)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val list = mutableListOf<ItemsModel>()

                for (childSnapshot in p0.children) {
                    val item = childSnapshot.getValue(ItemsModel::class.java)
                    item?.let { list.add(it) }
                }

                itemsLiveData.value = list
            }

            override fun onCancelled(p0: DatabaseError) {}
        })

        return itemsLiveData
    }
}