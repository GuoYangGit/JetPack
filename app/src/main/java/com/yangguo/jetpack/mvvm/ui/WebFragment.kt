package com.yangguo.jetpack.mvvm.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.just.agentweb.AgentWeb
import com.yangguo.base.ui.BaseVMFragment
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.FragmentWebBinding
import com.yangguo.jetpack.mvvm.viewmodel.WebViewModel
import com.yangguo.jetpack.mvvm.vo.ArterialBean
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
        binding.webLayout.run {
            addStatusBarTopPadding()
            addNavigationBarBottomPadding()
        }
        //点击文章进来的
        arguments?.run {
            getParcelable<ArterialBean.Data>("articleData")?.let {
                viewModel.articleId = it.id
                viewModel.showTitle = it.title
                viewModel.collect = it.collect
                viewModel.url = it.link
//                viewModel.collectType = CollectType.Ariticle.type
            }
        }
        preWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.webLayout, LinearLayout.LayoutParams(-1, -1))
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