package com.example.taipeitour.delegate

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlin.reflect.KProperty

/**
 * Author: FlyWei
 * E-mail: tony91097@gmail.com
 * Date: 2023/2/1
 */
class ContentViewBindingDelegate<in A : AppCompatActivity, out T : ViewDataBinding>(@LayoutRes private val layoutRes: Int) {

    private var binding: T? = null

    operator fun getValue(activity: A, property: KProperty<*>): T {
        binding?.let { return it }

        binding = DataBindingUtil.setContentView<T>(activity, layoutRes).apply {
            lifecycleOwner = activity
        }
        return binding!!
    }
}

fun <A : AppCompatActivity, T : ViewDataBinding> AppCompatActivity.contentView(@LayoutRes layoutRes: Int): ContentViewBindingDelegate<A, T> = ContentViewBindingDelegate(layoutRes)