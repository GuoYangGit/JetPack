package com.yangguo.base.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.preference.PreferenceManager
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.guoyang.mvvm.ext.util.getCompatColor
import com.kingja.loadsir.core.LoadService
import com.yangguo.base.R
import com.yangguo.base.weight.loadcallback.LoadingCallback


object SettingUtil {

    /**
     * 获取当前主题颜色
     */
    fun getColor(context: Context): Int = context.getCompatColor(R.color.colorPrimary)

    private fun getOneColorStateList(color: Int): ColorStateList {
        val colors = intArrayOf(color)
        val states = arrayOfNulls<IntArray>(1)
        states[0] = intArrayOf()
        return ColorStateList(states, colors)
    }

    /**
     * 设置loading的颜色 加载布局
     */
    fun setLoadingColor(color: Int, load: LoadService<Any>) {
        load.setCallBack(LoadingCallback::class.java) { _, view ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.findViewById<ProgressBar>(R.id.loading_progress).run {
                    indeterminateTintMode = PorterDuff.Mode.SRC_ATOP
                    indeterminateTintList = getOneColorStateList(color)
                }
            }
        }
    }
}
