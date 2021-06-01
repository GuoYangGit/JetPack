package com.yangguo.jetpack.weight

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.facebook.drawee.view.SimpleDraweeView
import com.guoyang.mvvm.ext.view.dpi
import com.yangguo.jetpack.R
import com.yangguo.jetpack.ext.loadAnimationImage

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
class PKProgressLayout(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private var leftStartColor: Int = 0 // 左边view渐变开始颜色
    private var leftEndColor: Int = 0 // 左边view渐变结束颜色
    private var rightStartColor: Int = 0 // 右边view渐变开始颜色
    private var rightEndColor: Int = 0 // 右边view渐变结束颜色
    private var rectRadius: Float = 0f // view的圆角角度
    private var leftTextColor: Int = 0 // 左边textview的颜色
    private var rightTextColor: Int = 0 // 右边textview的颜色
    private var lineColor: Int = 0 // 分割线的背景色
    private var lineWith: Int = 0 // 中间分割线的宽度
    private var lineIconResId = -1 // 分割线动画
    private var minPaddingWith = 8 * dpi

    private var leftView: View? = null // 左边的view
    private var rightView: View? = null // 右边的view
    private var lineView: View? = null // 中间的分割线
    private var leftTextView: TextView? = null // 左边文字
    private var rightTextView: TextView? = null // 右边文字
    private var lineIconView: SimpleDraweeView? = null // 分割线的动图view

    private var animatorSet: AnimatorSet? = null // 动画集合

    constructor(context: Context) : this(context, null)

    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

    companion object {
        private const val DURATION: Long = 500
    }

    init {
        val typeArray =
            context.obtainStyledAttributes(attrs, R.styleable.PKProgressLayout, defStyleAttr, 0)
        leftStartColor =
            typeArray.getColor(R.styleable.PKProgressLayout_leftRectStartColor, Color.WHITE)
        leftEndColor =
            typeArray.getColor(R.styleable.PKProgressLayout_leftRectEndColor, Color.WHITE)
        rightStartColor =
            typeArray.getColor(R.styleable.PKProgressLayout_rightRectStartColor, Color.WHITE)
        rightEndColor =
            typeArray.getColor(R.styleable.PKProgressLayout_rightRectEndColor, Color.WHITE)
        rectRadius = typeArray.getDimension(R.styleable.PKProgressLayout_rectRadius, 0f)
        leftTextColor = typeArray.getColor(R.styleable.PKProgressLayout_leftTextColor, Color.WHITE)
        rightTextColor =
            typeArray.getColor(R.styleable.PKProgressLayout_rightTextColor, Color.WHITE)
        lineColor = typeArray.getColor(R.styleable.PKProgressLayout_lineColor, Color.WHITE)
        lineIconResId = typeArray.getResourceId(R.styleable.PKProgressLayout_lineIcon, NO_ID)
        typeArray.recycle()

        val view = LayoutInflater.from(context).inflate(R.layout.layout_pk_progress, this, false)
        addView(view)
        leftView = view.findViewById(R.id.start_view)
        rightView = view.findViewById(R.id.end_view)
        lineView = view.findViewById(R.id.divider_line)
        leftTextView = view.findViewById(R.id.start_tv)
        rightTextView = view.findViewById(R.id.end_tv)
        lineIconView = view.findViewById(R.id.divider_icon)

        // 获取中间分割线宽度
        if (lineWith == 0) lineWith = lineView?.width ?: 0

        // 设置分割线的圆角背景
        val lineDrawable = GradientDrawable()
        lineDrawable.setColor(lineColor)
        lineDrawable.cornerRadius = rectRadius
        lineView?.background = lineDrawable
        // 设置分割线动画
        if (lineIconResId != NO_ID) {
            lineIconView?.loadAnimationImage(lineIconResId)
            lineIconView?.visibility = View.GONE
        }
        // 设置文字颜色
        leftTextView?.setTextColor(leftTextColor)
        rightTextView?.setTextColor(rightTextColor)

        // 阿语反向适配
        val rtl = isSupportRTL()
        val orientation =
            if (rtl) GradientDrawable.Orientation.RIGHT_LEFT else GradientDrawable.Orientation.LEFT_RIGHT
        val leftRadii = floatArrayOf(rectRadius, rectRadius, 0f, 0f, 0f, 0f, rectRadius, rectRadius)
        val rightRadii =
            floatArrayOf(0f, 0f, rectRadius, rectRadius, rectRadius, rectRadius, 0f, 0f)
        //设置左边view的背景
        val leftDrawable = GradientDrawable(
            orientation, intArrayOf(leftStartColor, leftEndColor)
        )
        leftDrawable.cornerRadii = if (rtl) rightRadii else leftRadii
        leftView?.background = leftDrawable

        // 设置右边view的背景
        val rightDrawable = GradientDrawable(
            orientation, intArrayOf(rightStartColor, rightEndColor)
        )
        rightDrawable.cornerRadii = if (rtl) leftRadii else rightRadii
        rightView?.background = rightDrawable
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancelAnimator()
    }

    /**
     * 设置数据
     */
    fun setNum(leftNum: Int, rightNum: Int) {
        leftView?.let { view ->
            // 获取总数
            val totalCount = leftNum + rightNum
            // 根据左边值/总数占比*width获取到左边view需要绘制的宽度，这里需要注意的临界点总数为0,左右两边view宽度相等
            // 注意最后计算出来的左边view的宽度需要减去中间分割线的宽度二分之一
            var leftWith =
                (if (totalCount == 0) width / 2 else width * (leftNum * 100 / totalCount) / 100) - lineWith / 2
            // 获取左边textView的宽度
            val leftTextWith = leftTextView?.width ?: 0
            // 获取右边textView的宽度
            val rightTextWith = rightTextView?.width ?: 0

            if (leftWith <= leftTextWith + minPaddingWith) {
                // 判断左边view的宽度是否小于左边textView的宽度
                leftWith = leftTextWith + minPaddingWith
            } else if (leftWith >= width - rightTextWith - minPaddingWith) {
                // 判断左边view的宽度是否大于总宽度减去右边textView的宽度
                leftWith = width - rightTextWith - minPaddingWith
            }
            val leftLayoutParams = view.layoutParams
            if (leftLayoutParams.width == leftWith) return
            val startWith =
                if (leftLayoutParams.width == 0) width / 2 - lineWith / 2 else leftLayoutParams.width

            cancelAnimator()
            // 宽度变化动画
            val withAnimator = ValueAnimator.ofInt(startWith, leftWith)
            withAnimator?.addUpdateListener { valueAnimator ->
                // 动态设置左边view的宽度
                leftLayoutParams.width = valueAnimator.animatedValue as Int
                view.layoutParams = leftLayoutParams
            }

            // 左边textview数值动画
            val leftStartNum = leftTextView?.text.toString()
            val leftTextViewAnimator = ValueAnimator.ofInt(leftStartNum.safeToInt(), leftNum)
            leftTextViewAnimator?.addUpdateListener { valueAnimator ->
                leftTextView?.text = (valueAnimator.animatedValue as Int).toString()
            }

            // 右边textview数值动画
            val rightStartNum = rightTextView?.text.toString()
            val rightTextViewAnimator = ValueAnimator.ofInt(rightStartNum.safeToInt(), rightNum)
            rightTextViewAnimator?.addUpdateListener { valueAnimator ->
                rightTextView?.text = (valueAnimator.animatedValue as Int).toString()
            }

            animatorSet = AnimatorSet()
            animatorSet?.playTogether(withAnimator, leftTextViewAnimator, rightTextViewAnimator)
            animatorSet?.duration = DURATION
            animatorSet?.doOnStart {
                lineIconView?.visibility = View.VISIBLE
            }
            animatorSet?.doOnEnd {
                lineIconView?.visibility = View.GONE
            }
            animatorSet?.start()
        }
    }

    /**
     * 取消动画
     */
    private fun cancelAnimator() {
        if (animatorSet?.isRunning == true) {
            animatorSet?.cancel()
        }
    }

    /**
     * 判断是不是阿语反向
     */
    private fun isSupportRTL(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context?.resources?.configuration?.layoutDirection == View.LAYOUT_DIRECTION_RTL
        } else {
            return false
        }
    }

    /**
     * String转Int
     */
    private fun String.safeToInt(defaultValue: Int = 0): Int {
        return try {
            this.toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }
}