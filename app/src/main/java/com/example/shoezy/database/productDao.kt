package com.example.shoezy.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface productDao {

    @Insert
    suspend fun insertProduct(product : ProductModel)

    @Delete
    suspend fun deleteProduct(product: ProductModel)

    @Query("SELECT * FROM products")
    fun getAllProduct() : LiveData<List<ProductModel>>

    @Query("SELECT * FROM products WHERE productId = :id ")
    fun isExist(id:String):ProductModel
}