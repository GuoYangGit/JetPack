package com.yangguo.base.weight.loadCallBack


import com.kingja.loadsir.callback.Callback
import com.yangguo.base.R


class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}