package com.yangguo.jetpack.util


import android.content.Context
import android.graphics.Bitmap
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig


object FrescoUtils {

    fun init(context: Context?) {
        if (!Fresco.hasBeenInitialized()) {
            val config = ImagePipelineConfig.newBuilder(context)
                .setResizeAndRotateEnabledForNetwork(true)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setDownsampleEnabled(true)
                .build()
            Fresco.initialize(context, config)
        }
    }
}
