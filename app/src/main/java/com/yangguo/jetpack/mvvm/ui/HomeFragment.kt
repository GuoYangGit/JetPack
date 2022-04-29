package com.yangguo.jetpack.mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.guoyang.mvvm.ext.nav
import com.guoyang.mvvm.ext.navigateAction
import com.guoyang.mvvm.ext.util.showToast
import com.guoyang.mvvm.ext.view.dpi
import com.guoyang.mvvm.ext.view.setNbOnItemClickListener
import com.guoyang.mvvm.network.msg
import com.kingja.loadsir.core.LoadService
import com.yangguo.base.ext.view.bindBaseAdapter
import com.yangguo.base.ext.view.bindTitle
import com.yangguo.base.ext.view.initLoadService
import com.yangguo.base.ui.BaseVMFragment
import com.yangguo.base.ui.state.bindView
import com.yangguo.base.ui.state.doError
import com.yangguo.base.ui.state.doSuccess
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.FragmentHomeBinding
import com.yangguo.jetpack.mvvm.adapter.ArterialAdapter
import com.yangguo.jetpack.mvvm.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.yangguo.base.weight.recyclerview.SpaceItemDecoration
import com.yangguo.jetpack.mvvm.adapter.BannerAdapter
import com.yangguo.jetpack.mvvm.vo.ArterialBean
import com.yangguo.jetpack.mvvm.vo.BannerBean
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zhpan.bannerview.BannerViewPager

/***
 *
 * 首页Fragment
 * @author Yang.Guo on 2021/6/4.
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
        binding?.run {
            toolBar.run {
                addStatusBarTopPadding()
                bindTitle("首页")
            }
            val linearLayoutManager = LinearLayoutManager(context)
            recyclerView.bindBaseAdapter(linearLayoutManager, adapter, {
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
            setNbOnItemClickListener { _, _, position ->
                if (position !in data.indices) return@setNbOnItemClickListener
                val item = data[position] as? ArterialBean.Data ?: return@setNbOnItemClickListener
                nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                    putParcelable("articleData", item)
                })
            }
        }
        viewModel.apply {
            arterialList.observe(viewLifecycleOwner) {
                it.bindView(binding?.swipeRefresh, adapter, loadService)
                it.doSuccess { data ->
                    if (it.refresh) {
                        adapter.setDiffNewData(data?.toMutableList())
                    } else if (!data.isNullOrEmpty()) {
                        adapter.addData(data)
                    }
                }
                it.doError { throwable ->
                    showToast(throwable.msg)
                }
            }
            bannerData.observe(viewLifecycleOwner) {
                it.doSuccess { data ->
                    if (adapter.hasHeaderLayout()) return@doSuccess
                    val headView =
                        LayoutInflater.from(context)
                            .inflate(R.layout.include_banner, binding?.recyclerView, false).run {
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
                                    create(data)
                                }
                            }
                    adapter.addHeaderView(headView)
                }
            }
        }
    }

    override fun lazyLoadData() {
        viewModel.run {
            getArterialList(true)
            getBannerList()
        }
    }
}