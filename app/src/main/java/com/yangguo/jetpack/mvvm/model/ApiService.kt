package com.yangguo.jetpack.mvvm.model

import com.yangguo.jetpack.mvvm.vo.ArterialBean
import com.yangguo.jetpack.mvvm.vo.BannerBean
import com.yangguo.jetpack.mvvm.vo.ClassifyBean
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse
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

    /**
     * 获取文章列表
     */
    fun getArterialList(page: Int): Flow<ArterialBean> {
        return RxHttp.get("article/list/$page/json")
            .toFlowResponse()
    }

    /**
     * 获取置顶文章列表
     */
    fun getTopArterialList(): Flow<List<ArterialBean.Data>> {
        return RxHttp.get("article/top/json")
            .toFlowResponse()
    }

    /**
     * 获取banner集合
     */
    fun getBannerList(): Flow<List<BannerBean>> {
        return RxHttp.get("banner/json")
            .toFlowResponse()
    }

    /**
     * 获取项目标题
     */
    fun getProjectTitle(): Flow<List<ClassifyBean>> {
        return RxHttp.get("project/tree/json")
            .toFlowResponse()
    }

}