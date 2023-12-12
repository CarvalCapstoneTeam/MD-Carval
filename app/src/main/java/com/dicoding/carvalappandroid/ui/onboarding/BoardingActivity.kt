package com.dicoding.carvalappandroid.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dicoding.carvalappandroid.MainActivity
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.ActivityBoardingBinding
import com.dicoding.carvalappandroid.databinding.ActivityLoginBinding
import com.dicoding.carvalappandroid.ui.login.LoginActivity
import com.dicoding.carvalappandroid.utils.ViewModelFactory

class BoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardingBinding
    private val viewModel by viewModels<BoardingViewModel> {
        ViewModelFactory.getInstance(this, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this){
            if(it.isLogin){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}