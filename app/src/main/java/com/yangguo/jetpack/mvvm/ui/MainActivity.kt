package com.yangguo.jetpack.mvvm.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.gyf.immersionbar.ktx.immersionBar
import com.yangguo.base.ui.BaseVMActivity
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.ActivityMainBinding
import com.yangguo.jetpack.mvvm.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseVMActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.vm = viewModel
        viewModel.getArterialList()
    }
}