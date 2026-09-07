package com.gamedealsradar.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gamedealsradar.data.model.GiveawayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GiveawayDao {

    @Query("SELECT * FROM giveaways")
    fun getAllGiveaways(): Flow<List<GiveawayEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGiveaways(giveaways: List<GiveawayEntity>)

    @Query(
        """
    SELECT * FROM giveaways
    WHERE (
        :searchQuery = ''
        OR title LIKE '%' || :searchQuery || '%'
        OR stores LIKE '%' || :searchQuery || '%'
        OR platforms LIKE '%' || :searchQuery || '%'
        OR type LIKE '%' || :searchQuery || '%'
    )
    AND (:stores = '' OR stores LIKE '%' || :stores || '%')
    AND (:platforms = '' OR platforms LIKE '%' || :platforms || '%')
    AND (:types = '' OR type LIKE '%' || :types || '%')
    AND CAST(REPLACE(worth, '$', '') AS REAL) BETWEEN :minPrice AND :maxPrice
    """
    )
    fun getFilteredGiveaways(
        searchQuery: String,
        stores: String,
        platforms: String,
        types: String,
        minPrice: Float,
        maxPrice: Float,
    ): Flow<List<GiveawayEntity>>
}