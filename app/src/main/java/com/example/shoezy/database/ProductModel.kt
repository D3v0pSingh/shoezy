package com.example.shoezy.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "products")
data class ProductModel(
    @PrimaryKey
    @Nonnull
    val productId:String,
    @ColumnInfo(name = "prodName")
    val productName: String?,
    @ColumnInfo(name = "prodImage")
    val productImage: String?,
    @ColumnInfo(name = "prodPrice")
    val productPrice: String?


)
