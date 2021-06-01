package com.yangguo.jetpack.weight

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import com.facebook.drawee.view.SimpleDraweeView
import com.guoyang.mvvm.ext.view.dpi
import com.yangguo.jetpack.R
import com.yangguo.jetpack.ext.loadAvatar

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
 * Created by yz on 2021/5/25.
 * github https://github.com/GuoYangGit
 * QQ:352391291
 */
class PKTimeDownLayout(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private var shapeBackGroundColor: Int = 0 // shape的背景色
    private var shapeStrokeColor: Int = 0 // shape的波纹色
    private var rectRadius: Float = 0f // 左右进场view的圆角角度
    private var startColor: Int = 0 // 左右view的渐变开始颜色
    private var endColor: Int = 0 // 左右view的渐变结束颜色
    private var pkIconResId: Int = -1 // pk的图片资源
    private var vsIconResId: Int = -1 // vs的图片资源

    private var downTimeTextView: TextView? = null // 倒计时的textview
    private var leftLayout: ConstraintLayout? = null // 左边布局
    private var rightLayout: ConstraintLayout? = null // 右边布局
    private var leftNameTextView: TextView? = null // 左边用户TextView
    private var rightNameTextView: TextView? = null // 右边用户TextView
    private var leftAvatarIv: SimpleDraweeView? = null // 左边用户头像
    private var rightAvatarIv: SimpleDraweeView? = null // 右边用户头像
    private var pkIconIv: ImageView? = null // Pk or Vs的ImageView

    private var downTimeAnimator: ValueAnimator? = null // 倒计时动画
    private var inputAnimator: ValueAnimator? = null // 入场动画

    constructor(context: Context) : this(context, null)

    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

    companion object {
        private const val INPUT_DURATION: Long = 1500
        private const val DOWN_TIME_DURATION: Long = 1000
        private const val TAG = "PKTimeDownLayout"
    }

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.PKTimeDownLayout, defStyleAttr, 0)
        shapeBackGroundColor = typeArray.getColor(R.styleable.PKTimeDownLayout_shapeBackgroundColor, 0x50000000)
        shapeStrokeColor = typeArray.getColor(R.styleable.PKTimeDownLayout_shapeStrokeColor, 0x10000000)
        startColor = typeArray.getColor(R.styleable.PKTimeDownLayout_startColor, 0x00000000)
        endColor = typeArray.getColor(R.styleable.PKTimeDownLayout_endColor, 0x70000000)
        rectRadius = typeArray.getDimension(R.styleable.PKTimeDownLayout_shapeRadius, 30f * dpi)
        pkIconResId = typeArray.getResourceId(R.styleable.PKTimeDownLayout_pk_icon, NO_ID)
        vsIconResId = typeArray.getResourceId(R.styleable.PKTimeDownLayout_vs_icon, NO_ID)
        typeArray.recycle()

        val view = LayoutInflater.from(context).inflate(R.layout.layout_pk_downtime, this, false)
        addView(view)

        val insideShapeView = view.findViewById<View>(R.id.inside_shape_bg)
        val externalShapeView = view.findViewById<View>(R.id.external_shape_bg)
        downTimeTextView = view.findViewById(R.id.down_time_tv)
        leftLayout = view.findViewById(R.id.left_layout)
        rightLayout = view.findViewById(R.id.right_layout)
        leftAvatarIv = view.findViewById(R.id.left_avatar_iv)
        rightAvatarIv = view.findViewById(R.id.right_avatar_iv)
        leftNameTextView = view.findViewById(R.id.left_name_tv)
        rightNameTextView = view.findViewById(R.id.right_name_tv)
        pkIconIv = view.findViewById(R.id.pk_iv)

        // 设置内圆背景
        val insideShapeDrawable = GradientDrawable()
        insideShapeDrawable.shape = GradientDrawable.OVAL

        insideShapeDrawable.setColor(shapeBackGroundColor)
        insideShapeView.background = insideShapeDrawable

        // 设置外圆水波纹背景
        val externalShapeDrawable = GradientDrawable()
        externalShapeDrawable.shape = GradientDrawable.OVAL
        externalShapeDrawable.setColor(shapeStrokeColor)
        externalShapeView.background = externalShapeDrawable

        // 适配阿语反向
        val rtl = isSupportRTL()
        val orientation = if (rtl) GradientDrawable.Orientation.RIGHT_LEFT else GradientDrawable.Orientation.LEFT_RIGHT
        val leftRadii = floatArrayOf(0f, 0f, rectRadius, rectRadius, rectRadius, rectRadius, 0f, 0f)
        val rightRadii = floatArrayOf(rectRadius, rectRadius, 0f, 0f, 0f, 0f, rectRadius, rectRadius)

        //设置左边view的背景
        val leftDrawable = GradientDrawable(
            orientation, intArrayOf(startColor, endColor)
        )
        leftDrawable.cornerRadii = if (rtl) rightRadii else leftRadii
        leftLayout?.background = leftDrawable

        // 设置右边view的背景
        val rightDrawable = GradientDrawable(
            orientation, intArrayOf(endColor, startColor)
        )
        rightDrawable.cornerRadii = if (rtl) leftRadii else rightRadii
        rightLayout?.background = rightDrawable
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopDownTime()
        stopInputAnimator()
    }

    /**
     * 设置用户数据
     * leftAvatarUrl:左边用户头像
     * rightAvatarUrl:右边用户头像
     * leftUserName:左边用户姓名
     * rightUserName:右边用户姓名
     */
    fun setData(leftAvatarUrl: String, rightAvatarUrl: String, leftUserName: String, rightUserName: String) {
        leftAvatarIv?.loadAvatar(leftAvatarUrl)
        rightAvatarIv?.loadAvatar(rightAvatarUrl)
        leftNameTextView?.text = leftUserName
        rightNameTextView?.text = rightUserName
    }

    /**
     * 开始入场动画
     * isPk:是否是倒计时Pk动画
     */
    fun startInputAnimator(isPk: Boolean = false, onFinish: () -> Unit = {}) {
        if (leftLayout == null || rightLayout == null) return
        val rtl = isSupportRTL()
        stopInputAnimator()
        // 获取控件的宽度
        val with = leftLayout?.width ?: 0
        // 设置动画
        inputAnimator = ValueAnimator.ofInt(0, with, with, with)
        inputAnimator?.duration = INPUT_DURATION
        inputAnimator?.addUpdateListener { valueAnimator ->
            val value = (valueAnimator.animatedValue as Int).toFloat()
            // 图标进行缩放效果
            pkIconIv?.scaleX = value / with
            pkIconIv?.scaleY = value / with
            if (isPk) return@addUpdateListener
            // 左右两边view进行y轴平移效果
            leftLayout?.translationX = if (rtl) with - value else value - with
            rightLayout?.translationX = if (rtl) value - with else with - value
        }
        inputAnimator?.addListener(object : Animator.AnimatorListener {
            private var isCancel: Boolean = false

            override fun onAnimationStart(animation: Animator?) {
                // 如果是pk标识,左右两边view是不需要进行展示
                leftLayout?.visibility = if (isPk) View.INVISIBLE else View.VISIBLE
                rightLayout?.visibility = if (isPk) View.INVISIBLE else View.VISIBLE
                pkIconIv?.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
                if (isCancel) return
                onFinish.invoke()
//            leftLayout?.visibility = View.INVISIBLE
//            rightLayout?.visibility = View.INVISIBLE
//            pkIconIv?.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {
                isCancel = true
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
        inputAnimator?.start()

        //根据pk状态设置图标
        if (isPk && pkIconResId != NO_ID) {
            pkIconIv?.setImageResource(pkIconResId)
        } else if (vsIconResId != NO_ID) {
            pkIconIv?.setImageResource(vsIconResId)
        }
    }

    /**
     * 取消入场动画
     */
    private fun stopInputAnimator() {
        if (inputAnimator?.isRunning == true) {
            inputAnimator?.cancel()
        }
    }

    /**
     * 开始倒计时动画
     * downTime:倒计时时长(默认五秒倒计时)
     */
    fun startDownTime(downTime: Int = 5, onFinish: () -> Unit = {}) {
        stopDownTime()
        if (downTime < 1) return
        downTimeTextView?.let { textView ->
            // 根据倒计时时长获取重复次数(动画时长为1秒,重复5次正好是倒计时5秒)
            var downCount = downTime
            textView.text = downCount.toString()
            // 设置动画插值
            downTimeAnimator = ValueAnimator.ofFloat(
                0f, 0.4f, 0.6f, 0.8f, 1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 0.8f, 0.6f, 0.4f, 0f, 0f, 0f, 0f, 0f
            )
            downTimeAnimator?.addUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue as Float
                // 设置倒计时的缩放动画
                textView.scaleX = value
                textView.scaleY = value
            }
            downTimeAnimator?.duration = DOWN_TIME_DURATION
            // 这里就是设置的重复次数
            downTimeAnimator?.repeatCount = downCount - 1
            downTimeAnimator?.addListener(object : Animator.AnimatorListener {
                private var isCancel: Boolean = false

                override fun onAnimationStart(animation: Animator?) {
                    textView.visibility = View.VISIBLE
                    pkIconIv?.visibility = View.GONE
                    leftLayout?.visibility = View.INVISIBLE
                    rightLayout?.visibility = View.INVISIBLE
                }

                override fun onAnimationEnd(animation: Animator?) {
                    if (isCancel) return
                    textView.visibility = View.GONE
                    onFinish.invoke()
                }

                override fun onAnimationCancel(animation: Animator?) {
                    isCancel = true
                }

                override fun onAnimationRepeat(animation: Animator?) {
                    downCount -= 1
                    textView.text = downCount.toString()
                }

            })
            downTimeAnimator?.start()
        }
    }

    /**
     * 取消倒计时动画
     */
    private fun stopDownTime() {
        if (downTimeAnimator?.isRunning == true) {
            downTimeAnimator?.cancel()
        }
    }

    /**
     * 判断是不是阿语反向
     */
    private fun isSupportRTL(): Boolean {
        return context?.resources?.configuration?.layoutDirection == View.LAYOUT_DIRECTION_RTL
    }
}