package com.dicoding.carvalappandroid.ui.reset

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.carvalappandroid.databinding.ActivityPassResetBinding
import com.dicoding.carvalappandroid.ui.login.LoginActivity
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

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        val email = intent.getStringExtra("emailReset")
        Log.d("Log", "Email : $email")

        binding.Pass.addTextChangedListener (object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //none
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //none
            }

            override fun afterTextChanged(p0: Editable?) {
                val password = p0.toString()

                if (p0 != null) {
                    if (!password.any { it.isDigit()}){
                        binding.Pass.error = "Password must contain at least 1 number"
                    }else if (!password.any{it.isUpperCase()}){
                        binding.Pass.error = "Password must contain at least 1 uppercase letter"
                    }
                }
            }

        })

        binding.newPass.addTextChangedListener (object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //none
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //none
            }

            override fun afterTextChanged(p0: Editable?) {
                val password = p0.toString()

                if (p0 != null) {
                    if (!password.any { it.isDigit()}){
                        binding.newPass.error = "Password must contain at least 1 number"
                    }else if (!password.any{it.isUpperCase()}){
                        binding.newPass.error = "Password must contain at least 1 uppercase letter"
                    }
                }
            }

        })


        binding.sendButton.setOnClickListener{
            if (email != null) {
                val password = binding.Pass.text.toString()
                val confirmPassword = binding.newPass.text.toString()
                viewModel.resetPassword(email, password, confirmPassword).observe(this){result->
                    when(result){
                        is Result.Success->{
                            Log.d("Log", "Message : ${result.data.message}")
                            showLoading(false)
                            Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                            val intentToMain = Intent(this, LoginActivity::class.java)
                            startActivity(intentToMain)
                        }

                        is Result.Error -> {
                            Log.d("Error", "Message : ${result.error}")
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                            showLoading(false)
                        }

                        is Result.Loading -> showLoading(true)
                    }
                }
            }
        }
    }

    private fun showLoading(it: Boolean?) {
        binding.progressBar.visibility = if (it==true) View.VISIBLE else View.GONE
    }
}