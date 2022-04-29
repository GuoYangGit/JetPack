package com.yangguo.jetpack

import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.chad.library.adapter.base.module.LoadMoreModuleConfig.defLoadMoreView
import com.guoyang.mvvm.base.BaseApp
import com.guoyang.mvvm.ext.getProcessName
import com.guoyang.mvvm.ext.util.MVVM_LOG
import com.kingja.loadsir.core.LoadSir
import com.tencent.bugly.crashreport.CrashReport
import com.yangguo.base.weight.recyclerview.CustomLoadMoreView
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import javax.inject.Inject


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
@HiltAndroidApp
class MyApplication : BaseApp() {
    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var rxHttpPlugins: RxHttpPlugins

    @Inject
    lateinit var loadSir: LoadSir.Builder

    override fun onCreate() {
        super.onCreate()
        MVVM_LOG = BuildConfig.DEBUG
        rxHttpPlugins.setDebug(BuildConfig.DEBUG)
        loadSir.commit()
        // 在 Application 中配置全局自定义的 LoadMoreView
        defLoadMoreView = CustomLoadMoreView()
        Coil.setImageLoader(imageLoader)

        //初始化Bugly
        initBugly()
    }

    /**
     * 初始化Bugly
     */
    private fun initBugly() {
        val context = applicationContext
        // 获取当前包名
        val packageName = context.packageName
        // 获取当前进程名
        val processName = getProcessName(android.os.Process.myPid())
        // 设置是否为上报进程
        val strategy = CrashReport.UserStrategy(context)
        strategy.isUploadProcess = processName == null || processName == packageName
        // 初始化Bugly
//        Bugly.init(context, if (BuildConfig.DEBUG) "xxx" else "a52f2b5ebb", BuildConfig.DEBUG)
        // bugly应用更新
//        Beta.checkUpgrade(false, true)
    }
}