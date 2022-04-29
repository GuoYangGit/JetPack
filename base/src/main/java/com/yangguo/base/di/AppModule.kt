package com.yangguo.base.di

import android.app.Application
import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.decode.VideoFrameDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import com.kingja.loadsir.core.LoadSir
import com.yangguo.base.weight.loadcallback.EmptyCallback
import com.yangguo.base.weight.loadcallback.ErrorCallback
import com.yangguo.base.weight.loadcallback.LoadingCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/***
 *
 * App通用注入Module
 * @author Yang.Guo on 2021/6/2.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * 提供ImageLoader
     * @param appContext Application上下文
     */
    @Provides
    @Singleton
    fun provideImageLoader(appContext: Application): ImageLoader {
        return ImageLoader.Builder(appContext)
            .availableMemoryPercentage(0.25)
            .componentRegistry {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder(appContext))
                } else {
                    add(GifDecoder())
                }
                add(SvgDecoder(appContext))
                add(VideoFrameFileFetcher(appContext))
                add(VideoFrameUriFetcher(appContext))
                add(VideoFrameDecoder(appContext))
            }
            .crossfade(true)
            .build()
    }


    /**
     * 提供LoadSir
     */
    @Provides
    @Singleton
    fun provideLoadSirBuild(): LoadSir.Builder {
        //界面加载管理 初始化
        return LoadSir.beginBuilder()
            .addCallback(LoadingCallback())//加载
            .addCallback(ErrorCallback())//错误
            .addCallback(EmptyCallback())//空
            .setDefaultCallback(LoadingCallback::class.java)//设置默认加载状态页
    }
}