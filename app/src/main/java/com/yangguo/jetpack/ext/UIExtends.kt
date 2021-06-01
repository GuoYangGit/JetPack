package com.yangguo.jetpack.ext

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Animatable
import android.net.Uri
import androidx.annotation.DrawableRes
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.controller.ControllerListener
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.fresco.animation.drawable.AnimatedDrawable2
import com.facebook.fresco.animation.drawable.AnimationListener
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder

/***
 * You may think you know what the following code does.
 * But you dont. Trust me.
 * Fiddle with it, and youll spend many a sleepless
 * night cursing the moment you thought youd be clever
 * enough to "optimize" the code below.
 * Now close this file and go play with something else.
 */
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
 * Created by yz on 2021/5/26.
 * github https://github.com/GuoYangGit
 * QQ:352391291
 */
fun SimpleDraweeView.loadAvatar(url: String?) {
    try {
        if (url.isNullOrEmpty()) {
            setImageURI("")
            return
        }
        val uri = Uri.parse(url)
        val request: ImageRequest = ImageRequestBuilder.newBuilderWithSource(uri).build()
        val controller = Fresco.newDraweeControllerBuilder()
            .setOldController(controller)
            // 根据图片设置ScaleType方式
            .setControllerListener(object : ControllerListener<ImageInfo> {
                override fun onFailure(id: String?, throwable: Throwable?) {
                }

                override fun onRelease(id: String?) {
                }

                override fun onSubmit(id: String?, callerContext: Any?) {
                }

                override fun onIntermediateImageSet(id: String?, imageInfo: ImageInfo?) {
                }

                override fun onIntermediateImageFailed(id: String?, throwable: Throwable?) {
                }

                override fun onFinalImageSet(
                    id: String?,
                    imageInfo: ImageInfo?,
                    animatable: Animatable?
                ) {
                    val width = imageInfo?.width?.toFloat() ?: 0f
                    val height = imageInfo?.height?.toFloat() ?: 0f
                    if ((width / height) < 0.75f) {
                        hierarchy.actualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP
                    } else {
                        hierarchy.actualImageScaleType = ScalingUtils.ScaleType.FIT_CENTER
                    }
                }
            })
            .setImageRequest(request)
            .build()
        setController(controller)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun SimpleDraweeView.loadAnimationImage(
    @DrawableRes resId: Int,
    onAnimationEnd: (() -> Unit)? = null
) {
    val uri = Uri.parse(imageTranslateUri(this.context, resId))
    val controller: DraweeController = Fresco.newDraweeControllerBuilder()
        .setUri(uri)
        .setAutoPlayAnimations(true)
        .setOldController(this.controller)
        .setControllerListener(object : BaseControllerListener<ImageInfo>() {
            override fun onFinalImageSet(
                id: String?,
                imageInfo: ImageInfo?,
                animatable: Animatable?
            ) {
                if (animatable != null && !animatable.isRunning) {
                    animatable.start()
                    val animatedDrawable2 = animatable as AnimatedDrawable2
                    animatedDrawable2.setAnimationListener(object : AnimationListener {
                        override fun onAnimationRepeat(drawable: AnimatedDrawable2?) {
                        }

                        override fun onAnimationStart(drawable: AnimatedDrawable2?) {
                        }

                        override fun onAnimationFrame(
                            drawable: AnimatedDrawable2?,
                            frameNumber: Int
                        ) {
                        }

                        override fun onAnimationStop(drawable: AnimatedDrawable2?) {
                            if (this@loadAnimationImage.isAttachedToWindow) {
                                onAnimationEnd?.invoke()
                            }
                        }

                        override fun onAnimationReset(drawable: AnimatedDrawable2?) {
                        }

                    })
                }
            }
        })
        .build()
    this.controller = controller
}

fun imageTranslateUri(context: Context, resId: Int): String {
    val res: Resources = context.resources
    val uri = Uri.parse(
        ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + res.getResourcePackageName(resId) + "/" + res.getResourceTypeName(
            resId
        ) + "/" + res.getResourceEntryName(resId)
    )
    return uri.toString()
}