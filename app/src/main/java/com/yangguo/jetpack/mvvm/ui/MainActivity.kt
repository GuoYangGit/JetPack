package com.yangguo.jetpack.mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yangguo.jetpack.R
import com.yangguo.jetpack.mvvm.viewmodel.MainViewModel
import com.yangguo.jetpack.weight.PKProgressLayout
import com.yangguo.jetpack.weight.PKTimeDownLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private var progressLayout: PKProgressLayout? = null
    private var downTimeLayout: PKTimeDownLayout? = null
    private var currentLeftNum = 0
    private var currentRightNum = 0
    private val viewModel:MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressLayout = findViewById(R.id.pk_progress_layout)
        downTimeLayout = findViewById(R.id.pk_time_down_layout)

        val leftAvatarUrl = "https://img1.baidu.com/it/u=708482407,3295795161&fm=26&fmt=auto&gp=0.jpg"
        val rightAvatarUrl = "https://img2.baidu.com/it/u=2993836181,3668684044&fm=26&fmt=auto&gp=0.jpg"
        val leftUserName = "Vivian"
        val rightUserName = "Lisa\uD83D\uDC97\uD83D\uDC84"
        downTimeLayout?.setData(leftAvatarUrl, rightAvatarUrl, leftUserName, rightUserName)
        downTimeLayout?.post {
            downTimeLayout?.startInputAnimator()
        }
    }

    fun changeNum(view: View) {
        currentLeftNum = (0..1000).random()
//        currentLeftNum = (currentLeftNum..1000).random()
        currentRightNum = (0..1000).random()
//        currentRightNum = (currentRightNum..1000).random()
        progressLayout?.setNum(currentLeftNum, currentRightNum)
        downTimeLayout?.startDownTime(5) {
            downTimeLayout?.startInputAnimator(true)
        }
    }
}