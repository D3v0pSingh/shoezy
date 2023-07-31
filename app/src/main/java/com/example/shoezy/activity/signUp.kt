package com.example.shoezy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.shoezy.R
import com.example.shoezy.databinding.ActivitySignUpBinding
import com.example.shoezy.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class signUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = Firebase.auth

        binding.appCompatButton.setOnClickListener {
            validataData()
        }
    }

    private fun validataData() {
        if (binding.fullnameEditText.text.isEmpty() || binding.usernameEditText.text.isEmpty()
            ||binding.passwordEditText.text.isEmpty()||binding.confpasswordEditText.text.isEmpty()){
            Toast.makeText(this, "Please fill all the details!", Toast.LENGTH_SHORT).show()
        }else if (binding.passwordEditText.text.isEmpty()!=binding.confpasswordEditText.text.isEmpty()){
            Toast.makeText(this, "Password is not matching.Please re-Enter.", Toast.LENGTH_SHORT).show()
        }else{
            storeData()
            authenticate()
        }
    }

    private fun storeData() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Loading....")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
        builder.show()

        val db = Firebase.firestore

        val data = User(binding.usernameEditText.text.toString(), binding.fullnameEditText.text.toString())
        db.collection("usersProfile").document(binding.usernameEditText.text.toString()).set(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Seller details saved", Toast.LENGTH_SHORT).show()
                builder.dismiss()
                startActivity(Intent(this,SignIn::class.java))
            }.addOnFailureListener {
                builder.dismiss()
                Toast.makeText(this, "Something went Wrong!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun authenticate() {
        val username = binding.usernameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        auth.createUserWithEmailAndPassword(username,password).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}