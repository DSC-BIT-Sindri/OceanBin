package com.nipun.oceanbin.core

sealed class UIEvent(val name : String ) {
    data class ShowSnackbar(val message: String): UIEvent(name = message)

    data class GoNext(
        val message : String = ""
    ) : UIEvent(name = message)

}