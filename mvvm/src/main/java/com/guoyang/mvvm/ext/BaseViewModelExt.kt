package com.guoyang.mvvm.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.rxLifeScope
import com.guoyang.mvvm.base.viewmodel.BaseViewModel
import com.guoyang.mvvm.state.DataUiState
import com.guoyang.mvvm.state.UILoadingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

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
 * Created by Yang.Guo on 2021/6/3.
 */

fun <T> BaseViewModel.requestWithUiDataState(
    uiDataState: MutableLiveData<DataUiState<T>>,
    block: suspend () -> T?,
    isRefresh: Boolean = true,
    onError: ((Throwable) -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onFinally: (() -> Unit)? = null
) {
    launch({
        val result = block.invoke()
        uiDataState.postValue(DataUiState.onSuccess(result, isRefresh))
    }, {
        uiDataState.postValue(DataUiState.onError(it, isRefresh))
        onError?.invoke(it)
    }, {
        uiDataState.postValue(DataUiState.onStart(isRefresh = isRefresh))
        onStart?.invoke()
    }, onFinally)
}

fun BaseViewModel.requestWithLoading(
    block: suspend CoroutineScope.() -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onFinally: (() -> Unit)? = null,
    loadingMessage: String = "请求网络中..."
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return launch(block, onError, {
        loadingChange.postValue(UILoadingState.onShow(loadingMessage))
        onStart?.invoke()
    }, {
        loadingChange.postValue(UILoadingState.onEnd())
        onFinally?.invoke()
    })
}

/**
 *  调用携程
 * @param block 操作耗时操作任务
 * @param success 成功回调
 * @param error 失败回调 可不给
 */
fun BaseViewModel.launch(
    block: suspend CoroutineScope.() -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onFinally: (() -> Unit)? = null
): Job = rxLifeScope.launch(block, onError, onStart, onFinally)