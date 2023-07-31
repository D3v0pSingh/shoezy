package com.example.shoezy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shoezy.R
import com.example.shoezy.adapter.CategoryAdapter
import com.example.shoezy.adapter.TrendingAdapter
import com.example.shoezy.databinding.FragmentHomeBinding
import com.example.shoezy.models.Product
import com.example.shoezy.models.categoryItems
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Home_Fragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter:CategoryAdapter
    private lateinit var trendingList:ArrayList<Product>
    private lateinit var list:ArrayList<categoryItems>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        settingSlider()

        trendingList = ArrayList()

        //setting category recycler view.
        list = ArrayList()
        list.add(categoryItems(R.drawable.pumaa,"puma"))
        list.add(categoryItems(R.drawable.adidas,"adidas"))
        list.add(categoryItems(R.drawable.nike,"nike"))
        list.add(categoryItems(R.drawable.reebok,"reebok"))

        adapter = CategoryAdapter(requireContext(),list)
        binding.categoryRecyclerView.adapter = adapter

        fetchTrending()

        val preference = requireContext().getSharedPreferences("cartInfo", AppCompatActivity.MODE_PRIVATE)

        if (preference.getBoolean("isCart", false)){
            findNavController().navigate(R.id.action_home_Fragment_to_cart_Fragment)
        }

        return binding.root
    }

    private fun fetchTrending() {
        Firebase.firestore.collection("Trending")
            .get().addOnSuccessListener {
                trendingList.clear()
                for (i in it.documents){
                    val data = i.toObject(Product::class.java)
                    trendingList.add(data!!)
                }
                binding.trendingRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
                binding.trendingRecyclerView.adapter = TrendingAdapter(requireContext(),trendingList)
            }
    }

    private fun settingSlider() {

        val imageSlider = binding.ImageSlider
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.adiads))
        imageList.add(SlideModel(R.drawable.nikeads))
        imageList.add(SlideModel(R.drawable.pumaads))
        imageList.add(SlideModel(R.drawable.reebokads))

        imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }

}