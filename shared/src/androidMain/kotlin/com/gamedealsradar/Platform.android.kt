package com.gamedealsradar

import android.os.Build
import android.util.Log

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun logDebug(tag: String, message: String, throwable: Throwable?) {
    Log.d(tag, message, throwable)
}