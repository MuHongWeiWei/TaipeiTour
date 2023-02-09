package com.example.taipeitour.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

/**
 * Author: FlyWei
 * E-mail: tony91097@gmail.com
 * Date: 2021/4/28
 */

class SharedInfo private constructor() {

    private var sp: SharedPreferences = SPUtil.getSp(context, fileName)

    companion object {
        val instance: SharedInfo by lazy { SharedInfo() }
        private lateinit var fileName: String
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun init(fileName: String, context: Context) {
            Companion.fileName = fileName
            Companion.context = context
        }
    }

    /**
     * 獲取資料
     */
    fun <T> getEntity(clazz: Class<T>): T? =
        SPUtil.getEntity(sp, clazz)

    fun getValue(key: String, defaultValue: Any): Any? =
        SPUtil.getValue(sp, key, defaultValue)

    /**
     * 保存資料
     */
    fun saveEntity(any: Any) {
        SPUtil.saveEntity(sp, any)
    }

    fun saveValue(key: String, value: Any) {
        SPUtil.saveValue(sp, key, value)
    }

    /**
     * 刪除資料
     */
    fun remove(clazz: Class<*>) {
        SPUtil.remove(sp, clazz.name)
    }

    fun remove(key: String) {
        SPUtil.remove(sp, key)
    }

    /**
     * 清除資料
     */
    fun clear() {
        SPUtil.clear(sp)
    }
}