package com.yangguo.base.ext

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.chad.library.adapter.base.module.LoadMoreModule
import com.guoyang.mvvm.base.appContext
import com.guoyang.mvvm.ext.util.getCompatColor
import com.guoyang.mvvm.ext.util.toHtml
import com.guoyang.mvvm.state.DataUiState
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.yangguo.base.R
import com.yangguo.base.util.SettingUtil
import com.yangguo.base.weight.ScaleTransitionPagerTitleView
import com.yangguo.base.weight.loadcallback.EmptyCallback
import com.yangguo.base.weight.loadcallback.ErrorCallback
import com.yangguo.base.weight.loadcallback.LoadingCallback
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

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
 * Created by Yang.Guo on 2021/6/3.
 */
/**
 * 设置错误布局错误提示
 */
private fun LoadService<*>.setErrorText(message: String) {
    if (message.isNotEmpty()) {
        this.setCallBack(ErrorCallback::class.java) { _, view ->
            view.findViewById<TextView>(R.id.error_tv).text = message
        }
    }
}

/**
 * 设置错误布局
 * @param message 错误布局显示的提示内容
 */
fun LoadService<*>.showError(message: String = "") {
    this.setErrorText(message)
    this.showCallback(ErrorCallback::class.java)
}

/**
 * 设置空布局
 */
fun LoadService<*>.showEmpty() {
    this.showCallback(EmptyCallback::class.java)
}

/**
 * 设置加载中
 */
fun LoadService<*>.showLoading() {
    this.showCallback(LoadingCallback::class.java)
}

/**
 * 初始化状态布局
 */
fun View.loadServiceInit(callback: () -> Unit): LoadService<Any> {
    val loadService = LoadSir.getDefault().register(this) {
        //点击重试时触发的操作
        callback.invoke()
    }
    SettingUtil.setLoadingColor(SettingUtil.getColor(appContext), loadService)
    return loadService
}

/**
 * RecyclerView绑定BaseQuickAdapter
 * @link https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
fun RecyclerView.initBRVAH(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: BaseQuickAdapter<*, *>,
    isScroll: Boolean = true,
    loadMoreListener: OnLoadMoreListener? = null
): RecyclerView {
    init(layoutManger, bindAdapter, isScroll)
    // 这里需要判断Adapter是否实现了LoadMoreModule接口
    if (bindAdapter is LoadMoreModule)
        bindAdapter.loadMoreModule.setOnLoadMoreListener(loadMoreListener)
    return this
}

/**
 * 绑定普通的Recyclerview
 */
fun RecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): RecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}

fun SwipeRefreshLayout.initLoadService(onRefreshListener: () -> Unit): LoadService<Any> {
    init(onRefreshListener)
    return loadServiceInit(onRefreshListener)
}

/**
 * 初始化 SwipeRefreshLayout
 */
fun SwipeRefreshLayout.init(onRefreshListener: () -> Unit) {
    this.run {
        setOnRefreshListener {
            onRefreshListener.invoke()
        }
        //设置主题颜色
        setColorSchemeColors(SettingUtil.getColor(appContext))
    }
}

/**
 * 初始化普通的toolbar 只设置标题
 */
fun Toolbar.init(titleStr: String = ""): Toolbar {
    setBackgroundColor(SettingUtil.getColor(appContext))
//    setTitleTextColor(appContext.getCompatColor(R.color.white))
    title = titleStr
    return this
}

/**
 * 初始化有返回键的toolbar
 */
fun Toolbar.initClose(
    titleStr: String = "",
    backImg: Int = R.mipmap.ic_back,
    onBack: (toolbar: Toolbar) -> Unit
): Toolbar {
    init(titleStr)
    setNavigationIcon(backImg)
    setNavigationOnClickListener { onBack.invoke(this) }
    return this
}

fun MagicIndicator.bindViewPager2(
    viewPager: ViewPager2,
    mStringList: List<String> = arrayListOf(),
    action: (index: Int) -> Unit = {}
) {
    val commonNavigator = CommonNavigator(appContext)
    commonNavigator.adapter = object : CommonNavigatorAdapter() {

        override fun getCount(): Int {
            return mStringList.size
        }

        override fun getTitleView(context: Context, index: Int): IPagerTitleView {
            return ScaleTransitionPagerTitleView(appContext).apply {
                //设置文本
                text = mStringList[index].toHtml()
                //字体大小
                textSize = 17f
                //未选中颜色
                normalColor = Color.WHITE
                //选中颜色
                selectedColor = Color.WHITE
                //点击事件
                setOnClickListener {
                    viewPager.currentItem = index
                    action.invoke(index)
                }
            }
        }

        override fun getIndicator(context: Context): IPagerIndicator {
            return LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_EXACTLY
                //线条的宽高度
                lineHeight = UIUtil.dip2px(appContext, 3.0).toFloat()
                lineWidth = UIUtil.dip2px(appContext, 30.0).toFloat()
                //线条的圆角
                roundRadius = UIUtil.dip2px(appContext, 6.0).toFloat()
                startInterpolator = AccelerateInterpolator()
                endInterpolator = DecelerateInterpolator(2.0f)
                //线条的颜色
                setColors(Color.WHITE)
            }
        }
    }
    this.navigator = commonNavigator

    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            this@bindViewPager2.onPageSelected(position)
            action.invoke(position)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            this@bindViewPager2.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            this@bindViewPager2.onPageScrollStateChanged(state)
        }
    })
}

fun <T> MutableLiveData<DataUiState<T>>.observeUi(
    owner: LifecycleOwner,
    observer: (DataUiState<T>) -> Unit,
    swipeRefreshLayout: SwipeRefreshLayout? = null,
    adapter: BaseQuickAdapter<*, *>? = null,
    loadService: LoadService<*>? = null
) {
    observe(owner) {
        val isLoadMore = adapter is LoadMoreModule
        when (it) {
            is DataUiState.Start -> {
                if (it.isRefresh) {
                    swipeRefreshLayout?.isRefreshing = true
                    if (loadService?.currentCallback != SuccessCallback::class.java) {
                        loadService?.showLoading()
                    }
                }
            }
            is DataUiState.Success -> {
                when {
                    it.isRefresh -> {
                        swipeRefreshLayout?.isRefreshing = false
                        loadService?.showSuccess()
                    }
                    isLoadMore -> {
                        adapter?.loadMoreModule?.loadMoreComplete()
                    }
                }
            }
            is DataUiState.Error -> {
                when {
                    it.isRefresh -> {
                        swipeRefreshLayout?.isRefreshing = false
                        loadService?.showError(it.error.message ?: "")
                    }
                    isLoadMore -> {
                        adapter?.loadMoreModule?.loadMoreFail()
                    }
                }
            }
        }
        observer.invoke(it)
    }
}