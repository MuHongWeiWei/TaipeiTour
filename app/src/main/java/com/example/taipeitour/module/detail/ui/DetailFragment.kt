package com.example.taipeitour.module.detail.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.taipeitour.R
import com.example.taipeitour.databinding.FragmentDetailBinding
import com.example.taipeitour.delegate.binding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by binding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val number = arguments?.getString("picture")
        val title = arguments?.getString("title")
        val introduction = arguments?.getString("introduction")
        Log.e("GGG", number.toString())
        Log.e("GGG", title.toString())
        Log.e("GGG", introduction.toString())

    }


}