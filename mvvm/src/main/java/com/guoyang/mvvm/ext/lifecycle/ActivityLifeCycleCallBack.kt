package com.guoyang.mvvm.ext.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.guoyang.mvvm.ext.util.logD

/***
 *
 * 全局的Activity生命周期回调
 * @author Yang.Guo on 2021/5/31.
 */
class ActivityLifeCycleCallBack : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        ActivityManger.pushActivity(activity)
        "onActivityCreated : ${activity.localClassName}".logD()
    }

    override fun onActivityStarted(activity: Activity) {
        "onActivityStarted : ${activity.localClassName}".logD()
    }

    override fun onActivityResumed(activity: Activity) {
        "onActivityResumed : ${activity.localClassName}".logD()
    }

    override fun onActivityPaused(activity: Activity) {
        "onActivityPaused : ${activity.localClassName}".logD()
    }

    override fun onActivityStopped(activity: Activity) {
        "onActivityStopped : ${activity.localClassName}".logD()
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        "onActivitySaveInstanceState : ${activity.localClassName}".logD()
    }

    override fun onActivityDestroyed(activity: Activity) {
        "onActivityDestroyed : ${activity.localClassName}".logD()
        ActivityManger.popActivity(activity)
    }
}