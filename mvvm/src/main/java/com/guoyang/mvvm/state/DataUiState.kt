package com.guoyang.mvvm.state

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
 * Created by Yang.Guo on 2021/6/5.
 */
sealed class DataUiState<out T> {
    companion object {
        fun <T> onStart(loadMsg: String = "", isRefresh: Boolean = true): DataUiState<T> =
            Start(loadMsg, isRefresh)

        fun <T> onSuccess(data: T?, isRefresh: Boolean = true): DataUiState<T> =
            Success(data, isRefresh)

        fun <T> onError(error: Throwable, isRefresh: Boolean = true): DataUiState<T> =
            Error(error, isRefresh)
    }

    data class Start(val loadMsg: String, val isRefresh: Boolean) : DataUiState<Nothing>()

    data class Success<out T>(val data: T?, val isRefresh: Boolean) : DataUiState<T>()

    data class Error(val error: Throwable, val isRefresh: Boolean) : DataUiState<Nothing>()
}