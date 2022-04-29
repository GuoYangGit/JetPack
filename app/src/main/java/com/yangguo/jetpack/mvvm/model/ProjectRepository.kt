package com.yangguo.jetpack.mvvm.model

import com.yangguo.jetpack.mvvm.vo.ClassifyBean
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/***
 * @author Yang.Guo on 2021/6/2.
 */
class ProjectRepository @Inject constructor(private val apiService: ApiService) {

    /**
     * 获取项目分类
     */
    fun getProjectTitle(): Flow<List<ClassifyBean>> {
        return apiService.getProjectTitle()
    }
}