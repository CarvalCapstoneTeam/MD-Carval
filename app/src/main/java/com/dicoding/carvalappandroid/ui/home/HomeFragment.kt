package com.dicoding.carvalappandroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.FragmentArticleBinding
import com.dicoding.carvalappandroid.databinding.FragmentHomeBinding
import com.dicoding.carvalappandroid.setting.SettingsActivity
import com.dicoding.carvalappandroid.ui.article.ArticleAdapter
import com.dicoding.carvalappandroid.utils.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter : HomeFragmentAdapter
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity(), true)
    }
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HomeFragmentAdapter()
        binding.rvArticle.adapter = adapter

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.rvArticle.layoutManager = LinearLayoutManager(requireActivity())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvArticle.addItemDecoration(dividerItemDecoration)

        viewModel.getArticleUnlimited.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.getSession().observe(requireActivity()){session->
            if (_binding != null) {
                if (session != null) {
                    binding.tvWelcome.text = "Welcome, " + session.username
                } else {
                    binding.tvWelcome.text = "Welcome, Guest"
                }
            }
        }

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}