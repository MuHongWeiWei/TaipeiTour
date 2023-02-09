package com.example.taipeitour.module.home.viewModel

import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taipeitour.common.Language
import com.example.taipeitour.dataModel.AttractionsData
import com.example.taipeitour.dataModel.AttractionsRelease
import com.example.taipeitour.dataModel.Infos
import com.example.taipeitour.databinding.DialogLanguageLayoutBinding
import com.example.taipeitour.module.home.ui.activity.MainActivity
import com.example.taipeitour.utils.SharedInfo
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

/**
 * Author: FlyWei
 * E-mail: tony91097@gmail.com
 * Date: 2023/2/9
 */
class MainViewModel : ViewModel() {

    private val _language = MutableLiveData<Language>()
    val language: LiveData<Language> = _language

    fun showLanguageDialog(mainActivity: MainActivity) {
        val binding = DialogLanguageLayoutBinding.inflate(LayoutInflater.from(mainActivity))

        val dialog = AlertDialog.Builder(mainActivity)
            .setView(binding.root)
            .setCancelable(false)
            .show()

        binding.ok.setOnClickListener {
            val checkedRadioButtonId = binding.languageGroup.checkedRadioButtonId
            if (checkedRadioButtonId != -1) {
                val checkRadioButton: RadioButton = binding.root.findViewById(checkedRadioButtonId)
                for (value in Language.values()) {
                    if (value.lang == checkRadioButton.text) {
                        _language.value = value
                        dialog.dismiss()
                    }
                }
            } else {
                Toast.makeText(mainActivity, "未選擇", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun getPicture() {
        val attractionsInfos = SharedInfo.instance.getEntity(Infos::class.java)
        if (attractionsInfos == null) {
            getAttractionsPicture()
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
                val attractionsAllData = mutableListOf<AttractionsData>()

                val attractionsPicture =
                    Gson().fromJson(response.body!!.string(), AttractionsRelease::class.java)
                attractionsPicture.XML_Head?.Infos?.Info?.forEach {
                    attractionsAllData.add(it)
                }

                SharedInfo.instance.saveEntity(Infos(attractionsAllData))
            }
        })
    }
}