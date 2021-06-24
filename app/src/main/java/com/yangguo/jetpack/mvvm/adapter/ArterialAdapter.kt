package com.yangguo.jetpack.mvvm.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.ItemProjectBinding
import com.yangguo.jetpack.mvvm.vo.ArterialBean

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
 * Created by Yang.Guo on 2021/6/5.
 */
class ArterialAdapter(data: MutableList<ArterialBean.Data> = mutableListOf(), private val showTag: Boolean = true) :
    BaseQuickAdapter<ArterialBean.Data, BaseDataBindingHolder<ItemProjectBinding>>(R.layout.item_project, data),
    LoadMoreModule {

    init {
        // 设置差异刷新
        setDiffCallback(object : DiffUtil.ItemCallback<ArterialBean.Data>() {
            override fun areItemsTheSame(oldItem: ArterialBean.Data, newItem: ArterialBean.Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArterialBean.Data, newItem: ArterialBean.Data): Boolean {
                return oldItem == newItem
            }
        })
    }

    override fun convert(holder: BaseDataBindingHolder<ItemProjectBinding>, item: ArterialBean.Data) {
        holder.dataBinding?.run {
            bean = item
            showTag = this@ArterialAdapter.showTag
//            executePendingBindings()
        }
    }
}