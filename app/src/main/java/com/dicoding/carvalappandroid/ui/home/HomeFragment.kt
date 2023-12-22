package com.dicoding.carvalappandroid.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.FragmentHomeBinding
import com.dicoding.carvalappandroid.utils.Result
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

        binding.cardForm.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }


        viewModel.getArticle().observe(viewLifecycleOwner) {result->
            when(result){
                is Result.Loading ->{
                    showLoading(true)
                    Log.d("Loading", "Currently Loading" )
                }

                is Result.Success -> {
                    showLoading(false)
                    adapter.submitList(result.data)
                }

                is  Result.Error -> {
                    showLoading(false)
                    Log.d("ErrorArticle", "Error : ${result.error}")
                }
            }
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

    private fun showLoading(it: Boolean?) {
        binding.progressBar.visibility = if (it==true) View.VISIBLE else View.GONE
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}