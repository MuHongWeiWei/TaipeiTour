package com.example.taipeitour.module.home.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taipeitour.R
import com.example.taipeitour.databinding.FragmentHomeBinding
import com.example.taipeitour.delegate.binding
import com.example.taipeitour.module.home.paging.AttractionsAdapter
import com.example.taipeitour.module.home.paging.FooterAdapter
import com.example.taipeitour.module.home.viewModel.HomeViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by binding()
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var attractionsAdapter: AttractionsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attractionsAdapter = AttractionsAdapter(findNavController())

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter =
                attractionsAdapter.withLoadStateFooter(FooterAdapter { attractionsAdapter.retry() })
        }

        lifecycleScope.launch {
            viewModel.getPagingData().collect { pagingData ->
                attractionsAdapter.submitData(pagingData)
            }
        }

        attractionsAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                }
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                }
                is LoadState.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }

}