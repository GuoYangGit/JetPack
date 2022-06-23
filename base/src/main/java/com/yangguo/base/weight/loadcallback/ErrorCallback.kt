package com.yangguo.base.weight.loadcallback

import com.kingja.loadsir.callback.Callback
import com.yangguo.base.R


class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_error
    }

}