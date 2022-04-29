package com.guoyang.mvvm.ext.lifecycle

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*

/***
 *
 * 自动绑定生命周期的Handler
 * @author Yang.Guo on 2021/5/31.
 */
class LifecycleHandler(
    private val lifecycleOwner: LifecycleOwner,
    callback: Callback,
    looper: Looper = Looper.getMainLooper()
) :
    Handler(looper, callback),
    LifecycleEventObserver {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_DESTROY -> {
                removeCallbacksAndMessages(null)
                lifecycleOwner.lifecycle.removeObserver(this)
            }
            else -> {}
        }
    }

}