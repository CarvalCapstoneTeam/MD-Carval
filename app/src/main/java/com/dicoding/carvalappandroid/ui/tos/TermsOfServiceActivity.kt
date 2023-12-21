package com.dicoding.carvalappandroid.ui.tos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.ActivityLoginBinding
import com.dicoding.carvalappandroid.databinding.ActivityTermsOfServiceBinding

class TermsOfServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTermsOfServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsOfServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibBack.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }
}