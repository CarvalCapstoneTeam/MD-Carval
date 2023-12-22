package com.dicoding.carvalappandroid.ui.otp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.carvalappandroid.MainActivity
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.ActivityOtpactivityBinding
import com.dicoding.carvalappandroid.databinding.ActivityRegisterBinding
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.ViewModelFactory

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding
    private val viewModel by viewModels<OTPViewModel> {
        ViewModelFactory.getInstance(this, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        val email = intent.getStringExtra("email")

        binding.tvDesc.text = "Masukkan 4 digit kode OTP telah dikirimkan ke \n" + email

        if (email != null) {
            viewModel.verification(email).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                    }

                    is Result.Error -> {
                        Log.d("Log", "Message : ${result.error}")
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }

                    is Result.Success -> {
                        Log.d("Log", "Message : ${result.data.message}")
                        Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Gagal mendapatkan email", Toast.LENGTH_SHORT).show()
        }

        binding.resend.setOnClickListener {
            if (email != null) {
                Toast.makeText(this, email, Toast.LENGTH_SHORT).show()
                viewModel.verification(email).observe(this) { result ->
                    when (result) {
                        is Result.Loading -> {
                            Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                        }

                        is Result.Error -> {
                            Log.d("Log", "Message : ${result.error}")
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }

                        is Result.Success -> {
                            Log.d("Log", "Message : ${result.data.message}")
                            Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Gagal mendapatkan email", Toast.LENGTH_SHORT).show()
            }
        }

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
                viewModel.sendOTP(email, digit).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Success -> {
                                Log.d("Log", "Message : ${result.data.message}")
                                showLoading(false)
                                viewModel.saveVerified()
                                val intentToMain = Intent(this, MainActivity::class.java)
                                startActivity(intentToMain)
                            }

                            is Result.Error -> {
                                showLoading(false)
                                Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                                Log.d("Log", "Error : ${result.error}")
                            }

                            is Result.Loading -> showLoading(true)
                        }
                    }
                }

            }
        }
    }

    private fun showLoading(it: Boolean?) {
        binding.progressBar.visibility = if (it==true) View.VISIBLE else View.GONE
    }
}