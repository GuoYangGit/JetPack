package com.yangguo.base.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yangguo.base.ui.state.DataUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 *
 * @author  yang.guo on 2022/4/29
 */
fun <T> Flow<T>.bindUiState(
    uiState: MutableLiveData<DataUiState<T>>,
    isRefresh: Boolean = false
): Flow<T> {
    return this
        .onStart {
            uiState.postValue(DataUiState.onStart(isRefresh = isRefresh))
        }.catch {
            uiState.postValue(DataUiState.onError(it, isRefresh))
        }
}

fun <T> Flow<T>.bindUiState(
    uiState: MutableLiveData<DataUiState<T>>,
    viewModel: ViewModel,
    isRefresh: Boolean = false
) {
    viewModel.viewModelScope.launch {
        this@bindUiState.bindUiState(uiState, isRefresh)
            .collect {
                uiState.postValue(DataUiState.onSuccess(it, isRefresh))
            }
    }
}