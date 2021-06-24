package com.yangguo.jetpack.mvvm.adapter

import android.widget.ImageView
import coil.load
import com.yangguo.jetpack.R
import com.yangguo.jetpack.mvvm.vo.BannerBean
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class BannerAdapter : BaseBannerAdapter<BannerBean>() {

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.banner_itemhome
    }

    override fun bindData(
        holder: BaseViewHolder<BannerBean>?,
        data: BannerBean?,
        position: Int,
        pageSize: Int
    ) {
        val imageView = holder?.findViewById<ImageView>(R.id.iv_banner)
        imageView?.load(data?.imagePath)
    }
}