package com.example.taipeitour.module.detail.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taipeitour.R
import com.example.taipeitour.databinding.FragmentWebViewBinding
import com.example.taipeitour.module.home.ui.activity.MainActivity

/**
 * Author: FlyWei
 * E-mail: tony91097@gmail.com
 * Date: 2023/2/9
 */

@SuppressLint("SetJavaScriptEnabled")
class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    lateinit var binding: FragmentWebViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentWebViewBinding.bind(view)
        binding.lifecycleOwner = this

        initView()
    }

    private fun initView() {
        val title = arguments?.getString("title")
        (activity as MainActivity).upTitle(title)
        (activity as MainActivity).onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.webView.canGoBack()) {
                        binding.webView.goBack()
                    } else {
                        findNavController().popBackStack()
                    }
                }
            })

        arguments?.getString("url")?.let { url ->
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.loadUrl(url)
        }

        binding.webView.webViewClient = (object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                binding.loadingView.visibility = View.GONE
            }
        })
    }

}