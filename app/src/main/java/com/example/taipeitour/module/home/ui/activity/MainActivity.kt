package com.example.taipeitour.module.home.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.taipeitour.R
import com.example.taipeitour.common.Language
import com.example.taipeitour.databinding.ActivityMainBinding
import com.example.taipeitour.module.home.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel.getPicture()

        toObserver()
    }

    private fun toObserver() {
        viewModel.language.observe(this) { language ->
            Log.e("GGG", language.lang)
            Log.e("GGG", language.symbol)
        }
    }

    override fun onStart() {
        super.onStart()

        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.topAppBar, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.topAppBar.title = "台北旅遊網"
                    binding.topAppBar.menu.setGroupVisible(0, true)
                }
                R.id.detailFragment -> {
                    binding.topAppBar.title = ""
                    binding.topAppBar.menu.setGroupVisible(0, false)
                }
            }
        }

        binding.topAppBar.setOnMenuItemClickListener {menuItem ->
            when (menuItem.itemId) {
                R.id.language -> {
                    viewModel.showLanguageDialog(this)
                    true
                }
                else -> false
            }
        }
    }

    fun upTitle(title: String?) {
        binding.topAppBar.title = title
    }

}