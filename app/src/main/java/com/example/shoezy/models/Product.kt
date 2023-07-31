package com.example.shoezy.models

data class Product(
    val productName:String?=null,
    val productDesc:String?=null,
    val productPrice:String?=null,
    val productCategory:String?=null,
    val productCoverImage:String?=null,
    val productId:String?=null,
    //val productSize:ArrayList<sizeDataSet>? = null,
    val productImagesList:ArrayList<String>? = null,
    val sellerName:String? = null,
    val trending:Boolean? = null
)
