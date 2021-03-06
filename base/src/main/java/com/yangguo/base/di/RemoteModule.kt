package com.yangguo.base.di

import com.guoyang.mvvm.network.interceptor.LogInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.ssl.HttpsUtils
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLSession

/***
 *
 * App通用网络模块
 * @author Yang.Guo on 2021/6/2.
 */
@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    /**
     * 提供OkHttpClient
     */
    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val sslParams = HttpsUtils.getSslSocketFactory()
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(LogInterceptor()) // 添加Log日志打印拦截器
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) //添加信任证书
            .hostnameVerifier { _: String?, _: SSLSession? -> true } //忽略host验证
            .build()
    }

    /**
     * 提供RxHttp插件
     * @param okHttpClient OkHttpClient
     */
    @Provides
    @Singleton
    fun provideRxHttpPlugins(okHttpClient: OkHttpClient): RxHttpPlugins {
        return RxHttpPlugins.init(okHttpClient)
    }
}