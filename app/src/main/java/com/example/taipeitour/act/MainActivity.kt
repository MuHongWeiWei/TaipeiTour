package com.example.taipeitour.act

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.taipeitour.AttractionsPicture
import com.example.taipeitour.R
import com.example.taipeitour.databinding.ActivityMainBinding
import com.example.taipeitour.utils.SharedInfo
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val attractionsPicture = SharedInfo.instance.getEntity(AttractionsPicture::class.java)
        if (attractionsPicture == null) {
            getAttractionsPicture()
        }
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


    private fun getAttractionsPicture() {
        val client = OkHttpClient.Builder().build()
        val request = Request.Builder()
            .url("https://media.taiwan.net.tw/XMLReleaseALL_public/scenic_spot_C_f.json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val attractionsPicture =
                    Gson().fromJson(response.body!!.string(), AttractionsPicture::class.java)
                SharedInfo.instance.saveEntity(attractionsPicture)
            }
        })
    }

}