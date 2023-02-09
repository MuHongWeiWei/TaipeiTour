package com.example.taipeitour.act

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taipeitour.R
import com.example.taipeitour.databinding.ActivityWebViewBinding
import com.example.taipeitour.delegate.contentView

class WebViewActivity : AppCompatActivity() {

    private val binding: ActivityWebViewBinding by contentView(R.layout.activity_web_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}