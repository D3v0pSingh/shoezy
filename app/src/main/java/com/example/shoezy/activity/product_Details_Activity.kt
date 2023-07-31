package com.example.shoezy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView.ScaleType
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shoezy.MainActivity
import com.example.shoezy.adapter.sizeAdapter
import com.example.shoezy.database.AppDatabase
import com.example.shoezy.database.ProductModel
import com.example.shoezy.database.productDao
import com.example.shoezy.databinding.ActivityProductDetailsBinding
import com.example.shoezy.models.Product
import com.example.shoezy.models.sizeDataSet
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.DatabaseMetaData


class product_Details_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getStringExtra("prodId")
        val productCategory = intent.getStringExtra("prodCategory")
        val trends = intent.getBooleanExtra("trendingStat",false)

        getProductDetails(productId, productCategory, trends)
    }

    private fun getProductDetails(productId: String?, productCategory: String?, trends: Boolean) {

        if (trends){
            Firebase.firestore.collection("Trending")
                .document(productId!!).get().addOnSuccessListener {
                    val list = it.get("productImagesList") as ArrayList<String>
                    val name = it.getString("productName")
                    val price = it.getString("productPrice")
                    val image = it.getString("productCoverImage")
                    val id = it.getString("productId")
                    binding.shoesName.text = it.getString("productName")
                    binding.description.text =it.getString("productDesc")
                    binding.priceTag.text = it.getString("productPrice")

                    val slideList = ArrayList<SlideModel>()
                    for (data in list){
                        slideList.add(SlideModel(data, ScaleTypes.CENTER_CROP))
                    }

                    cartAction(id,name,price,image)

                    binding.ImageSlider.setImageList(slideList)
                }.addOnFailureListener {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
        }else{
            Firebase.firestore.collection(productCategory!!)
                .document(productId!!).get().addOnSuccessListener {
                    val list = it.get("productImagesList") as ArrayList<String>
                    val name = it.getString("productName")
                    val price = it.getString("productPrice")
                    val image = it.getString("productCoverImage")
                    val id = it.getString("productId")
                    binding.shoesName.text = it.getString("productName")
                    binding.description.text =it.getString("productDesc")
                    binding.priceTag.text = it.getString("productPrice")

                    val slideList = ArrayList<SlideModel>()
                    for (data in list){
                        slideList.add(SlideModel(data, ScaleTypes.CENTER_CROP))
                    }
                    binding.ImageSlider.setImageList(slideList)

                    cartAction(id,name,price,image)

                }.addOnFailureListener {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }

        }


    }

    private fun cartAction(id: String?, name: String?, price: String?, image: String?) {

        val prodDao = AppDatabase.getInstance(this).dao()

        if (prodDao.isExist(id!!) != null){
            binding.cartButton.text = "Go to Cart"
            binding.cartButton.backgroundTintList = getColorStateList(android.R.color.holo_green_light)
        }else{
            binding.cartButton.text = "Add to Cart"
        }

        binding.cartButton.setOnClickListener {
            if (prodDao.isExist(id!!) != null){
                openCart()
            }else{
                addToCart(prodDao,name,id,image,price)
            }
        }

    }

    private fun addToCart(prodDao: productDao, name: String?, id: String?, image: String?, price: String?) {

        val data = ProductModel(id!!,name,image,price)

        lifecycleScope.launch(Dispatchers.IO) {
            prodDao.insertProduct(data)
            binding.cartButton.text = "Go to Cart"
            binding.cartButton.backgroundTintList = getColorStateList(android.R.color.holo_green_light)
        }

    }

    private fun openCart() {

        val preference = this.getSharedPreferences("cartInfo", MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart", true)
        editor.apply()

        startActivity(Intent(this,MainActivity::class.java))
        finish()

    }
}