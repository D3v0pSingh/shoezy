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
import com.example.shoezy.activity.CategoryActivity
import com.example.shoezy.R
import com.example.shoezy.models.categoryItems

class CategoryAdapter(val context: Context, val List:ArrayList<categoryItems>):
    Adapter<CategoryAdapter.viewholder>() {

    inner class viewholder(itemView: View):ViewHolder(itemView){
        val image:ImageView = itemView.findViewById(R.id.ImageView)
        val text:TextView = itemView.findViewById(R.id.catTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        return viewholder(LayoutInflater.from(context).inflate(R.layout.itemslayoutcategory,parent,false))
    }

    override fun getItemCount(): Int {
        return List.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.image.setImageResource(List[position].img)
        holder.text.text = List[position].cat
        holder.itemView.setOnClickListener {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category",List[position].cat)
            context.startActivity(intent)
        }
    }
}