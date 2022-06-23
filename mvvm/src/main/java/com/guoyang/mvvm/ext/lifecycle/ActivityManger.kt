package com.guoyang.mvvm.ext.lifecycle

import android.app.Activity
import java.util.*

/***
 *
 * 管理App中所有的Activity
 * @author Yang.Guo on 2021/5/31.
 */
object ActivityManger {
    // 管理所有的Activity
    private val mActivityList = LinkedList<Activity>()

    // 当前展示的Activity
    private val currentActivity: Activity?
        get() =
            if (mActivityList.isEmpty()) null
            else mActivityList.last

    /**
     * activity入栈
     */
    fun pushActivity(activity: Activity) {
        if (!mActivityList.contains(activity)) {
            mActivityList.add(activity)
            return
        }
        if (mActivityList.last == activity) return
        mActivityList.remove(activity)
        mActivityList.add(activity)
    }

    /**
     * activity出栈
     */
    fun popActivity(activity: Activity) {
        mActivityList.remove(activity)
    }

    /**
     * 获取当前的Activity
     */
    fun getTopActivity(): Activity? {
        return currentActivity
    }

    /**
     * 关闭当前activity
     */
    fun finishCurrentActivity() {
        currentActivity?.finish()
    }

    /**
     * 关闭传入的activity
     */
    fun finishActivity(activity: Activity) {
        mActivityList.remove(activity)
        activity.finish()
    }

    /**
     * 关闭传入的activity类名
     */
    fun finishActivity(clazz: Class<*>) {
        mActivityList.forEach {
            if (it.javaClass == clazz)
                it.finish()
        }
    }

    /**
     * 关闭所有的activity
     */
    fun finishAllActivity() {
        mActivityList.forEach {
            it.finish()
        }
    }
}