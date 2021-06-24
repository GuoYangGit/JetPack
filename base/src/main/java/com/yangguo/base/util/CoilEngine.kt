package com.yangguo.base.util

import android.content.Context
import android.widget.ImageView
import coil.load
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.listener.OnImageCompleteCallback
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView

object CoilEngine : ImageEngine {
    override fun loadImage(context: Context, url: String, imageView: ImageView) {
        imageView.load(url)
    }

    override fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        longImageView: SubsamplingScaleImageView?,
        callback: OnImageCompleteCallback?
    ) {
        imageView.load(url)
    }

    override fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        longImageView: SubsamplingScaleImageView?
    ) {
        imageView.load(url)
    }

    override fun loadFolderImage(context: Context, url: String, imageView: ImageView) {
        imageView.load(url)
    }

    override fun loadAsGifImage(context: Context, url: String, imageView: ImageView) {
        imageView.load(url)
    }

    override fun loadGridImage(context: Context, url: String, imageView: ImageView) {
        imageView.load(url)
    }
}