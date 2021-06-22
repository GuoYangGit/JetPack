package com.yangguo.jetpack.mvvm.ui

import android.os.Bundle
import android.os.Parcelable
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
import com.kingja.loadsir.core.LoadSir
import com.yangguo.base.ext.*
import com.yangguo.base.ui.BaseVMFragment
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.FragmentHomeBinding
import com.yangguo.jetpack.mvvm.adapter.ArterialAdapter
import com.yangguo.jetpack.mvvm.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.yangguo.base.weight.recyclerview.SpaceItemDecoration
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding

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
        ArterialAdapter(showTag = true)
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
                addItemDecoration(SpaceItemDecoration(0, 8 * dpi, true))
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
    }
}