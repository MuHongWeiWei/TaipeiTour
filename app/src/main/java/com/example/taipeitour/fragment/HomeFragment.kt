package com.example.taipeitour.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taipeitour.AttractionsAdapter
import com.example.taipeitour.AttractionsService
import com.example.taipeitour.HomeViewModel
import com.example.taipeitour.R
import com.example.taipeitour.databinding.FragmentHomeBinding
import com.example.taipeitour.util.RetrofitUtils
import kotlinx.coroutines.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val attractionsAdapter = AttractionsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = attractionsAdapter
        }

        lifecycleScope.launch {
            viewModel.getPagingData().collect { pagingData ->
                attractionsAdapter.submitData(pagingData)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val aa = RetrofitUtils.instance.getService(AttractionsService::class.java).getAttractions()
            Log.e("GGG", aa.data.size.toString())
        }


//        attractionsAdapter.addLoadStateListener {
//            when(it.refresh) {
//                is LoadState.NotLoading -> {
//                    binding
//                }
//            }
//        }



    }


}