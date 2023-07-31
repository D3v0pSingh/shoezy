package com.example.shoezy.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoezy.R
import com.example.shoezy.activity.AddressActivity
import com.example.shoezy.adapter.cartAdapter
import com.example.shoezy.database.AppDatabase
import com.example.shoezy.database.ProductModel
import com.example.shoezy.databinding.FragmentCartBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class cart_Fragment : Fragment(),cartAdapter.onClick {

    private lateinit var binding: FragmentCartBinding
    var list = ArrayList<String>()
    var sum = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)

        //This preference is taken to prevent the issue of directly cart fragment getting launched when the app is launched.
        val preference =
            requireContext().getSharedPreferences("cartInfo", AppCompatActivity.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart", false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).dao()

        dao.getAllProduct().observe(requireActivity()) {

            //here result is used to change the price of the total whenever the user increases or decreases the product
            binding.RcyclerView.adapter = cartAdapter(requireContext(), it) { result ->
                sum += result!!.toInt()
                binding.subtotalprice.text = sum.toString()

            }

            //this for loop is to get all the price of the products present in the adapter.
            //using it which is list that is fetched from livedata.
//            for (i in it) {
//                sum += i.productPrice!!.toInt()
//            }
//            binding.subtotalprice.text = sum.toString()
        }
        binding.checkoutbtn.setOnClickListener {
            if (binding.subtotalprice.text == "0" || binding.subtotalprice.text == "100"){
                Toast.makeText(requireContext(), "Please add number of quantity", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(requireContext(),AddressActivity::class.java)
                startActivity(intent)
            }
        }

        return binding.root

    }

    override fun deleteFunc(position: Int) {
        val dao = AppDatabase.getInstance(requireContext()).dao()
        GlobalScope.launch(Dispatchers.IO) {
                dao.deleteProduct(ProductModel(list[position].productId,list[position].productName,
                    list[position].productImage,list[position].productPrice))
            }
    }
}