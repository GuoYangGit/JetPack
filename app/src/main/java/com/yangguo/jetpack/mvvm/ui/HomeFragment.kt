package com.yangguo.jetpack.mvvm.ui

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.guoyang.mvvm.ext.util.showToast
import com.guoyang.mvvm.ext.view.dpi
import com.guoyang.mvvm.network.msg
import com.yangguo.base.ext.init
import com.yangguo.base.network.DataUiState
import com.yangguo.base.ui.BaseVMFragment
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.FragmentHomeBinding
import com.yangguo.jetpack.mvvm.adapter.ArterialAdapter
import com.yangguo.jetpack.mvvm.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import me.hgj.jetpackmvvm.demo.app.weight.recyclerview.SpaceItemDecoration

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

    private val adapter: ArterialAdapter by lazy {
        ArterialAdapter(showTag = true)
    }

    override fun layoutId(): Int = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {
        binding.recyclerView.init(LinearLayoutManager(context), adapter).run {
            addItemDecoration(SpaceItemDecoration(0, 8 * dpi, false))
        }
        binding.swipeRefresh.init {
            viewModel.getArterialList(true)
        }
    }

    override fun initData() {
        viewModel.arterialList.observe(this) {
            when (it) {
                is DataUiState.Start -> {
                    binding.swipeRefresh.isRefreshing = true
                }
                is DataUiState.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    if (it.isRefresh) {
                        adapter.setDiffNewData(it.data?.toMutableList())
                    } else if (!it.data.isNullOrEmpty()) {
                        adapter.addData(it.data!!)
                    }
                }
                is DataUiState.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    showToast(it.error.msg)
                }
            }
        }
    }
}