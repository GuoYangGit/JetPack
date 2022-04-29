package com.yangguo.base.ext.view

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.chad.library.adapter.base.module.LoadMoreModule

/**
 * RecyclerView扩展类
 * @author  yang.guo on 2022/4/29
 */

/**
 * 绑定普通的Recyclerview
 */
fun RecyclerView.bindTitle(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): RecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}

/**
 * RecyclerView绑定BaseQuickAdapter
 * @link https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
fun RecyclerView.bindBaseAdapter(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: BaseQuickAdapter<*, *>,
    loadMoreListener: OnLoadMoreListener? = null,
    isScroll: Boolean = true
): RecyclerView {
    bindTitle(layoutManger, bindAdapter, isScroll)
    // 这里需要判断Adapter是否实现了LoadMoreModule接口
    if (bindAdapter is LoadMoreModule)
        bindAdapter.loadMoreModule.setOnLoadMoreListener(loadMoreListener)
    return this
}