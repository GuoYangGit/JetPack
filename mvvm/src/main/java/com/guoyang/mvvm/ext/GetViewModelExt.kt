package com.guoyang.mvvm.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.guoyang.mvvm.base.BaseApp
import com.guoyang.mvvm.base.viewmodel.BaseViewModel

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
 * Created by Yang.Guo on 2021/6/2.
 */

/**
 * 在Activity中得到Application上下文的ViewModel
 */
inline fun <reified VM : BaseViewModel> AppCompatActivity.getAppViewModel(): VM {
    (this.application as? BaseApp).let {
        if (it == null) {
            throw NullPointerException("你的Application没有继承框架自带的BaseApp类，暂时无法使用getAppViewModel该方法")
        } else {
            return it.getAppViewModelProvider().get(VM::class.java)
        }
    }
}

/**
 * 在Fragment中得到Application上下文的ViewModel
 * 提示，在fragment中调用该方法时，请在该Fragment onCreate以后调用或者请用by lazy方式懒加载初始化调用，不然会提示requireActivity没有导致错误
 */
inline fun <reified VM : BaseViewModel> Fragment.getAppViewModel(): VM {
    (this.requireActivity().application as? BaseApp).let {
        if (it == null) {
            throw NullPointerException("你的Application没有继承框架自带的BaseApp类，暂时无法使用getAppViewModel该方法")
        } else {
            return it.getAppViewModelProvider().get(VM::class.java)
        }
    }
}