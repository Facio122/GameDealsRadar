package com.gamedealsradar.data.room

import androidx.room.RoomDatabase

expect fun getDatabaseBuilder(context: Any? = null): RoomDatabase.Builder<AppDatabase>
