package com.yangguo.jetpack.mvvm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.guoyang.mvvm.base.appContext
import com.yangguo.base.ui.BaseVMFragment
import com.yangguo.base.util.SettingUtil
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

/***
 *
 *   █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 *  ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 *  ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 *  ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 *           ░     ░ ░      ░  ░
 *
 * Created by Yang.Guo on 2021/6/4.
 */
@AndroidEntryPoint
class MainFragment : BaseVMFragment<FragmentMainBinding>() {
    private val fragmentList: List<Fragment> by lazy {
        listOf(
            HomeFragment(),
            HomeFragment(),
            HomeFragment(),
            HomeFragment(),
            HomeFragment()
        )
    }

    override fun layoutId(): Int = R.layout.fragment_main

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.run {
            mainViewpager.run {
                isUserInputEnabled = false // 设置不可以滑动
                adapter = object : FragmentStateAdapter(this@MainFragment) {
                    override fun getItemCount(): Int = fragmentList.size

                    override fun createFragment(position: Int): Fragment = fragmentList[position]
                }
            }

            mainBtn.run {
                enableAnimation(true) // 点击动画(默认开启)
                enableShiftingMode(false) // 导航条位移模式
                enableItemShiftingMode(true) // 子菜单位移模式
                setTextSize(12F)
                setOnNavigationItemSelectedListener {
                    when (it.itemId) {
                        R.id.menu_main -> mainViewpager.setCurrentItem(0, false)
                        R.id.menu_project -> mainViewpager.setCurrentItem(1, false)
                        R.id.menu_system -> mainViewpager.setCurrentItem(2, false)
                        R.id.menu_public -> mainViewpager.setCurrentItem(3, false)
                        R.id.menu_me -> mainViewpager.setCurrentItem(4, false)
                    }
                    true
                }
            }
        }
    }
}