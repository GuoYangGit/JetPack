package com.yangguo.jetpack.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.guoyang.mvvm.base.viewmodel.BaseViewModel
import com.guoyang.mvvm.ext.requestWithUiDataState
import com.guoyang.mvvm.state.DataUiState
import com.yangguo.jetpack.mvvm.model.MainRepository
import com.yangguo.jetpack.mvvm.vo.ArterialBean
import com.yangguo.jetpack.mvvm.vo.BannerBean
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
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {
    val arterialList: MutableLiveData<DataUiState<List<ArterialBean.Data>>> = MutableLiveData()

    //首页轮播图数据
    var bannerData: MutableLiveData<DataUiState<List<BannerBean>>> = MutableLiveData()

    var page: Int = 0

    fun getArterialList(isRefresh: Boolean) {
        requestWithUiDataState(arterialList, {
            if (isRefresh) page = 0 else page += 1
            val result = mainRepository.getArterialList(page)
            return@requestWithUiDataState result.datas
        }, isRefresh)
    }

    fun getBannerList() {
        requestWithUiDataState(bannerData, {
            return@requestWithUiDataState mainRepository.getBannerList()
        })
    }
}