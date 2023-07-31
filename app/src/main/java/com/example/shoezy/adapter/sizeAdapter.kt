package com.example.shoezy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoezy.R
import com.example.shoezy.models.sizeDataSet

class sizeAdapter(val context:Context , val list:ArrayList<sizeDataSet>):Adapter<sizeAdapter.viewholder>() {

    inner class viewholder(itemView:View): ViewHolder(itemView){
        val text : TextView = itemView.findViewById(R.id.textView34)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        return viewholder(LayoutInflater.from(context).inflate(R.layout.sizelayout,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
       holder.text.text = list[position].num
    }

}