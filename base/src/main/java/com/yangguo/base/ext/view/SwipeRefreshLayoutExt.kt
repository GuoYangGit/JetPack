package com.yangguo.base.ext.view

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.guoyang.mvvm.base.appContext
import com.kingja.loadsir.core.LoadService
import com.yangguo.base.util.SettingUtil

/**
 * 刷新布局扩展类
 * @author  yang.guo on 2022/4/29
 */
/**
 * 初始化 SwipeRefreshLayout
 */
inline fun SwipeRefreshLayout.bindTitle(crossinline onRefreshListener: () -> Unit) {
    setOnRefreshListener {
        onRefreshListener.invoke()
    }
    //设置主题颜色
    setColorSchemeColors(SettingUtil.getColor(appContext))
}

/**
 * 初始化 SwipeRefreshLayout
 * 并且绑定状态布局
 */
inline fun SwipeRefreshLayout.initLoadService(crossinline onRefreshListener: () -> Unit): LoadService<Any> {
    bindTitle(onRefreshListener)
    return bindLoadSir(onRefreshListener)
}