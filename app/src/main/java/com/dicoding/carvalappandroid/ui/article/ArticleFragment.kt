package com.dicoding.carvalappandroid.ui.article

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.carvalappandroid.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val articleViewModel =
            ViewModelProvider(this).get(ArticleViewModel::class.java)

        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        articleViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    @SuppressLint("RestrictedApi")
    override fun onResume() {
        super.onResume()
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.let {
            it.setShowHideAnimationEnabled(false)
            it.hide()
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onPause() {
        super.onPause()
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.let {
            it.setShowHideAnimationEnabled(false)
            it.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}