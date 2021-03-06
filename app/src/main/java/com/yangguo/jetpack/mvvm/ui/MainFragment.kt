package com.yangguo.jetpack.mvvm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.guoyang.mvvm.ext.util.logD
import com.yangguo.base.ui.BaseVMFragment
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.FragmentMainBinding
import com.zackratos.ultimatebarx.ultimatebarx.addNavigationBarBottomPadding
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

    override fun layoutId(): Int = R.layout.fragment_main

    override fun initView(savedInstanceState: Bundle?) {
        binding?.run {
            // 设置ViewPager
            mainViewpager.run {
                // 设置不可以滑动
                isUserInputEnabled = false
                // 设置适配器
                adapter =
                    object :
                        FragmentStateAdapter(childFragmentManager, viewLifecycleOwner.lifecycle) {
                        override fun getItemCount(): Int = 5
                        override fun createFragment(position: Int): Fragment {
                            return when (position) {
                                0 -> HomeFragment()
                                1 -> ProjectFragment()
                                else -> MeFragment()
                            }
                        }
                    }
            }
            // 设置底部导航栏
            bottomNavigationView.run {
                // 设置距离底部导航栏的padding
                addNavigationBarBottomPadding()
                // 设置选中监听
                setOnItemSelectedListener {
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