package com.example.shoezy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shoezy.R
import com.example.shoezy.databinding.ActivityForgetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgetActivity : AppCompatActivity() {
    private lateinit var binding:ActivityForgetBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = Firebase.auth

        val email = binding.usernameEditText.text.toString()
        binding.appCompatButton.setOnClickListener { 
            auth.sendPasswordResetEmail(email).addOnCompleteListener { 
                if (it.isSuccessful){
                    Toast.makeText(this, "Check your email to reset the password", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,SignIn::class.java))
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}