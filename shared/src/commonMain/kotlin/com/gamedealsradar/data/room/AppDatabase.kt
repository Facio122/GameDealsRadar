package com.gamedealsradar.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gamedealsradar.data.model.GiveawayDao
import com.gamedealsradar.data.model.GiveawayEntity

@Database(entities = [GiveawayEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun giveawayDao(): GiveawayDao
}
