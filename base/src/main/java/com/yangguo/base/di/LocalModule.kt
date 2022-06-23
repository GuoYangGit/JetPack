package com.yangguo.base.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/***
 *
 * App本地通用模块
 * @author Yang.Guo on 2021/6/2.
 */
@Module
@InstallIn(SingletonComponent::class)
class LocalModule