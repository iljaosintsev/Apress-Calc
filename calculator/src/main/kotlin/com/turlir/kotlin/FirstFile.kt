package com.turlir.kotlin

import java.util.logging.Logger

class Demo
    (private val name: String) {

    var lastName: String? = ""
        get() {
            println("getter working")
            return field
        }
        set(value) {
            field = if (value == null) "Mr" else "$value Mr"
        }

    fun hi(): String {
        return "hi $name"
    }

    fun fullHi(): String {
        return "hi $name $lastName"
    }

}

interface Logger {
    val log: Logger
        get() = Logger.getLogger(javaClass.name)
}