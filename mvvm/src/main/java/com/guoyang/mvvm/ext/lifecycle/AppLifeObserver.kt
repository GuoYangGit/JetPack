package com.guoyang.mvvm.ext.lifecycle

import androidx.lifecycle.*

/***
 *
 * App前后台切换监听
 * @author Yang.Guo on 2021/5/31.
 */
object AppLifeObserver : LifecycleEventObserver {
    val isForeground by lazy { MutableLiveData<Boolean>() }

    /**
     * 前台展示
     */
    private fun onForeground() {
        isForeground.value = true
    }

    /**
     * 后台展示
     */
    private fun obBackground() {
        isForeground.value = false
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> onForeground()
            Lifecycle.Event.ON_STOP -> obBackground()
            else -> {}
        }
    }
}