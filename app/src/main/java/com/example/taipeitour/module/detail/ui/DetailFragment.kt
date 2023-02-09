package com.example.taipeitour.module.detail.ui

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.taipeitour.R
import com.example.taipeitour.act.MainActivity
import com.example.taipeitour.databinding.FragmentDetailBinding
import com.example.taipeitour.module.detail.viewModel.DetailViewModel

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding

    private val viewModel: DetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailBinding.bind(view)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        init()
    }

    private fun init() {
        val picture = arguments?.getString("picture")
            ?: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSN8VPG4Kf2uPQmz4ejWPVVwk2LcnGpwIyzcg&usqp=CAU"
        val title = arguments?.getString("title")
        val introduction = arguments?.getString("introduction")
        val url = arguments?.getString("url")

        (activity as MainActivity).upTitle(title)
        viewModel.setData(picture, title, introduction, url)

        binding.webViewURL.paint.flags = Paint.UNDERLINE_TEXT_FLAG
    }

}