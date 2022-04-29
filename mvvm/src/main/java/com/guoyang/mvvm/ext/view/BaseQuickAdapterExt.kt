package com.guoyang.mvvm.ext.view

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * 给adapter拓展的，防止重复点击item
 * @author Yang.Guo on 2021/5/31.
 */
var adapterLastClickTime = 0L

fun BaseQuickAdapter<*, *>.setNbOnItemClickListener(
    interval: Long = 1000,
    action: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit
) {
    setOnItemClickListener { adapter, view, position ->
        val currentTime = System.currentTimeMillis()
        if (adapterLastClickTime != 0L && (currentTime - adapterLastClickTime < interval)) {
            return@setOnItemClickListener
        }
        adapterLastClickTime = currentTime
        action(adapter, view, position)
    }
}

/**
 * 给adapter拓展的，防止重复点击item
 */
var adapterChildLastClickTime = 0L
fun BaseQuickAdapter<*, *>.setNbOnItemChildClickListener(
    interval: Long = 1000,
    action: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit
) {
    setOnItemChildClickListener { adapter, view, position ->
        val currentTime = System.currentTimeMillis()
        if (adapterChildLastClickTime != 0L && (currentTime - adapterChildLastClickTime < interval)) {
            return@setOnItemChildClickListener
        }
        adapterChildLastClickTime = currentTime
        action(adapter, view, position)
    }
}