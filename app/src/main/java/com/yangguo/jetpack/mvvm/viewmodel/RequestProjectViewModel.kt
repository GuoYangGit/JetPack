package com.yangguo.jetpack.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.guoyang.mvvm.base.viewmodel.BaseViewModel
import com.guoyang.mvvm.ext.requestWithUiDataState
import com.guoyang.mvvm.state.DataUiState
import com.yangguo.jetpack.mvvm.model.ProjectRepository
import com.yangguo.jetpack.mvvm.vo.ClassifyBean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RequestProjectViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : BaseViewModel() {

    val titleData: MutableLiveData<DataUiState<List<ClassifyBean>>> = MutableLiveData()

    fun getProjectTitle() {
        requestWithUiDataState(titleData, {
            return@requestWithUiDataState projectRepository.getProjectTitle()
        })
    }
}