package com.example.taipeitour.act

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.taipeitour.R
import com.example.taipeitour.databinding.ActivityMainBinding
import com.example.taipeitour.delegate.contentView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.topAppBar, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.topAppBar.title = "TaipeiTour"
                }
                R.id.detailFragment -> {
                    binding.topAppBar.title = "旅遊"

                }
            }
        }
    }

}