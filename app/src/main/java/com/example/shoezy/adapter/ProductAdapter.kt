package com.example.shoezy.adapter

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.shoezy.R
import com.example.shoezy.activity.product_Details_Activity
import com.example.shoezy.models.Product

class ProductAdapter(val context: Context, val list:ArrayList<Product>):
    Adapter<ProductAdapter.viewholder>() {

    inner class viewholder(itemView:View): RecyclerView.ViewHolder(itemView) {
        val Image: ImageView = itemView.findViewById(R.id.ImageViewproduct)
        val text1: TextView = itemView.findViewById(R.id.Titletext)
        val text2: TextView = itemView.findViewById(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        return viewholder(LayoutInflater.from(context).inflate(R.layout.product_list,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        Glide.with(context).load(list[position].productCoverImage).centerCrop().into(holder.Image)
        holder.text1.text = list[position].productCategory
        holder.text2.text = list[position].productPrice

        holder.itemView.setOnClickListener {
            val intent = Intent(context,product_Details_Activity::class.java)
            intent.putExtra("prodId",list[position].productId)
            intent.putExtra("prodCategory",list[position].productCategory)
            intent.putExtra("trendingStat",list[position].trending)
            context.startActivity(intent)
        }
    }
}