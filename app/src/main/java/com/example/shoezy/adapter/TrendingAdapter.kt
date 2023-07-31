package com.example.shoezy.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.shoezy.R
import com.example.shoezy.activity.product_Details_Activity
import com.example.shoezy.models.Product

class TrendingAdapter(val context: Context, val list:ArrayList<Product>):
    Adapter<TrendingAdapter.viewholder>() {

    inner class viewholder(itemView: View) : ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.trendingImage)
        val title: TextView = itemView.findViewById(R.id.trendingTitle)
        val price: TextView = itemView.findViewById(R.id.trendingPrice)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        return viewholder(LayoutInflater.from(context).inflate(R.layout.trendingitemslayout,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        Glide.with(context).load(list[position].productCoverImage).centerCrop().into(holder.image)
        holder.title.text = list[position].productName
        holder.price.text = list[position].productPrice

        holder.itemView.setOnClickListener {
            val intent = Intent(context, product_Details_Activity::class.java)
            intent.putExtra("prodId",list[position].productId)
            intent.putExtra("prodCategory",list[position].productCategory)
            intent.putExtra("trendingStat",list[position].trending)
            context.startActivity(intent)
        }


    }
}