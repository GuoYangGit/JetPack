package com.guoyang.mvvm.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.startup.Initializer
import com.guoyang.mvvm.ext.lifecycle.AppLifeObserver
import com.guoyang.mvvm.ext.lifecycle.ActivityLifeCycleCallBack

/***
 * App启动全局基础类
 * @author Yang.Guo on 2021/5/31.
 */

/**
 * 全局的AppContext
 */
val appContext: Application by lazy {
    Ktx.app
}

object Ktx {
    lateinit var app: Application

    fun initialize(context: Context): Ktx {
        app = context.applicationContext as Application
        // 注册全局的Activity生命周期监听
        app.registerActivityLifecycleCallbacks(ActivityLifeCycleCallBack())
        // 注册全局的App生命周期监听
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifeObserver)
        return this
    }
}

/**
 * AppStartUp启动类
 */
class KtxInitializer : Initializer<Ktx> {
    override fun create(context: Context): Ktx {
        return Ktx.initialize(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}