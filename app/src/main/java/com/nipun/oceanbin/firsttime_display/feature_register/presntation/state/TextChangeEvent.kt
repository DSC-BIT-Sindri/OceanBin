package com.nipun.oceanbin.firsttime_display.feature_register.presntation.state

sealed class TextChangeEvent(val text : String) {
    data class Email(val name : String) : TextChangeEvent(name)
    data class Phone(val name : String) : TextChangeEvent(name)
    data class Password(val name : String) : TextChangeEvent(name)
    data class ConfirmPassword(val name : String) : TextChangeEvent(name)
    data class Name(val name : String) : TextChangeEvent(name)
}