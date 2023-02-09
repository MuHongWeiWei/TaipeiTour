package com.example.taipeitour.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import com.google.gson.Gson

/**
 * Author: FlyWei
 * E-mail: tony91097@gmail.com
 * Date: 2021/4/28
 */

object SPUtil {

    fun getSp(context: Context, fileName: String): SharedPreferences {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    fun saveValue(sp: SharedPreferences, key: String, value: Any) {
        val editor = sp.edit()
        when (value) {
            is String -> editor.putString(key, value).apply()
            is Boolean -> editor.putBoolean(key, value).apply()
            is Float -> editor.putFloat(key, value).apply()
            is Int -> editor.putInt(key, value).apply()
            is Long -> editor.putLong(key, value).apply()
            else -> require(value !is Set<*>) { "Value can not be Set object!" }
        }
    }

    fun getValue(sp: SharedPreferences, key: String, defaultValue: Any): Any? {
        when (defaultValue) {
            is String -> return sp.getString(key, defaultValue)
            is Boolean -> return sp.getBoolean(key, defaultValue)
            is Float -> return sp.getFloat(key, defaultValue)
            is Int -> return sp.getInt(key, defaultValue)
            is Long -> return sp.getLong(key, defaultValue)
            else -> require(defaultValue !is Set<*>) { "Can not to get Set value!" }
        }
        return null
    }

    fun saveEntity(sp: SharedPreferences, any: Any) {
        val key = any.javaClass.name
        val value = String(Base64.encode(obj2str(any).toByteArray(), Base64.DEFAULT))
        saveValue(sp, key, value)
    }

    fun <T> getEntity(sp: SharedPreferences, clazz: Class<T>): T? {
        val key = clazz.name
        var value = getValue(sp, key, "") as String
        value = String(Base64.decode(value, Base64.DEFAULT))
        return str2obj(value, clazz)
    }

    fun remove(sp: SharedPreferences, key: String) {
        sp.edit().remove(key).apply()
    }

    fun clear(sp: SharedPreferences) {
        sp.edit().clear().apply()
    }

    /**
     * Object 到 String 的序列化
     */
    private fun obj2str(any: Any): String {
        return try {
            Gson().toJson(any)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * String 到 Object 的反序列化
     */
    private fun <T> str2obj(string: String, clazz: Class<T>): T? {
        return try {
            Gson().fromJson(string, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}