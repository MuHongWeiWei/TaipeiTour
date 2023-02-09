package com.example.taipeitour.module.detail.viewModel

import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.taipeitour.R

/**
 * Author: FlyWei
 * E-mail: tony91097@gmail.com
 * Date: 2023/2/9
 */
class DetailViewModel : ViewModel() {

    private val _picture = MutableLiveData<String>()
    val picture: LiveData<String> = _picture

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _content = MutableLiveData<String>()
    val content: LiveData<String> = _content

    private val _url = MutableLiveData<String>()
    val url: LiveData<String> = _url

    fun setData(picture: String?, title: String?, introduction: String?, url: String?) {
        _picture.value = picture.toString()
        _title.value = title.toString()
        _content.value = introduction.toString()
        _url.value = url.toString()
    }

    fun openWebView(findNavController: NavController) {
        findNavController.navigate(R.id.webViewFragment, bundleOf("title" to title.value, "url" to url.value))
    }

}