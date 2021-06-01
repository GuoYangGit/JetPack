package com.guoyang.mvvm.ext.view

import android.view.View

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
 * Created by Yang.Guo on 2021/5/31.
 */
/**
 * 获取设备的density
 */
val View.dpi: Int
    get() = context.resources.displayMetrics.density.toInt()
/**
 * 设置view显示
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * 设置view占位隐藏
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * 设置view隐藏
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * 根据条件设置view显示隐藏 为true 显示，为false 隐藏
 */
fun View.visibleOrGone(flag:Boolean) {
    visibility = if(flag){
        View.VISIBLE
    }else{
        View.GONE
    }
}

/**
 * 根据条件设置view显示隐藏 为true 显示，为false 隐藏
 */
fun View.visibleOrInvisible(flag:Boolean) {
    visibility = if(flag){
        View.VISIBLE
    }else{
        View.INVISIBLE
    }
}

/**
 * dp值转换为px
 */
fun View.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

/**
 * px值转换成dp
 */
fun View.px2dp(px: Int): Int {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

/**
 * 防止重复点击事件 默认0.5秒内不可重复点击
 * @param interval 时间间隔 默认0.5秒
 * @param action 执行方法
 */
var lastClickTime = 0L
fun View.clickNoRepeat(interval: Long = 500, action: (view: View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime != 0L && (currentTime - lastClickTime < interval)) {
            return@setOnClickListener
        }
        lastClickTime = currentTime
        action(it)
    }
}