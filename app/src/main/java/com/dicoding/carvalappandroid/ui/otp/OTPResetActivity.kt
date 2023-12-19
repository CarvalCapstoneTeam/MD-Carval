package com.dicoding.carvalappandroid.ui.otp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.carvalappandroid.MainActivity
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.ActivityForgotPassBinding
import com.dicoding.carvalappandroid.databinding.ActivityLoginBinding
import com.dicoding.carvalappandroid.databinding.ActivityOtpresetBinding
import com.dicoding.carvalappandroid.ui.forgot.ForgotPassViewModel
import com.dicoding.carvalappandroid.ui.reset.PassResetActivity
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.ViewModelFactory

class OTPResetActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOtpresetBinding
    private val viewModel by viewModels<OTPResetViewModel> {
        ViewModelFactory.getInstance(this, false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpresetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("emailReset")
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show()
        binding.tvDesc.text = "Masukkan 4 digit kode OTP telah dikirimkan ke \n" + email

        binding.editTextDigit1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //not used
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //not used
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 1) {
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
                if (p0?.length == 1) {
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
                if (p0?.length == 1) {
                    binding.editTextDigit4.requestFocus()
                }
            }
        })

        binding.editTextDigit4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //not used
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //not used
            }

            override fun afterTextChanged(p0: Editable?) {
                //not used
            }
        })

        binding.verifyButton.setOnClickListener {
            val digit1 = binding.editTextDigit1.text
            val digit2 = binding.editTextDigit2.text
            val digit3 = binding.editTextDigit3.text
            val digit4 = binding.editTextDigit4.text
            val digit = "$digit1$digit2$digit3$digit4"
            if (email != null) {
                viewModel.sendOtpReset(email, digit).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Success -> {
                                Log.d("Log", "Message : ${result.data.message}")
                                Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                                val intentReset = Intent(this, PassResetActivity::class.java)
                                intentReset.putExtra("emailReset", email)
                                startActivity(intentReset)
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
}