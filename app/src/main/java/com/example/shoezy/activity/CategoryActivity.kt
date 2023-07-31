package com.example.shoezy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoezy.adapter.ProductAdapter
import com.example.shoezy.databinding.ActivityCategoryBinding
import com.example.shoezy.models.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getContent(intent.getStringExtra("category"))

    }

    private fun getContent(catName: String?) {

        val list = ArrayList<Product>()
        Firebase.firestore.collection(catName!!)
            .get().addOnSuccessListener {
                list.clear()
                for (i in it.documents){
                    val data = i.toObject(Product::class.java)
                    list.add(data!!)
                }
                binding.RcyclerView.adapter = ProductAdapter(this,list)
            }

    }
}