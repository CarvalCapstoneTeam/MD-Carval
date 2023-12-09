package com.dicoding.carvalappandroid.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.ui.onboarding.BoardingActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this, BoardingActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}