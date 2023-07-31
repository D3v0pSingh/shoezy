package com.example.shoezy.adapter

import android.content.ComponentCallbacks
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.shoezy.R
import com.example.shoezy.database.AppDatabase
import com.example.shoezy.database.ProductModel
import com.example.shoezy.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class cartAdapter(val context : Context, val list: List<ProductModel>,
val listener:onClick, val myCallback: (result: String?) -> Unit):Adapter<cartAdapter.viewholder>() {
//To know about myCallback read about lambda functions and take a look in the bottom for explanation

    inner class viewholder(itemView:View): ViewHolder(itemView){
        val price:TextView=itemView.findViewById(R.id.price)
        val title:TextView=itemView.findViewById(R.id.Titletext)
        val image:ImageView=itemView.findViewById(R.id.ImageView10)
        val addbtn :Button = itemView.findViewById(R.id.add)
        val minus :Button = itemView.findViewById(R.id.minus)
        val qty: TextView = itemView.findViewById(R.id.qty)
        var count:Int = 0
        val delete:ImageView = itemView.findViewById(R.id.delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        return viewholder(LayoutInflater.from(context).inflate(R.layout.cart_items,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        Glide.with(context).load(list[position].productImage).into(holder.image)
        holder.price.text = list[position].productPrice
        holder.title.text = list[position].productName
        val dao = AppDatabase.getInstance(context).dao()
        holder.delete.setOnClickListener {

            listener.deleteFunc(position)
//            GlobalScope.launch(Dispatchers.IO) {
//                dao.deleteProduct(ProductModel(list[position].productId,list[position].productName,
//                    list[position].productImage,list[position].productPrice))
//            }
//            val pr = list[position].productPrice!!.toInt()
//            //val prr = holder.count*pr
//            myCallback.invoke((-pr).toString())

        }
        holder.addbtn.setOnClickListener {
            holder.count += 1
            val qty = holder.count.toString()
            holder.qty.text = qty
            myCallback.invoke(list[position].productPrice)
        }

        holder.minus.setOnClickListener {
            holder.count -= 1
            val qty = holder.count.toString()
            holder.qty.text = qty
            val prices = list[position].productPrice!!.toInt()
            myCallback.invoke((-prices).toString())
        }

//        holder.addbtn.setOnClickListener {
//            holder.count += 1
//            holder.qty.text = holder.count.toString()
//            if(holder.count == 1) {
//                holder.price.text =
//                    (holder.count * list[position].productPrice!!.toInt()).toString()
//                myCallback.invoke(holder.price.text.toString())
//            }else{
//                holder.price.text =
//                    (holder.count * list[position].productPrice!!.toInt()/holder.count).toString()
//                myCallback.invoke(holder.price.text.toString())
//            }
//
//        }
//
//        holder.minus.setOnClickListener {
//            holder.click += 1
//            if (holder.count == 0){
//                holder.count = 0
//            }else{
//                holder.count -= 1
//                holder.qty.text = holder.count.toString()
//                val price = holder.click * (-list[position].productPrice!!.toInt()/holder.click)
////                holder.price.text =
////                    (holder.click * (-list[position].productPrice!!.toInt()/holder.click)).toString()
//                myCallback.invoke(price.toString())
//            }
//
//        }

    }

    interface onClick{
        fun deleteFunc(position: Int)
    }



}
//1. I required the price to be fetched from recyclerView to my cart_Fragment
//2. TO do this I have called myCallback lambda function which returns String
//3. Whenever the add or minus button is clicked the COUNT variable increases or decreases accordingly.
//4. COUNT variable is inside viewholder so that for each viewGroup there will be their own count value.
//5. Now inside ADD BUTTON clicklistener multiplying COUNT to the PRICE and then changing the PRICE accordingly.
//6. Now using MYCALLBACK variable which has "invoke" functionality sent the PRICE which is increased or decreased to the fragment
//7. Inside each click listener there is if block which tells if the count is 1 price taken will be as it is.
//8. In else block when count is greater than 1 price taken will be divided by the current count.
//9. Example in count 1 price of item1 was rs10 and price of item2 was rs 5 total is 15, now in count 2 price is rs 20 and rs 10
// now total is 15+ the new price 20 and 10 so istead of total as 30 I get total as 45 bec
//7. In Fragment have taken a global variable i.e. SUM = 0
//8. then setting the SUM to the SUBTOTAL textbox.