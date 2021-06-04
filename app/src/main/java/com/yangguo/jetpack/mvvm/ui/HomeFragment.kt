package com.yangguo.jetpack.mvvm.ui

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.yangguo.base.ui.BaseVMFragment
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.FragmentHomeBinding
import com.yangguo.jetpack.databinding.FragmentMainBinding
import com.yangguo.jetpack.mvvm.viewmodel.HomeViewModel
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
class HomeFragment : BaseVMFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.vm = viewModel
        viewModel.getArterialList()
    }
}