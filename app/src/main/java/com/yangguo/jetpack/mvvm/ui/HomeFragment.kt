package com.yangguo.jetpack.mvvm.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.guoyang.mvvm.ext.nav
import com.guoyang.mvvm.ext.navigateAction
import com.guoyang.mvvm.ext.util.showToast
import com.guoyang.mvvm.ext.view.dpi
import com.guoyang.mvvm.ext.view.setNbOnItemClickListener
import com.guoyang.mvvm.network.msg
import com.guoyang.mvvm.state.DataUiState
import com.kingja.loadsir.core.LoadService
import com.yangguo.base.ext.*
import com.yangguo.base.ui.BaseVMFragment
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.FragmentHomeBinding
import com.yangguo.jetpack.mvvm.adapter.ArterialAdapter
import com.yangguo.jetpack.mvvm.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.yangguo.base.weight.recyclerview.SpaceItemDecoration
import com.yangguo.jetpack.mvvm.adapter.BannerAdapter
import com.yangguo.jetpack.mvvm.vo.BannerBean
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zhpan.bannerview.BannerViewPager

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
class HomeFragment : BaseVMFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private var loadService: LoadService<*>? = null

    private val adapter: ArterialAdapter by lazy {
        ArterialAdapter()
    }

    override fun layoutId(): Int = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {
        binding.run {
            toolBar.run {
                init("首页")
                addStatusBarTopPadding()
            }
            recyclerView.initBRVAH(LinearLayoutManager(context), adapter, loadMoreListener = {
                viewModel.getArterialList(false)
            }).run {
                addItemDecoration(SpaceItemDecoration(0, 8 * dpi, false))
            }
            loadService = swipeRefresh.initLoadService {
                viewModel.getArterialList(true)
            }
        }
        adapter.run {
            // 设置空布局
            setEmptyView(R.layout.layout_empty)
            // 设置点击事件
            setNbOnItemClickListener { adapter, _, position ->
                nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                    putParcelable(
                        "articleData",
                        adapter.data[position] as Parcelable?
                    )
                })
            }
        }
    }

    override fun initData() {
        viewModel.getArterialList(true)
        viewModel.arterialList.observeUi(viewLifecycleOwner, {
            when (it) {
                is DataUiState.Success -> {
                    if (it.isRefresh) {
                        adapter.setDiffNewData(it.data?.toMutableList())
                    } else if (!it.data.isNullOrEmpty()) {
                        adapter.addData(it.data!!)
                    }
                }
                is DataUiState.Error -> {
                    showToast(it.error.msg)
                }
                else -> {
                }
            }
        }, binding.swipeRefresh, adapter, loadService)
        viewModel.getBannerList()
        viewModel.bannerData.observe(viewLifecycleOwner, {
            when (it) {
                is DataUiState.Success -> {
                    if (adapter.hasHeaderLayout()) return@observe
                    val headView =
                        LayoutInflater.from(context)
                            .inflate(R.layout.include_banner, binding.recyclerView, false).run {
                                findViewById<BannerViewPager<BannerBean>>(R.id.banner_view).apply {
                                    adapter = BannerAdapter()
                                    setLifecycleRegistry(lifecycle)
                                    setOnPageClickListener { _, position ->
                                        nav().navigateAction(
                                            R.id.action_to_webFragment,
                                            Bundle().apply {
                                                putParcelable(
                                                    "bannerdata",
                                                    data?.get(position)
                                                )
                                            })
                                    }
                                    create(it.data)
                                }
                            }
                    adapter.addHeaderView(headView)
                }
                else -> {
                }
            }
        })
    }
}