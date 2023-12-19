package com.dicoding.carvalappandroid.ui.home

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.FragmentArticleBinding
import com.dicoding.carvalappandroid.databinding.FragmentHomeBinding
import com.dicoding.carvalappandroid.response.HomeDataItem
import com.dicoding.carvalappandroid.setting.SettingsActivity
import com.dicoding.carvalappandroid.ui.article.ArticleAdapter
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.ViewModelFactory
import com.dicoding.carvalappandroid.utils.ViewModelFactoryHome

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter : HomeFragmentAdapter
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactoryHome.getInstanceHome(requireActivity(), true)
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
        binding.rvArticle.layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvArticle.addItemDecoration(dividerItemDecoration)
        binding.rvArticle.scrollToPosition(5)



        viewModel.getArticle()

        viewModel.getData().observe(viewLifecycleOwner){result->
            adapter.submitList(result)
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