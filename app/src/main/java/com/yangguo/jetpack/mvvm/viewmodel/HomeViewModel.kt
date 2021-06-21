package com.yangguo.jetpack.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.guoyang.mvvm.base.viewmodel.BaseViewModel
import com.guoyang.mvvm.ext.launch
import com.guoyang.mvvm.ext.requestWithUiDataState
import com.guoyang.mvvm.state.DataUiState
import com.yangguo.jetpack.mvvm.model.MainRepository
import com.yangguo.jetpack.mvvm.vo.ArterialBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    var page: Int = 0

    fun getArterialList(isRefresh: Boolean) {
        requestWithUiDataState(arterialList, {
            if (isRefresh) page = 0 else page += 1
            delay(1000)
//            if (page >= 0) throw Throwable("没有更多数据")
            val result = mainRepository.getArterialList(page)
            return@requestWithUiDataState result.datas
        }, isRefresh)
    }
}