package com.dicoding.carvalappandroid

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.carvalappandroid.databinding.ActivityMainBinding
import com.dicoding.carvalappandroid.ui.login.LoginActivity
import com.dicoding.carvalappandroid.ui.onboarding.BoardingActivity
import com.dicoding.carvalappandroid.utils.NightMode
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.ViewModelFactory
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import java.util.Locale

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