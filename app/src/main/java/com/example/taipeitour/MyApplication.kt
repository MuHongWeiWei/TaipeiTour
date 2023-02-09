package com.example.taipeitour

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.example.taipeitour.common.BaseParams
import com.example.taipeitour.utils.ActivityManage
import com.example.taipeitour.utils.SharedInfo
import com.hjq.language.MultiLanguages

/**
 * Author: FlyWei
 * E-mail: tony91097@gmail.com
 * Date: 2023/2/9
 */
class MyApplication : Application() {

    private val TAG = MyApplication::class.java.simpleName
    var count = 0

    override fun onCreate() {
        super.onCreate()

        SharedInfo.init(BaseParams.SP_NAME, applicationContext)
        MultiLanguages.init(this)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                ActivityManage.push(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                ActivityManage.setTopActivity(activity)
                if (count++ == 0) {
                    Log.e(TAG, "前台")
                }
            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {
                count--
                if (count == 0) {
                    Log.e(TAG, "後台")
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {
                ActivityManage.remove(activity)
            }
        })
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(MultiLanguages.attach(base))
    }

}