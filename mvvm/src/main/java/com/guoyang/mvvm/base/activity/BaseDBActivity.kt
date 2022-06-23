package com.guoyang.mvvm.base.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/***
 *
 * 通用的数据绑定Activity基类
 * @author Yang.Guo on 2021/6/2.
 */
abstract class BaseDBActivity<DB : ViewDataBinding> : BaseActivity() {
    protected var binding: DB? = null

    override fun userDataBinding(): Boolean = true

    override fun initDataBind() {
        binding = DataBindingUtil.setContentView(this, layoutId())
        binding?.lifecycleOwner = this
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}