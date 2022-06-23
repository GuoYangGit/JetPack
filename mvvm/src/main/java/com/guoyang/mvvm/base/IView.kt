package com.guoyang.mvvm.base

import android.os.Bundle

/**
 * Activity、Fragment的基类接口
 * @author  yang.guo on 2022/4/28
 */
interface IView {
    /**
     * 获取布局文件ID
     */
    fun layoutId(): Int

    /**
     * 初始化数据
     */
    fun initView(savedInstanceState: Bundle?)

    /**
     * 显示Loading
     */
    fun showLoading(message: String)

    /**
     * 隐藏Loading
     */
    fun dismissLoading()
}