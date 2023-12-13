package com.dicoding.carvalappandroid.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.dicoding.carvalappandroid.MainActivity
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.ActivityBoardingBinding
import com.dicoding.carvalappandroid.databinding.ActivitySplashScreenBinding
import com.dicoding.carvalappandroid.ui.onboarding.BoardingActivity
import com.dicoding.carvalappandroid.utils.ViewModelFactory

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel by viewModels<SplashScreenViewModel> {
        ViewModelFactory.getInstance(this, false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Handler().postDelayed({
            viewModel.getSession().observe(this){
                if(it.isLogin){
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }else{
                    val intent = Intent(this, BoardingActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 1000)
    }
}