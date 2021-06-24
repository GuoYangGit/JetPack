package com.yangguo.jetpack.mvvm.model

import com.yangguo.jetpack.mvvm.vo.ArterialBean
import com.yangguo.jetpack.mvvm.vo.BannerBean
import com.yangguo.jetpack.mvvm.vo.ClassifyBean
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedComponentBuilderEntryPoint
import okhttp3.internal.wait
import rxhttp.startDelay
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse
import javax.inject.Inject
import javax.inject.Singleton

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
@Singleton
class ApiService @Inject constructor() {

    suspend fun getArterialList(page: Int): ArterialBean {
        return RxHttp.get("article/list/$page/json")
            .toResponse<ArterialBean>()
            .startDelay(1000)
            .await()
    }

    suspend fun getTopArterialList(): List<ArterialBean.Data> {
        return RxHttp.get("article/top/json")
            .toResponse<List<ArterialBean.Data>>()
            .await()
    }

    suspend fun getBannerList(): List<BannerBean> {
        return RxHttp.get("banner/json")
            .toResponse<List<BannerBean>>()
            .await()
    }

    suspend fun getProjectTitle(): List<ClassifyBean> {
        return RxHttp.get("project/tree/json")
            .toResponse<List<ClassifyBean>>()
            .await()
    }

}