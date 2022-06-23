package com.yangguo.base.ext.view

import androidx.appcompat.widget.Toolbar
import com.guoyang.mvvm.base.appContext
import com.guoyang.mvvm.ext.util.getCompatColor
import com.yangguo.base.R
import com.yangguo.base.util.SettingUtil

/**
 * ToolBar扩展类
 * @author  yang.guo on 2022/4/29
 */

/**
 * 初始化普通的toolbar 只设置标题
 */
fun Toolbar.bindTitle(titleStr: String = ""): Toolbar {
    setBackgroundColor(SettingUtil.getColor(appContext))
    setTitleTextColor(appContext.getCompatColor(R.color.white))
    title = titleStr
    return this
}

/**
 * 初始化有返回键的toolbar
 */
inline fun Toolbar.bind(
    titleStr: String = "",
    backImg: Int = R.mipmap.ic_back,
    crossinline onBack: (toolbar: Toolbar) -> Unit
): Toolbar {
    bindTitle(titleStr)
    setNavigationIcon(backImg)
    setNavigationOnClickListener { onBack.invoke(this) }
    return this
}
