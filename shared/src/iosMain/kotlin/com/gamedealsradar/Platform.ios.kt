package com.gamedealsradar

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun logDebug(tag: String, message: String, throwable: Throwable?) {
    val logMessage = if (throwable != null) "$message\n${throwable.message}" else message
    println("[$tag] $logMessage")
}