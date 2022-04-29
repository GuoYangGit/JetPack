package com.yangguo.jetpack.mvvm.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yangguo.base.ext.view.bindViewPager2
import com.yangguo.base.ui.BaseVMFragment
import com.yangguo.base.ui.state.doSuccess
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.FragmentProjectBinding
import com.yangguo.jetpack.mvvm.viewmodel.RequestProjectViewModel
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
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
class ProjectFragment : BaseVMFragment<FragmentProjectBinding>() {

    //标题集合
    private var titleList: ArrayList<String> = arrayListOf()

    private val viewModel: RequestProjectViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_project

    @SuppressLint("NotifyDataSetChanged")
    override fun initView(savedInstanceState: Bundle?) {
        binding?.run {
            magicIndicator.run {
                addStatusBarTopPadding()
                bindViewPager2(viewPager, titleList)
            }
            viewPager.run {
                //设置适配器
                adapter = object : FragmentStateAdapter(this@ProjectFragment) {
                    override fun getItemCount() = titleList.size
                    override fun createFragment(position: Int) = MeFragment()
                }
            }
        }

        viewModel.titleData.observe(viewLifecycleOwner) {
            it.doSuccess { data ->
                titleList.clear()
                data?.forEach { bean ->
                    titleList.add(bean.name)
                }
                binding?.run {
                    magicIndicator.navigator.notifyDataSetChanged()
                    viewPager.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun lazyLoadData() {
        //请求标题数据
        viewModel.getProjectTitle()
    }
}