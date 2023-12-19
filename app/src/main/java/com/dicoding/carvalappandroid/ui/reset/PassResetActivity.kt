package com.dicoding.carvalappandroid.ui.reset

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.ActivityForgotPassBinding
import com.dicoding.carvalappandroid.databinding.ActivityOtpresetBinding
import com.dicoding.carvalappandroid.databinding.ActivityPassResetBinding
import com.dicoding.carvalappandroid.ui.login.LoginActivity
import com.dicoding.carvalappandroid.ui.otp.OTPResetViewModel
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.ViewModelFactory

class PassResetActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPassResetBinding
    private val viewModel by viewModels<PassResetViewModel> {
        ViewModelFactory.getInstance(this, false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassResetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("emailReset")
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show()
        Log.d("Log", "Email : $email")

        binding.sendButton.setOnClickListener{
            if (email != null) {
                val password = binding.Pass.text.toString()
                val confirmPassword = binding.newPass.text.toString()
                viewModel.resetPassword(email, password, confirmPassword).observe(this){result->
                    when(result){
                        is Result.Success->{
                            Log.d("Log", "Message : ${result.data.message}")
                            Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                            val intentToMain = Intent(this, LoginActivity::class.java)
                            startActivity(intentToMain)
                        }

                        is Result.Error -> {
                            Toast.makeText(
                                this,
                                result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Result.Loading -> Toast.makeText(this, "Loading", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}