package com.dicoding.carvalappandroid.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.ActivityBoardingBinding
import com.dicoding.carvalappandroid.databinding.ActivityLoginBinding
import com.dicoding.carvalappandroid.ui.login.LoginActivity

class BoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boarding)
        binding = ActivityBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}