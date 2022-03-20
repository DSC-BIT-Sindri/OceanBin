package com.nipun.oceanbin.core

sealed class UIEvent {
    data class ShowSnackbar(val message: String): UIEvent()
}