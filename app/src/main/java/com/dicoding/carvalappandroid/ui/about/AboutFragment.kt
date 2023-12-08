package com.dicoding.carvalappandroid.ui.about

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.dicoding.carvalappandroid.databinding.FragmentAboutBinding
import com.dicoding.carvalappandroid.databinding.FragmentArticleBinding
import com.dicoding.carvalappandroid.setting.SettingsActivity
import com.dicoding.carvalappandroid.ui.onboarding.BoardingActivity
import com.dicoding.carvalappandroid.utils.ViewModelFactory

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val viewModel by viewModels<AboutViewModel> {
        ViewModelFactory.getInstance(requireActivity(), false)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        binding.btnSetting.setOnClickListener{
            val intent = Intent(requireActivity(), SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener{
            val intent = Intent(requireActivity(), BoardingActivity::class.java)
            startActivity(intent)
            viewModel.logout()
        }

//        val textView: TextView = binding.textDashboard
//        aboutViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}