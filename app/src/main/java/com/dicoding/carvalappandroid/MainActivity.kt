package com.dicoding.carvalappandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.carvalappandroid.databinding.ActivityMainBinding
import com.dicoding.carvalappandroid.ui.login.LoginActivity
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
//        preferences.getString(
//            getString(R.string.pref_key_dark),
//            getString(R.string.pref_dark_follow_system)
//        )?.apply {
//            val mode = NightMode.valueOf(this.uppercase(Locale.US))
//            AppCompatDelegate.setDefaultNightMode(mode.value)
//        }

        val navView: BottomNavigationView = binding.navView

        viewModel.checkToken().observe(this){result->
            when(result){
                is Result.Success->{
                    Log.d("Log", "Message : ${result.data.message}")
                }

                is Result.Error -> {
                    Log.d("Log", "Message : ${result.error}")
                    val intentToLogin = Intent(this, LoginActivity::class.java)
                    startActivity(intentToLogin)
                    viewModel.logout()
                }

                is Result.Loading -> {
                    Log.d("Log", "Checking Token")
                }
            }
        }


        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }
}