package com.dicoding.carvalappandroid.ui.forgot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.ActivityForgotPassBinding
import com.dicoding.carvalappandroid.databinding.ActivityLoginBinding
import com.dicoding.carvalappandroid.ui.otp.OTPResetActivity
import com.dicoding.carvalappandroid.ui.reset.PassResetActivity
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.ViewModelFactory

class ForgotPassActivity : AppCompatActivity() {

    private lateinit var binding : ActivityForgotPassBinding
    private val viewModel by viewModels<ForgotPassViewModel> {
        ViewModelFactory.getInstance(this, false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendButton.setOnClickListener{
            val email = binding.email.text.toString()
            viewModel.getOTPReset(email).observe(this){result->
                when(result){
                    is Result.Success->{
                        Log.d("Log", "Message : ${result.data.message}")
                        Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                        val intentReset = Intent(this, OTPResetActivity::class.java)
                        intentReset.putExtra("emailReset", email)
                        startActivity(intentReset)
                    }

                    is Result.Error -> {
                        Log.d("Log", "Message : ${result.error}")
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }

                    is Result.Loading -> Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}