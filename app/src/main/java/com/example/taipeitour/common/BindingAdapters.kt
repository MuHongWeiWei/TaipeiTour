package com.example.taipeitour.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.taipeitour.utils.ActivityManage

/**
 * Author: FlyWei
 * E-mail: tony91097@gmail.com
 * Date: 2023/2/9
 */
object BindingAdapters {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(imageView: ImageView, url: String?) {
        Glide.with(ActivityManage.peek()!!).load(url).into(imageView)
    }

}