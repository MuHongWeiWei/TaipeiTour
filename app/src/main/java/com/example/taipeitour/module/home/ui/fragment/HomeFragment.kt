package com.example.taipeitour.module.home.ui.fragment

import android.os.Bundle
import android.os.LocaleList
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taipeitour.R
import com.example.taipeitour.common.Language
import com.example.taipeitour.databinding.FragmentHomeBinding
import com.example.taipeitour.module.home.paging.AttractionsAdapter
import com.example.taipeitour.module.home.paging.FooterAdapter
import com.example.taipeitour.module.home.ui.activity.MainActivity
import com.example.taipeitour.module.home.viewModel.HomeViewModel
import com.example.taipeitour.utils.ActivityManage
import com.hjq.language.MultiLanguages
import kotlinx.coroutines.launch
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var attractionsAdapter: AttractionsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        attractionsAdapter = AttractionsAdapter(findNavController())

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter =
                attractionsAdapter.withLoadStateFooter(FooterAdapter { attractionsAdapter.retry() })
        }

        lifecycleScope.launch {
            val nowLanguage: String = if (MultiLanguages.getAppLanguage().language == "zh") {
                "zh-cn"
            } else {
                MultiLanguages.getAppLanguage().language
            }
            viewModel.getPagingData(nowLanguage).collect { pagingData ->
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

        toObserver()
    }

    private fun toObserver() {
        (activity as MainActivity).viewModel.language.observe(viewLifecycleOwner) { language ->
            if (language.lang == "簡體中文") {
                MultiLanguages.setAppLanguage(requireContext(), Locale.SIMPLIFIED_CHINESE)
            } else {
                MultiLanguages.setAppLanguage(requireContext(), Locale(language.symbol))
            }

            lifecycleScope.launch {
                viewModel.getPagingData(language.symbol).collect { pagingData ->
                    attractionsAdapter.submitData(pagingData)
                }
            }
        }
    }

}