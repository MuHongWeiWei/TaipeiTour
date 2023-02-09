package com.example.taipeitour

import android.app.Application
import com.example.taipeitour.common.BaseParams
import com.example.taipeitour.utils.SharedInfo

/**
 * Author: FlyWei
 * E-mail: tony91097@gmail.com
 * Date: 2023/2/9
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        SharedInfo.init(BaseParams.SP_NAME, applicationContext)
    }
}