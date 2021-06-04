package com.yangguo.jetpack.mvvm.ui

import android.os.Bundle
import com.yangguo.base.ui.BaseVMActivity
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseVMActivity<ActivityMainBinding>() {

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
    }
}