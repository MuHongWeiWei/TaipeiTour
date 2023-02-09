package com.example.taipeitour.delegate

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.lang.IllegalArgumentException
import kotlin.reflect.KProperty

/**
 * Author: FlyWei
 * E-mail: tony91097@gmail.com
 * Date: 2023/2/1
 */
class FragmentViewBindingDelegate<in F : Fragment, out T : ViewDataBinding> {

    private var binding: T? = null

    operator fun getValue(fragment: F, property: KProperty<*>): T {
        binding?.let { return it }

        fragment.view ?: throw IllegalArgumentException("The fragment view is empty or has been destroyed")

        binding = DataBindingUtil.bind<T>(fragment.requireView())?.also {
            it.lifecycleOwner = fragment.viewLifecycleOwner
        }

        fragment.parentFragmentManager.registerFragmentLifecycleCallbacks(Clear(fragment), false)

        return binding!!
    }

    inner class Clear(private val thisRef: F) : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
            if (thisRef === f) {
                binding = null
                fm.unregisterFragmentLifecycleCallbacks(this)
            }
        }
    }
}

fun <F : Fragment, T : ViewDataBinding> Fragment.binding(): FragmentViewBindingDelegate<F, T> = FragmentViewBindingDelegate()