package com.yangguo.jetpack.mvvm.ui

import android.os.Bundle
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.yangguo.base.ui.BaseVMFragment
import com.yangguo.base.util.CoilEngine
import com.yangguo.jetpack.R
import com.yangguo.jetpack.databinding.FragmentMeBinding
import dagger.hilt.android.AndroidEntryPoint

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
 * Created by Yang.Guo on 2021/6/4.
 */
@AndroidEntryPoint
class MeFragment : BaseVMFragment<FragmentMeBinding>() {

    override fun layoutId(): Int = R.layout.fragment_me

    override fun initView(savedInstanceState: Bundle?) {
        binding?.run {
            pictureBtn.setOnClickListener {
                PictureSelector.create(this@MeFragment)
                    .openGallery(PictureMimeType.ofAll())
                    .theme(R.style.picture_white_style)
                    .imageEngine(CoilEngine)
                    .isEnableCrop(true)
                    .withAspectRatio(1, 1)// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    .forResult(object : OnResultCallbackListener<LocalMedia?> {
                        override fun onResult(result: List<LocalMedia?>) {
                            // 结果回调
                        }

                        override fun onCancel() {
                            // 取消
                        }
                    })
            }
        }
    }
}