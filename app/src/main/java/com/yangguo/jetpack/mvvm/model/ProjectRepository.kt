package com.yangguo.jetpack.mvvm.model

import com.yangguo.jetpack.mvvm.vo.ClassifyBean
import javax.inject.Inject

class ProjectRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getProjectTitle(): List<ClassifyBean> {
        return apiService.getProjectTitle()
    }
}