package com.yangguo.jetpack.mvvm.ui

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.guoyang.mvvm.state.DataUiState
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.yangguo.base.ext.bindViewPager2
import com.yangguo.base.ui.BaseVMFragment
import com.yangguo.base.util.CoilEngine
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.FragmentMeBinding
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
    private var mTitleList: ArrayList<String> = arrayListOf()

    private val requestProjectViewModel: RequestProjectViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_project

    override fun initView(savedInstanceState: Bundle?) {
        binding.run {
            magicIndicator.run {
                addStatusBarTopPadding()
                bindViewPager2(viewPager, mTitleList)
            }
            viewPager.run {
                //设置适配器
                adapter = object : FragmentStateAdapter(this@ProjectFragment) {
                    override fun createFragment(position: Int) = MeFragment()
                    override fun getItemCount() = mTitleList.size
                }
            }
        }
    }

    override fun lazyLoadData() {
        //请求标题数据
        requestProjectViewModel.getProjectTitle()
    }

    override fun initData() {
        requestProjectViewModel.titleData.observe(viewLifecycleOwner) {
            when (it) {
                is DataUiState.Success -> {
                    mTitleList.clear()
                    it.data?.map { bean ->
                        mTitleList.add(bean.name)
                    }
                    binding.run {
                        magicIndicator.navigator.notifyDataSetChanged()
                        viewPager.adapter?.notifyDataSetChanged()
                    }
                }
                else -> {

                }
            }
        }
    }
}