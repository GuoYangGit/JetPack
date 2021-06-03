package com.yangguo.jetpack.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.rxLifeScope
import com.guoyang.mvvm.base.viewmodel.BaseViewModel
import com.guoyang.mvvm.ext.request
import com.guoyang.mvvm.ext.requestWithLoading
import com.guoyang.mvvm.ext.util.logD
import com.yangguo.jetpack.mvvm.model.MainRepository
import com.yangguo.jetpack.mvvm.vo.ArterialBean
import com.yangguo.jetpack.mvvm.vo.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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
 * Created by Yang.Guo on 2021/5/31.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {
    val arterialList: MutableLiveData<Data> = MutableLiveData()


    fun getArterialList() {
        requestWithLoading({
            val result = mainRepository.getArterialList(0)
            result.datas?.let {
                arterialList.postValue(it[0])
            }
        })
    }
}