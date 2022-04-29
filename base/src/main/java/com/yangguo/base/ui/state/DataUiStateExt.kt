package com.yangguo.base.ui.state

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.yangguo.base.ext.view.showError
import com.yangguo.base.ext.view.showLoading

/**
 *
 * @author  yang.guo on 2022/4/29
 */
inline fun <T> DataUiState<T>.doStart(block: () -> Unit) {
    if (this is DataUiState.Start) {
        block.invoke()
    }
}

inline fun <T> DataUiState<T>.doSuccess(block: (data: T?) -> Unit) {
    if (this is DataUiState.Success) {
        block.invoke(data)
    }
}

inline fun <T> DataUiState<T>.doError(block: (throwable: Throwable) -> Unit) {
    if (this is DataUiState.Error) {
        block.invoke(error)
    }
}

fun <T> DataUiState<T>.bindView(
    swipeRefreshLayout: SwipeRefreshLayout? = null,
    adapter: BaseQuickAdapter<*, *>? = null,
    loadService: LoadService<*>? = null
) {
// 判断是否有加载更多
    val isLoadMore = adapter is LoadMoreModule
    doStart {
        if (!refresh) return@doStart
        swipeRefreshLayout?.isRefreshing = true
        if (loadService?.currentCallback == SuccessCallback::class.java) return@doStart
        loadService?.showLoading()
    }
    doSuccess { _ ->
        when {
            refresh -> {
                swipeRefreshLayout?.isRefreshing = false
                loadService?.showSuccess()
            }
            isLoadMore -> {
                adapter?.loadMoreModule?.loadMoreComplete()
            }
        }
    }
    doError { throwable ->
        when {
            refresh -> {
                swipeRefreshLayout?.isRefreshing = false
                if (loadService?.currentCallback == SuccessCallback::class.java) return@doError
                loadService?.showError(throwable.message ?: "")
            }
            isLoadMore -> {
                adapter?.loadMoreModule?.loadMoreFail()
            }
        }
    }
}