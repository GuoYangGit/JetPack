package com.guoyang.mvvm.ext.util

import android.util.Log

/***
 *
 * 日志工具扩展类
 * @author Yang.Guo on 2021/5/31.
 */

// 通用Tag
const val TAG = "JetpackMvvm"

// 是否开启日志打印
var MVVM_LOG = true

// 日志打印等级
private enum class LEVEL {
    V, D, I, W, E
}

fun String.logV(tag: String = TAG) = log(LEVEL.V, tag, this)
fun String.logD(tag: String = TAG) = log(LEVEL.D, tag, this)
fun String.logI(tag: String = TAG) = log(LEVEL.I, tag, this)
fun String.logW(tag: String = TAG) = log(LEVEL.W, tag, this)
fun String.logE(tag: String = TAG) = log(LEVEL.E, tag, this)

/**
 * 日志打印
 * @param level 日志等级
 * @param tag 日志Tag
 * @param message 日志内容
 */
private fun log(level: LEVEL, tag: String, message: String) {
    if (!MVVM_LOG) return
    when (level) {
        LEVEL.V -> Log.v(tag, message)
        LEVEL.D -> Log.d(tag, message)
        LEVEL.I -> Log.i(tag, message)
        LEVEL.W -> Log.w(tag, message)
        LEVEL.E -> Log.e(tag, message)
    }
}