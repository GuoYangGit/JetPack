package com.yangguo.base.util

import android.content.Context
import android.widget.ImageView
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.listener.OnImageCompleteCallback
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView
import com.yangguo.base.ext.loadUrl

object CoilEngine : ImageEngine {
    override fun loadImage(context: Context, url: String, imageView: ImageView) {
        imageView.loadUrl(url)
    }

    override fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        longImageView: SubsamplingScaleImageView?,
        callback: OnImageCompleteCallback?
    ) {
        imageView.loadUrl(url)
    }

    override fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        longImageView: SubsamplingScaleImageView?
    ) {
        imageView.loadUrl(url)
    }

    override fun loadFolderImage(context: Context, url: String, imageView: ImageView) {
        imageView.loadUrl(url)
    }

    override fun loadAsGifImage(context: Context, url: String, imageView: ImageView) {
        imageView.loadUrl(url)
    }

    override fun loadGridImage(context: Context, url: String, imageView: ImageView) {
        imageView.loadUrl(url)
    }
}