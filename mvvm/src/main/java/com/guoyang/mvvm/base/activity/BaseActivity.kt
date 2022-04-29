package com.guoyang.mvvm.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.guoyang.mvvm.base.IView

/***
 *
 * 通用的Activity基类
 * @author Yang.Guo on 2021/5/31.
 */
abstract class BaseActivity : AppCompatActivity(), IView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (userDataBinding()) {
            setContentView(layoutId())
        } else {
            initDataBind()
        }
        initView(savedInstanceState)
    }

    /**
     * 是否使用DataBinding
     */
    open fun userDataBinding() = false

    /**
     * 供子类BaseVmDbActivity 初始化DataBinding操作
     */
    open fun initDataBind() {}
}