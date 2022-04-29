package com.yangguo.jetpack.mvvm.model

import com.yangguo.jetpack.mvvm.vo.ArterialBean
import com.yangguo.jetpack.mvvm.vo.BannerBean
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/***
 * @author Yang.Guo on 2021/6/2.
 */
class MainRepository @Inject constructor(private val apiService: ApiService) {
    fun getArterialList(page: Int): Flow<List<ArterialBean.Data>> {
        return if (page > 0) {
            apiService.getArterialList(page).map { it.datas }
        } else {
            combine(apiService.getArterialList(page), apiService.getTopArterialList()) { t1, t2 ->
                t1.datas.addAll(0, t2)
                t1.datas
            }
        }
    }

    fun getBannerList(): Flow<List<BannerBean>> {
        return apiService.getBannerList()
    }
}