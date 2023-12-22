package com.dicoding.carvalappandroid.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.carvalappandroid.MainActivity
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.ActivityLoginBinding
import com.dicoding.carvalappandroid.ui.forgot.ForgotPassActivity
import com.dicoding.carvalappandroid.ui.otp.OTPActivity
import com.dicoding.carvalappandroid.ui.register.RegisterActivity
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.UserModel
import com.dicoding.carvalappandroid.utils.ViewModelFactory
import com.google.android.material.button.MaterialButton


class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this, false)
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var customAlertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.forgotPassword.setOnClickListener {
            val intentForgot = Intent(this, ForgotPassActivity::class.java)
            startActivity(intentForgot)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()


            if (email.isEmpty()) {
                binding.emailLogin.error = "Email cannot be empty"
                binding.emailLogin.requestFocus()
            } else if (password.isEmpty()) {
                binding.passwordLogin.error = "Password cannot be empty"
                binding.emailLogin.requestFocus()
            } else {
                viewModel.login(email, password).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Success -> {
                                val overlayLayout = layoutInflater.inflate(R.layout.dim_background, null) as FrameLayout
                                showLoading(false)
                                val token = result.data.loginResult?.token
                                val name = result.data.loginResult?.name
                                Log.d("Log", "Message : ${result.data.message}")
                                if (token != null) {
                                    Log.d("TokenLog", "Token: $token")
                                    viewModel.saveSession(UserModel(email, name.toString(), token))
                                }
                                if (result.data.loginResult?.emailVerifiedAt == null) {
                                    val builder = AlertDialog.Builder(this)
                                    val customAlertDialogView = View.inflate(this, R.layout.layout_login, null)
                                    val customVerifyButton = customAlertDialogView.findViewById<MaterialButton>(R.id.btn_verify_login)
                                    val customContinueButton = customAlertDialogView.findViewById<MaterialButton>(R.id.btn_continue)
                                    customVerifyButton.setOnClickListener{
                                        val intent = Intent(this, OTPActivity::class.java)
                                        intent.putExtra("token", result.data.loginResult?.token)
                                        intent.putExtra("email", result.data.loginResult?.email)
                                        startActivity(intent)
                                    }
                                    customContinueButton.setOnClickListener{
                                        val intent = Intent(this, MainActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(intent)
                                    }
                                    builder.setView(customAlertDialogView)
                                    customAlertDialog = builder.create()
                                    (window.decorView as ViewGroup).addView(overlayLayout)
                                    customAlertDialog.window?.setDimAmount(0.8f)
                                    if(!isFinishing && !isDestroyed){
                                        customAlertDialog.show()
                                    }
                                    customAlertDialog.setOnDismissListener {
                                        if(!isFinishing)(window.decorView as ViewGroup).removeView(overlayLayout)
                                    }
                                } else {
                                    viewModel.saveVerified()
                                    val inflater = LayoutInflater.from(this)
                                    val builder = AlertDialog.Builder(this)
                                    val customAlertDialogView = inflater.inflate(R.layout.layout_login, null)
                                    val customVerifyButton = customAlertDialogView.findViewById<MaterialButton>(R.id.btn_verify_login)
                                    val customDesc = customAlertDialogView.findViewById<TextView>(R.id.login_desc)
                                    val customContinueButton = customAlertDialogView.findViewById<MaterialButton>(R.id.btn_continue)
                                    customDesc.text = "Login Successfull! Press Continue \nto go into the app"
                                    customVerifyButton.text = "Continue"
                                    customContinueButton.visibility = View.GONE
                                    customVerifyButton.setOnClickListener{
                                        val intent = Intent(this, MainActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(intent)
                                    }
                                    builder.setView(customAlertDialogView)
                                    customAlertDialog = builder.create()
                                    (window.decorView as ViewGroup).addView(overlayLayout)
                                    if (!isFinishing){
                                        customAlertDialog.show()
                                    }
                                    customAlertDialog.setOnDismissListener {
                                        (window.decorView as ViewGroup).removeView(overlayLayout)
                                    }
                                }
                            }

                            is Result.Error -> {
                                showLoading(false)
                                Toast.makeText(this, "Wrong Email or Password", Toast.LENGTH_SHORT).show()
                                Log.d("Log", "Message : ${result.error}")
                            }

                            is Result.Loading -> showLoading(true)
                        }
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