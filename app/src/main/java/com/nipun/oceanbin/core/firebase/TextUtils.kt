package com.nipun.oceanbin.core.firebase

import java.util.regex.Pattern

fun String.notValidName(): Boolean {
    return this.trim().length < 4
}

fun String.notValidEmail(): Boolean {
    val pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    return when{
        this.trim().isBlank() -> true
        !pattern.matcher(this).matches() -> true
        else -> false
    }
}

fun String.notValidPhone() : Boolean{
    return this.trim().length<10
}

fun String.notValidPassword() : Boolean{
    return this.trim().length<8
}

fun String.notMatchPassword(password : String) : Boolean{
    return this.trim() != password.trim()
}
