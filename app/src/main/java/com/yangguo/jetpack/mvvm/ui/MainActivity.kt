package com.yangguo.jetpack.mvvm.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.guoyang.mvvm.ext.util.showToast
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.yangguo.base.ui.BaseVMActivity
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.ActivityMainBinding
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseVMActivity<ActivityMainBinding>() {
    var exitTime = 0L

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        UltimateBarX.with(this)
            .light(true)
            .transparent()
            .apply {
                applyStatusBar()
                applyNavigationBar()
            }
        //进入首页检查更新
//        Beta.checkUpgrade(false, true)

//        XXPermissions.with(this)
//            // 申请安装包权限
//            .permission(Permission.REQUEST_INSTALL_PACKAGES)
//            // 申请悬浮窗权限
//            .permission(Permission.SYSTEM_ALERT_WINDOW)
//            // 申请通知栏权限
//            .permission(Permission.NOTIFICATION_SERVICE)
//            // 申请系统设置权限
//            .permission(Permission.WRITE_SETTINGS)
//            // 申请单个权限
//            .permission(Permission.RECORD_AUDIO)
//            // 申请多个权限
//            .permission(Permission.Group.CALENDAR)
//            .request(object : OnPermissionCallback {
//                override fun onGranted(permissions: List<String>, all: Boolean) {
//                    if (all) {
//                        showToast("获取录音和日历权限成功")
//                    } else {
//                        showToast("获取部分权限成功，但部分权限未正常授予")
//                    }
//                }
//
//                override fun onDenied(permissions: List<String>, never: Boolean) {
//                    if (never) {
//                        showToast("被永久拒绝授权，请手动授予录音和日历权限")
//                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
//                        XXPermissions.startPermissionActivity(this@MainActivity, permissions)
//                    } else {
//                        showToast("获取录音和日历权限失败")
//                    }
//                }
//            })

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val nav = Navigation.findNavController(this@MainActivity, R.id.host_fragment)
                if (nav.currentDestination != null && nav.currentDestination?.id != R.id.main_fragment) {
                    //如果当前界面不是主页，那么直接调用返回即可
                    nav.navigateUp()
                } else {
                    //是主页
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        showToast("再按一次退出程序")
                        exitTime = System.currentTimeMillis()
                    } else {
                        finish()
                    }
                }
            }
        })
    }
}