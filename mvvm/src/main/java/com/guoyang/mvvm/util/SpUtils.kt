package com.guoyang.mvvm.util

import android.content.Context
import com.tencent.mmkv.MMKV

/***
 *
 *   █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 *  ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 *  ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 *  ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 *           ░     ░ ░      ░  ░
 *
 * Created by Yang.Guo on 2021/6/1.
 */
object SpUtils {
    private val defaultMapID = "app"

    fun init(context: Context, pathName: String = "/mmkv") {
        val root = context.filesDir.absolutePath + "/mmkv"
        MMKV.initialize(root)
    }

    fun putString(key: String, value: String) {
        val kv = MMKV.mmkvWithID(defaultMapID)
        kv.encode(key, value)
    }

    fun putInt(key: String, value: Int) {
        val kv = MMKV.mmkvWithID(defaultMapID)
        kv.encode(key, value)
    }

    fun putBoolean(key: String, value: Boolean) {
        val kv = MMKV.mmkvWithID(defaultMapID)
        kv.encode(key, value)
    }

    fun putFloat(key: String, value: Float) {
        val kv = MMKV.mmkvWithID(defaultMapID)
        kv.encode(key, value)
    }

    fun putDouble(key: String, value: Double) {
        val kv = MMKV.mmkvWithID(defaultMapID)
        kv.encode(key, value)
    }

    fun putLong(key: String, value: Long) {
        val kv = MMKV.mmkvWithID(defaultMapID)
        kv.encode(key, value)
    }

    fun getString(key: String, defaultValue: String) {
        val kv = MMKV.mmkvWithID(defaultMapID)
        kv.decodeString(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int) {
        val kv = MMKV.mmkvWithID(defaultMapID)
        kv.decodeInt(key, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val kv = MMKV.mmkvWithID(defaultMapID)
        return kv.decodeBool(key, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        val kv = MMKV.mmkvWithID(defaultMapID)
        return kv.decodeFloat(key, defaultValue)
    }

    fun getDouble(key: String, defaultValue: Double): Double {
        val kv = MMKV.mmkvWithID(defaultMapID)
        return kv.decodeDouble(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        val kv = MMKV.mmkvWithID(defaultMapID)
        return kv.decodeLong(key, defaultValue)
    }
}