package com.dicoding.carvalappandroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.FragmentHomeBinding
import com.dicoding.carvalappandroid.setting.SettingsActivity
import com.dicoding.carvalappandroid.utils.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity(), true)
    }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.getSession().observe(requireActivity()){session->
            if (_binding != null) {
                if (session != null) {
                    binding.tvWelcome.text = "Welcome, " + session.username
                } else {
                    binding.tvWelcome.text = "Welcome, Guest"
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}