package com.example.taipeitour.module.detail.ui.fragment

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.taipeitour.R
import com.example.taipeitour.databinding.FragmentDetailBinding
import com.example.taipeitour.module.detail.viewModel.DetailViewModel
import com.example.taipeitour.module.home.ui.activity.MainActivity

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailBinding.bind(view)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initData()
        initView()
    }

    private fun initData() {
        val picture = arguments?.getString("picture")
            ?: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSN8VPG4Kf2uPQmz4ejWPVVwk2LcnGpwIyzcg&usqp=CAU"
        val title = arguments?.getString("title")
        val introduction = arguments?.getString("introduction")
        val url = arguments?.getString("url")

        viewModel.setData(picture, title, introduction, url)
        (activity as MainActivity).upTitle(title)
    }

    private fun initView() {
        binding.webViewURL.apply {
            paint.flags = Paint.UNDERLINE_TEXT_FLAG

            setOnClickListener {
                viewModel.openWebView(findNavController())
            }
        }
    }
}