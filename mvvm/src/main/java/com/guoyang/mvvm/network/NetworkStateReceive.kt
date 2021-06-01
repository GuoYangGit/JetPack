package com.guoyang.mvvm.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

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
 * Created by Yang.Guo on 2021/5/31.
 */
class NetworkStateReceive : BroadcastReceiver() {
    var isInit = false

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ConnectivityManager.CONNECTIVITY_ACTION) return
        if (isInit) return
        if (!NetworkUtil.isNetworkAvailable(context)) {
            NetworkStateManager.instance.mNetworkStateCallback.value.let {
                if (it == null || it.isAvailable) {
                    NetworkStateManager.instance.mNetworkStateCallback.value = NetState(false)
                }
            }
        } else {
            NetworkStateManager.instance.mNetworkStateCallback.value.let {
                if (it == null || !it.isAvailable) {
                    NetworkStateManager.instance.mNetworkStateCallback.value = NetState(true)
                }
            }
        }
        isInit = true
    }
}