package com.yangguo.base.ext.view

import android.widget.TextView
import com.kingja.loadsir.core.LoadService
import com.yangguo.base.R
import com.yangguo.base.weight.loadcallback.EmptyCallback
import com.yangguo.base.weight.loadcallback.ErrorCallback
import com.yangguo.base.weight.loadcallback.LoadingCallback

/**
 * 状态布局扩展类
 * @author  yang.guo on 2022/4/29
 */
/**
 * 设置错误布局错误提示
 * @param message 错误提示
 */
private fun LoadService<*>.setErrorText(message: String) {
    if (message.isEmpty()) return
    this.setCallBack(ErrorCallback::class.java) { _, view ->
        view.findViewById<TextView>(R.id.error_tv).text = message
    }
}

/**
 * 设置错误布局
 * @param message 错误布局显示的提示内容
 */
fun LoadService<*>.showError(message: String = "") {
    this.setErrorText(message)
    this.showCallback(ErrorCallback::class.java)
}

/**
 * 设置空布局
 */
fun LoadService<*>.showEmpty() {
    this.showCallback(EmptyCallback::class.java)
}

/**
 * 设置加载中
 */
fun LoadService<*>.showLoading() {
    this.showCallback(LoadingCallback::class.java)
}
