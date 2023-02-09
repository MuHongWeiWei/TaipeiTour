package com.example.taipeitour.module.detail.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.taipeitour.R
import com.example.taipeitour.act.MainActivity
import com.example.taipeitour.databinding.FragmentDetailBinding
import com.example.taipeitour.delegate.binding
import com.example.taipeitour.module.detail.viewModel.DetailViewModel

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by binding()
    private val viewModel: DetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        init()
    }

    private fun init() {
        val picture = arguments?.getString("picture")
        val title = arguments?.getString("title")
        val introduction = arguments?.getString("introduction")
        val url = arguments?.getString("url")

        (activity as MainActivity).upTitle(title)
        viewModel.setData(picture, title, introduction, url)
    }

}