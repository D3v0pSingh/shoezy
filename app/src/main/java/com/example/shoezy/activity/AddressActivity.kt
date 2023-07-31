package com.example.shoezy.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shoezy.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = this.getSharedPreferences("username", MODE_PRIVATE)
        val data = preferences.getString("email","email")

        Firebase.firestore.collection("usersProfile").document(data!!)
            .get().addOnSuccessListener {
                binding.usernameEditText.setText(it.getString("username"))
                binding.PhoneNumberET.setText(it.getString("phoneNumber"))
                binding.AddressET.setText(it.getString("address"))
                binding.cityET.setText(it.getString("city"))
                binding.stateET.setText(it.getString("state"))
                binding.pincodeET.setText(it.getString("pincode"))
            }

        binding.checkoutfromhere.setOnClickListener {
            validateData(binding.PhoneNumberET.text.toString(),binding.cityET.text.toString(),binding.AddressET.text.toString()
                ,binding.stateET.text.toString(),binding.pincodeET.text.toString())
        }
    }

    private fun validateData(phoneNumber: String, city: String, address: String, state: String, pincode: String) {
        if (phoneNumber.isEmpty()||city.isEmpty()|| address.isEmpty()|| state.isEmpty()||pincode.isEmpty()){
            Toast.makeText(this, "Please enter data in all the fields", Toast.LENGTH_SHORT).show()
        }else{
            storeData(phoneNumber,city,address,state,pincode)
        }
    }

    private fun storeData(phoneNumber: String, city: String, address: String, state: String, pincode: String) {

        val map = hashMapOf<String, Any>()
        map["phoneNumber"] = phoneNumber
        map["city"] = city
        map["address"] = address
        map["state"] = state
        map["pincode"] = pincode
        val preferences = this.getSharedPreferences("username", MODE_PRIVATE)
        val data = preferences.getString("email","email")
        Firebase.firestore.collection("usersProfile").document(data!!)
            .update(map).addOnSuccessListener {
                startActivity(Intent(this,CheckOutActivity::class.java))
            }.addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

}