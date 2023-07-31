package com.example.shoezy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoezy.R
import com.example.shoezy.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.nextBtn.setOnClickListener {
            startActivity(Intent(this,SignIn::class.java))
        }
    }
}