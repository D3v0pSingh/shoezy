package com.example.shoezy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shoezy.MainActivity
import com.example.shoezy.R
import com.example.shoezy.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignIn : AppCompatActivity() {
    private lateinit var binding : ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.textView4.setOnClickListener {
            startActivity(Intent(this,ForgetActivity::class.java))
            finish()
        }

        binding.textView6.setOnClickListener {
            startActivity(Intent(this,signUp::class.java))
            finish()
        }

        auth = Firebase.auth

        binding.appCompatButton.setOnClickListener {
            val email = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            val preferences = this.getSharedPreferences("username", MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("email",email)
            editor.apply()

            if (email.isNotEmpty() && password.isNotEmpty()){
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                    }else{
                        Toast.makeText(this,"${it.exception?.message}",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}