package com.dicoding.carvalappandroid.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.ActivityRegisterBinding
import com.dicoding.carvalappandroid.ui.login.LoginActivity
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this, false)
    }

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.password.addTextChangedListener (object: TextWatcher{
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
                        binding.password.error = "Password must contain at least 1 number"
                    }else if (!password.any{it.isUpperCase()}){
                        binding.password.error = "Password must contain at least 1 uppercase letter"
                    }
                }
            }

        })

        binding.confirmPassword.addTextChangedListener (object: TextWatcher{
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
                        binding.confirmPassword.error = "Password must contain at least 1 number"
                    }else if (!password.any{it.isUpperCase()}){
                        binding.confirmPassword.error = "Password must contain at least 1 uppercase letter"
                    }
                }
            }

        })

        binding.registerButton.setOnClickListener{
            val name = binding.name.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.password.text.toString()
            val password2 = binding.confirmPassword.text.toString()
            when{
                email.isEmpty() -> {
                    binding.etEmail.error = "Email cannot be empty"
                    binding.etEmail.requestFocus()
                }
                name.isEmpty() -> {
                    binding.name.error = "Name cannot be empty"
                    binding.name.requestFocus()
                }
                password.isEmpty() -> {
                    binding.password.error = "Password cannot be empty"
                    binding.password.requestFocus()
                }
                password2.isEmpty() -> {
                    binding.confirmPassword.error = "Confirm Password cannot be empty"
                    binding.confirmPassword.requestFocus()
                }
                password != password2 -> binding.confirmPassword.error = "Password and Confirm Password doesn't match"
                else ->{
                    viewModel.register(name, email, password, password2).observe(this){result->
                        if (result!=null){
                            when(result){
                                is Result.Success->{
                                    showLoading(false)
                                    Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, LoginActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                }

                                is Result.Error -> {
                                    showLoading(false)
                                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                                }
                                is Result.Loading -> showLoading(true)
                            }
                        }

                    }
                }
            }
        }

        binding.login.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(it: Boolean?) {
        binding.progressBar.visibility = if (it==true) View.VISIBLE else View.GONE
    }
}