package com.yangguo.jetpack.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guoyang.mvvm.base.viewmodel.BaseViewModel
import com.yangguo.base.ui.state.DataUiState
import com.yangguo.base.ext.bindUiState
import com.yangguo.jetpack.mvvm.model.ProjectRepository
import com.yangguo.jetpack.mvvm.vo.ClassifyBean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RequestProjectViewModel @Inject constructor(
    private val repository: ProjectRepository
) : BaseViewModel() {

    private val _uiState = MutableLiveData<DataUiState<List<ClassifyBean>>>()

    val titleData: LiveData<DataUiState<List<ClassifyBean>>> = _uiState

    fun getProjectTitle() {
        repository.getProjectTitle()
            .bindUiState(_uiState, this)
    }
}