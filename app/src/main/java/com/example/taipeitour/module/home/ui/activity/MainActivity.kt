package com.example.taipeitour.module.home.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.taipeitour.R
import com.example.taipeitour.common.BaseActivity
import com.example.taipeitour.databinding.ActivityMainBinding
import com.example.taipeitour.module.home.viewModel.MainViewModel
import com.hjq.language.MultiLanguages
import com.hjq.language.OnLanguageListener
import java.util.*


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel.getPicture()
    }

    override fun onStart() {
        super.onStart()

        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.topAppBar, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.topAppBar.title = getString(R.string.app_name)
                    binding.topAppBar.menu.setGroupVisible(0, true)
                }
                R.id.detailFragment -> {
                    binding.topAppBar.title = ""
                    binding.topAppBar.menu.setGroupVisible(0, false)
                }
            }
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.language -> {
                    viewModel.showLanguageDialog(this)
                    true
                }
                else -> false
            }
        }

        MultiLanguages.setOnLanguageListener(object : OnLanguageListener {
            override fun onAppLocaleChange(oldLocale: Locale?, newLocale: Locale?) {
                Toast.makeText(this@MainActivity, "切換語言請重新啟動APP", Toast.LENGTH_SHORT).show()
            }

            override fun onSystemLocaleChange(oldLocale: Locale?, newLocale: Locale?) {
                Toast.makeText(this@MainActivity, "切換語言請重新啟動APP", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun upTitle(title: String?) {
        binding.topAppBar.title = title
    }

}