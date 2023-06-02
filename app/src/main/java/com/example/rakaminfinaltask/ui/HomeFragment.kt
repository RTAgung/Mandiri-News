package com.example.rakaminfinaltask.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rakaminfinaltask.databinding.FragmentHomeBinding
import com.example.rakaminfinaltask.viewmodel.ViewModelFactory
import com.example.rakaminfinaltask.data.Result
import com.example.rakaminfinaltask.data.local.entity.HeadlinesNewsEntity
import com.example.rakaminfinaltask.data.local.entity.NewsEntity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: MainViewModel by viewModels {
            factory
        }

        generateHeadlinesView(viewModel)
        generateNewsView(viewModel)
    }

    private fun generateHeadlinesView(viewModel: MainViewModel) {
        val headlinesAdapter = HeadlinesAdapter()
        viewModel.getHeadlineNews().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(isLoading = true, isHeadline = true)
                    }
                    is Result.Success -> {
                        showLoading(isLoading = false, isHeadline = true)
                        val headlinesData = ArrayList<HeadlinesNewsEntity>()
                        headlinesData.addAll(result.data)
                        headlinesAdapter.headlinesList = headlinesData
                        Log.d(
                            HomeFragment::class.java.simpleName,
                            "generateHeadlinesView: $headlinesData[0]"
                        )
                    }
                    is Result.Error -> {
                        showLoading(isLoading = false, isHeadline = true)
                        Toast.makeText(context, "Oops, An error occur!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        binding?.rvHeadlines?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = headlinesAdapter
        }
    }

    private fun generateNewsView(viewModel: MainViewModel) {
        val newsAdapter = NewsAdapter()
        viewModel.getAllNews().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(isLoading = true, isHeadline = false)
                    }
                    is Result.Success -> {
                        showLoading(isLoading = false, isHeadline = false)
                        val allData = ArrayList<NewsEntity>()
                        allData.addAll(result.data)
                        newsAdapter.newsList = allData
                        Log.d(HomeFragment::class.java.simpleName, "generateNewsView: $allData[0]")
                    }
                    is Result.Error -> {
                        showLoading(isLoading = false, isHeadline = false)
                        Toast.makeText(context, "Oops, An error occur!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        binding?.rvNews?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    private fun showLoading(isLoading: Boolean, isHeadline: Boolean) {
        if (isHeadline) {
            if (isLoading) {
                binding?.loadingHeadlines?.visibility = View.VISIBLE
                binding?.rvHeadlines?.visibility = View.GONE
            } else {
                binding?.loadingHeadlines?.visibility = View.GONE
                binding?.rvHeadlines?.visibility = View.VISIBLE
            }
        } else {
            if (isLoading) {
                binding?.loadingNews?.visibility = View.VISIBLE
                binding?.rvNews?.visibility = View.GONE
            } else {
                binding?.loadingNews?.visibility = View.GONE
                binding?.rvNews?.visibility = View.VISIBLE
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}