package com.yangguo.jetpack.mvvm.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.guoyang.mvvm.ext.util.showToast
import com.tencent.bugly.beta.Beta
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
            .applyStatusBar()
        //进入首页检查更新
//        Beta.checkUpgrade(false, true)

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