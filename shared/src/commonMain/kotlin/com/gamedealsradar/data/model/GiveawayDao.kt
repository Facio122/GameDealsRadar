package com.gamedealsradar.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GiveawayDao {

    @Query("SELECT * FROM giveaways")
    fun getAllGiveaways(): Flow<List<GiveawayEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGiveaways(giveaways: List<GiveawayEntity>)
}