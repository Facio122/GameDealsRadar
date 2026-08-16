package com.gamedealsradar.data.room

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun getDatabaseBuilder(context: Any?): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = NSHomeDirectory() + "/game_deals.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
        factory = { /* AppDatabase constructor here */ throw IllegalStateException("Database constructor not provided") }
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun NSHomeDirectory(): String {
    return NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )?.path ?: ""
}
