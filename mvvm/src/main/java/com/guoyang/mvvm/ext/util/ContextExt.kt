package com.guoyang.mvvm.ext.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.provider.Settings
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.time.Duration

/***
 *
 * Context扩展类
 * @author Yang.Guo on 2021/5/31.
 */
/**
 * 获取设备屏幕宽度
 */
val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

/**
 * 获取设备屏幕高度
 */
val Context.screenHeight: Int
    get() = resources.displayMetrics.heightPixels

/**
 * dp值转换为px
 */
fun Context.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

/**
 * px值转换成dp
 */
fun Context.px2dp(px: Int): Int {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

/**
 * 复制文本到粘贴板
 */
fun Context.copyToClipboard(text: String, label: String = "JetpackMvvm") {
    val clipData = ClipData.newPlainText(label, text)
    ContextCompat.getSystemService(this, ClipboardManager::class.java)?.setPrimaryClip(clipData)
}

/**
 * 检查是否启用无障碍服务
 */
fun checkAccessibilityServiceEnabled(serviceName: String): Boolean {
    var result = false
    val splitter = TextUtils.SimpleStringSplitter(':')
    while (splitter.hasNext()) {
        if (splitter.next().equals(serviceName, true)) {
            result = true
            break
        }
    }
    return result
}

/**
 * 显示toast
 */
fun Fragment.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) =
    context?.showToast(text, duration)

/**
 * 显示toast
 */
fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

/**
 * 获取Color
 */
fun Context.getCompatColor(@ColorRes colorID: Int): Int = ContextCompat.getColor(this, colorID)