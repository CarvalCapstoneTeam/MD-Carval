package com.dicoding.carvalappandroid.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.ActivityBoardingBinding
import com.dicoding.carvalappandroid.databinding.ActivityLoginBinding
import com.dicoding.carvalappandroid.databinding.ActivityRegisterBinding
import com.dicoding.carvalappandroid.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.registerButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}