package com.yangguo.jetpack.mvvm.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.guoyang.mvvm.ext.util.toHtml
import com.guoyang.mvvm.ext.view.visibleOrGone
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.ItemProjectBinding
import com.yangguo.jetpack.mvvm.vo.ArterialBean

/***
 *
 * 文章列表适配器
 * @author Yang.Guo on 2021/6/5.
 */
class ArterialAdapter(
    data: MutableList<ArterialBean.Data> = mutableListOf(),
    private val showTag: Boolean = true
) :
    BaseQuickAdapter<ArterialBean.Data, BaseDataBindingHolder<ItemProjectBinding>>(
        R.layout.item_project,
        data
    ), LoadMoreModule {

    init {
        // 设置差异刷新
        setDiffCallback(object : DiffUtil.ItemCallback<ArterialBean.Data>() {
            override fun areItemsTheSame(
                oldItem: ArterialBean.Data,
                newItem: ArterialBean.Data
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ArterialBean.Data,
                newItem: ArterialBean.Data
            ): Boolean {
                return oldItem == newItem
            }
        })
    }

    @SuppressLint("SetTextI18n")
    override fun convert(
        holder: BaseDataBindingHolder<ItemProjectBinding>,
        item: ArterialBean.Data
    ) {
        holder.dataBinding?.run {
            // 设置作者
            authorTv.text = item.author.ifEmpty { item.shareUser }
            // 设置置顶标题是否显示/隐藏
            topTv.visibleOrGone(item.type == 1 && showTag)
            // 设置新是否显示/隐藏
            newTv.visibleOrGone(item.fresh && showTag)
            // 设置tag类型
            typeTv.text = item.tags.firstOrNull()?.name ?: ""
            // 设置tag类型是否显示/隐藏
            typeTv.visibleOrGone(item.tags.isNotEmpty() && showTag)
            // 设置时间
            dateTv.text = item.niceDate
            // 设置图片
            projectIv.load(item.envelopePic)
            // 设置图片是否显示/隐藏
            projectIv.visibleOrGone(item.envelopePic.isNotEmpty())
            // 设置标题
            titleTv.text = item.title
            // 设置内容
            contentTv.text = item.desc.toHtml()
            // 设置内容是否显示/隐藏
            contentTv.visibleOrGone(item.desc.isNotEmpty())
            // 设置分类
            tagTv.text = "${item.superChapterName}·${item.chapterName}".toHtml()
        }
    }
}