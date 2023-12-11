package com.dicoding.carvalappandroid.ui.otp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.ActivityOtpactivityBinding
import com.dicoding.carvalappandroid.databinding.ActivityRegisterBinding
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.ViewModelFactory

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding
    private val viewModel by viewModels<OTPViewModel>{
        ViewModelFactory.getInstance(this,true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpactivity)

        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("email")
        val token = intent.getStringExtra("token")

        if (email != null) {
            Toast.makeText(this, email, Toast.LENGTH_SHORT).show()
            viewModel.verification(email).observe(this){result->
                when (result){
                    is Result.Loading->{
                        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Error ->{
                        Log.d("Log", "Message : ${result.error}")
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                    is Result.Success->{
                        Log.d("Log", "Message : ${result.data.message}")
                        Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }else{
            Toast.makeText(this, "Gagal mendapatkan email", Toast.LENGTH_SHORT).show()
        }

        binding.editTextDigit1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //not used
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //not used
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 1){
                    binding.editTextDigit2.requestFocus()
                }
            }
        })

        binding.editTextDigit2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //not used
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //not used
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 1){
                    binding.editTextDigit3.requestFocus()
                }
            }
        })

        binding.editTextDigit3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //not used
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //not used
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 1){
                    binding.editTextDigit4.requestFocus()
                }
            }
        })

    }
}