package com.dicoding.carvalappandroid.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.carvalappandroid.MainActivity
import com.dicoding.carvalappandroid.databinding.ActivityLoginBinding
import com.dicoding.carvalappandroid.ui.home.HomeFragment
import com.dicoding.carvalappandroid.ui.register.RegisterActivity
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.UserModel
import com.dicoding.carvalappandroid.utils.ViewModelFactory
import kotlinx.coroutines.MainScope


class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this, false)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.loginButton.setOnClickListener{
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            viewModel.login(email,password).observe(this){result->
                if (result!=null){
                    when (result){
                        is Result.Success ->{
                            showLoading(false)
                            val token = result.data.loginResult?.token
                            Log.d("Log", "Message : ${result.data.message}")
                            Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                            if (token != null){
                                Log.d("TokenLog", "Token: $token")
                                viewModel.saveSession(UserModel(email, token))
                            }
                            AlertDialog.Builder(this).apply {
                                setTitle("Mau Masuk?")
                                setMessage("Berhasil Login!")
                                setPositiveButton("Login"){_,_->
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                }
                                create()
                                show()
                            }

                        }
                        is Result.Error ->{
                            showLoading(false)
                            Log.d("Log", "Message : ${result.error}")
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }

                        is Result.Loading -> showLoading(true)
                    }
                }
            }
        }
        supportActionBar?.hide()

        binding.register.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(it: Boolean?) {
        binding.progressBar.visibility = if (it == true) View.VISIBLE else View.GONE
    }
}