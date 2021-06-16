package com.yangguo.base.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.guoyang.mvvm.base.fragment.BaseDbFragment
import com.guoyang.mvvm.base.viewmodel.BaseViewModel
import com.guoyang.mvvm.state.UILoadingState
import com.yangguo.base.ext.dismissLoadingExt
import com.yangguo.base.ext.showLoadingExt

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
 * Created by Yang.Guo on 2021/6/3.
 */
abstract class BaseVMFragment<DB : ViewDataBinding> : BaseDbFragment<DB>() {
    /**
     * 当前Fragment绑定的视图布局
     */
    abstract override fun layoutId(): Int


    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        showLoadingExt(message)
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        dismissLoadingExt()
    }

    /**
     * 将非该Activity绑定的ViewModel添加 loading回调 防止出现请求时不显示 loading 弹窗bug
     * @param viewModels Array<out BaseViewModel>
     */
    protected fun addLoadingObserve(vararg viewModels: BaseViewModel) {
        viewModels.forEach { viewModel ->
            viewModel.loadingChange.observeInFragment(this) {
                when (it) {
                    //显示弹窗
                    is UILoadingState.Start -> {
                        showLoading(it.loadMsg)
                    }
                    //关闭弹窗
                    is UILoadingState.End -> {
                        dismissLoading()
                    }
                }
            }
        }
    }
}