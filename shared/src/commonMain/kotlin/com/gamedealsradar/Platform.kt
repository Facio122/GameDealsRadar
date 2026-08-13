package com.gamedealsradar

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun logDebug(tag: String, message: String, throwable: Throwable? = null)

object Logger {
    fun d(tag: String, message: String, throwable: Throwable? = null) {
        logDebug(tag, message, throwable)
    }
}