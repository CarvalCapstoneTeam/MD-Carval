package com.dicoding.carvalappandroid.setting

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.utils.NightMode
import java.util.Locale

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if(savedInstanceState==null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
//        supportActionBar?.hide()
//        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home->{
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat(){
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val prefDark = findPreference<ListPreference>(getString(R.string.pref_key_dark))
            prefDark?.setOnPreferenceChangeListener{_, newVal->
                val theme = NightMode.valueOf(newVal.toString().uppercase(Locale.US))
                updateTheme(theme.value)
                true
            }
        }

        private fun updateTheme(value: Int) : Boolean {
            AppCompatDelegate.setDefaultNightMode(value)
            requireActivity().recreate()
            return true
        }

    }
}