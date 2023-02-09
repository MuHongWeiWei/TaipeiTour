package com.example.taipeitour.act

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.taipeitour.R
import com.example.taipeitour.dataModel.AttractionsData
import com.example.taipeitour.dataModel.AttractionsRelease
import com.example.taipeitour.dataModel.Infos
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

        val attractionsInfos = SharedInfo.instance.getEntity(Infos::class.java)
        if (attractionsInfos == null) {
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
                    binding.topAppBar.title = "台北旅遊網"
                }
                R.id.detailFragment -> {
                    binding.topAppBar.title = "名稱"
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
                // 儲存資訊
                val attractionsAllData = mutableListOf<AttractionsData>()

                val attractionsPicture = Gson().fromJson(response.body!!.string(), AttractionsRelease::class.java)
                attractionsPicture.XML_Head?.Infos?.Info?.forEach {
                    attractionsAllData.add(it)
                }

                SharedInfo.instance.saveEntity(Infos(attractionsAllData))
            }
        })
    }

}