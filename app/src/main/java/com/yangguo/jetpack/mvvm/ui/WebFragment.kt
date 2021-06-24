package com.yangguo.jetpack.mvvm.ui

import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import com.guoyang.mvvm.ext.nav
import com.just.agentweb.AgentWeb
import com.yangguo.base.ext.initClose
import com.yangguo.base.ui.BaseVMFragment
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.FragmentWebBinding
import com.yangguo.jetpack.mvvm.viewmodel.WebViewModel
import com.yangguo.jetpack.mvvm.vo.ArterialBean
import com.yangguo.jetpack.mvvm.vo.BannerBean
import com.zackratos.ultimatebarx.ultimatebarx.addNavigationBarBottomPadding
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
class WebFragment : BaseVMFragment<FragmentWebBinding>() {
    private var mAgentWeb: AgentWeb? = null

    private var preWeb: AgentWeb.PreAgentWeb? = null

    private val viewModel: WebViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_web

    override fun initView(savedInstanceState: Bundle?) {
        //点击文章进来的
        arguments?.run {
            getParcelable<ArterialBean.Data>("articleData")?.let {
                viewModel.articleId = it.id
                viewModel.showTitle = it.title
                viewModel.collect = it.collect
                viewModel.url = it.link
//                viewModel.collectType = CollectType.Ariticle.type
            }
            //点击首页轮播图进来的
            getParcelable<BannerBean>("bannerdata")?.let {
                viewModel.articleId = it.id
                viewModel.showTitle = it.title
                //从首页轮播图 没法判断是否已经收藏过，所以直接默认没有收藏
                viewModel.collect = false
                viewModel.url = it.url
//                viewModel.collectType = CollectType.Url.type
            }
        }
        binding.run {
            toolBar.run {
                initClose(viewModel.showTitle, onBack = {
                    nav().navigateUp()
                })
                addStatusBarTopPadding()
            }
            navigationBar.run {
                addNavigationBarBottomPadding()
            }
        }
        preWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.webLayout, FrameLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
    }

    override fun lazyLoadData() {
        //加载网页
        mAgentWeb = preWeb?.go(viewModel.url)
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }
}