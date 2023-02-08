package com.example.taipeitour.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.taipeitour.R
import com.example.taipeitour.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    lateinit var binding: FragmentDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

    }


}