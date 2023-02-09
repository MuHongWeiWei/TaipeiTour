package com.example.taipeitour.common

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.hjq.language.MultiLanguages

/**
 * Author: FlyWei
 * E-mail: tony91097@gmail.com
 * Date: 2023/2/9
 */
open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(MultiLanguages.attach(newBase));
    }

}