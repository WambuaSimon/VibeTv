package com.vibetv.common.utils

sealed class ViewState<out T> {
    object Empty : ViewState<Nothing>()

    object Loading : ViewState<Nothing>()

    data class Ready<T>(val value: T) : ViewState<T>()
}
