package com.guoyang.mvvm.util

import android.content.Context
import com.tencent.mmkv.MMKV

/***
 *
 * MVVM工具类
 * @author Yang.Guo on 2021/6/1.
 */
object SpUtils {
    private const val defaultMapID: String = "app"

    fun init(context: Context, pathName: String = "/mmkv") {
        val root = context.filesDir.absolutePath + pathName
        MMKV.initialize(root)
    }

    fun putString(key: String, value: String, mmapID: String = defaultMapID) {
        val kv = MMKV.mmkvWithID(mmapID)
        kv.encode(key, value)
    }

    fun putInt(key: String, value: Int, mmapID: String = defaultMapID) {
        val kv = MMKV.mmkvWithID(mmapID)
        kv.encode(key, value)
    }

    fun putBoolean(key: String, value: Boolean, mmapID: String = defaultMapID) {
        val kv = MMKV.mmkvWithID(mmapID)
        kv.encode(key, value)
    }

    fun putFloat(key: String, value: Float, mmapID: String = defaultMapID) {
        val kv = MMKV.mmkvWithID(mmapID)
        kv.encode(key, value)
    }

    fun putDouble(key: String, value: Double, mmapID: String = defaultMapID) {
        val kv = MMKV.mmkvWithID(mmapID)
        kv.encode(key, value)
    }

    fun putLong(key: String, value: Long, mmapID: String = defaultMapID) {
        val kv = MMKV.mmkvWithID(mmapID)
        kv.encode(key, value)
    }

    fun getString(key: String, defaultValue: String = "", mmapID: String = defaultMapID): String {
        val kv = MMKV.mmkvWithID(mmapID)
        return kv.decodeString(key, defaultValue) ?: defaultValue
    }

    fun getInt(key: String, defaultValue: Int = 0, mmapID: String = defaultMapID): Int {
        val kv = MMKV.mmkvWithID(mmapID)
        return kv.decodeInt(key, defaultValue)
    }

    fun getBoolean(
        key: String,
        defaultValue: Boolean = false,
        mmapID: String = defaultMapID
    ): Boolean {
        val kv = MMKV.mmkvWithID(mmapID)
        return kv.decodeBool(key, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float = 0f, mmapID: String = defaultMapID): Float {
        val kv = MMKV.mmkvWithID(mmapID)
        return kv.decodeFloat(key, defaultValue)
    }

    fun getDouble(key: String, defaultValue: Double = 0.00, mmapID: String = defaultMapID): Double {
        val kv = MMKV.mmkvWithID(mmapID)
        return kv.decodeDouble(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long = 0L, mmapID: String = defaultMapID): Long {
        val kv = MMKV.mmkvWithID(mmapID)
        return kv.decodeLong(key, defaultValue)
    }

    fun clear(mmapID: String = defaultMapID) {
        val kv = MMKV.mmkvWithID(defaultMapID)
        kv.clear()
    }
}