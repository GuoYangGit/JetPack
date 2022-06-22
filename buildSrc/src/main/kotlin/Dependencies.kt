/**
 * 统一版本号配置类
 * @author  yang.guo on 2022/4/29
 */
object Versions {
    // gradle版本号
    const val gradleVersion = "7.0.4"

    // 编译版本
    const val compileSdk = 31

    // 最低版本
    const val minSdk = 19

    // 目标版本
    const val targetSdk = 31

    // 构建版本
    const val buildToolsVersion = "30.0.3"


    // 注入框架hilt版本
    const val hiltVersion = "2.37"

    // 生命周期lifecycle版本
    const val lifecycleVersion = "2.4.1"

    // Navigation版本
    const val navigationVersion = "2.4.2"

    // kotlin版本
    const val kotlinVersion = "1.6.21"

    // 协程版本
    const val coroutinesVersion = "1.5.2"

    // coil版本
    const val coilVersion = "1.4.0"
}

object Libs {
    // gradle依赖
    const val plugin = "com.android.tools.build:gradle:${Versions.gradleVersion}"

    // kotlin依赖
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"

    // kotlin协程
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"

    // AndroidView
    const val appcompat = "androidx.appcompat:appcompat:1.4.1"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"
    const val material = "com.google.android.material:material:1.5.0"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    // AndroidX
    const val androidxCore = "androidx.core:core-ktx:1.7.0"
    const val androidxFragment = "androidx.fragment:fragment-ktx:1.4.1"

    // 注入框架Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"

    // 生命周期Lifecycle
    const val lifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"
    const val lifecycleCommon =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleVersion}"
    const val lifecycleProcessor =
        "androidx.lifecycle:lifecycle-process:${Versions.lifecycleVersion}"
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"

    const val startup = "androidx.startup:startup-runtime:1.1.1"

    // navigation
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

    // 集成测试
    const val testJunit = "junit:junit:4.13.2"
    const val androidJunit = "androidx.test.ext:junit:1.1.3"
    const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
    const val leakcanaryAndroid = "com.squareup.leakcanary:leakcanary-android:2.8.1"

    // coil
    const val coil = "io.coil-kt:coil:${Versions.coilVersion}"
    const val coilGif = "io.coil-kt:coil-gif:${Versions.coilVersion}"
    const val coilSvg = "io.coil-kt:coil-svg:${Versions.coilVersion}"
    const val coilVideo = "io.coil-kt:coil-video:${Versions.coilVersion}"

    // Http
    const val rxHttp = "com.github.liujingxing.rxhttp:rxhttp:2.8.7"
    const val rxHttpCompiler = "com.github.liujingxing.rxhttp:rxhttp-compiler:2.8.7"
    const val okHttp = "com.squareup.okhttp3:okhttp:4.9.3"
    const val gson = "com.google.code.gson:gson:2.8.9"

    // WebView
    const val agentWeb = "com.just.agentweb:agentweb:4.1.3"
    const val WebFileChooser = "com.just.agentweb:filechooser:4.1.3"
    const val WebDownLoader = "com.download.library:Downloader:4.1.3"

    // 权限请求
    const val permissions = "com.github.getActivity:XXPermissions:11.5"

    // MMVK
    const val mmvk = "com.tencent:mmkv:1.2.10"

    // 屏幕适配
    const val autoSize = "me.jessyan:autosize:1.2.1"

    const val ultimateBarX = "com.gitee.zackratos:UltimateBarX:0.8.0"

    const val loadSir = "com.kingja.loadsir:loadsir:1.3.8"

    const val pictureSelector = "io.github.lucksiege:pictureselector:v2.7.3-rc03"

    const val bannerViewpager = "com.github.zhpanvip:BannerViewPager:3.5.2"

    const val magicIndicator = "com.github.hackware1993:MagicIndicator:1.7.0"

    const val buglyCrash = "com.tencent.bugly:crashreport_upgrade:1.4.5"
    const val buglyNative = "com.tencent.bugly:nativecrashreport:3.7.1"
}