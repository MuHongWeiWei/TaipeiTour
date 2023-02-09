package com.example.taipeitour.module.home.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taipeitour.module.home.paging.AttractionsAdapter
import com.example.taipeitour.network.api.AttractionsService
import com.example.taipeitour.module.home.viewModel.HomeViewModel
import com.example.taipeitour.R
import com.example.taipeitour.databinding.FragmentHomeBinding
import com.example.taipeitour.delegate.binding
import com.example.taipeitour.utils.RetrofitUtils
import kotlinx.coroutines.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by binding()
    private val viewModel: HomeViewModel by viewModels()
    private val attractionsAdapter = AttractionsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = attractionsAdapter
        }

        lifecycleScope.launch {
            viewModel.getPagingData().collect { pagingData ->
                attractionsAdapter.submitData(pagingData)
            }
        }
    }
}