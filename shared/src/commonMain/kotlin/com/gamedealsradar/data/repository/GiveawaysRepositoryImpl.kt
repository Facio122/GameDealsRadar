package com.gamedealsradar.data.repository

import com.gamedealsradar.data.mapper.toDomain
import com.gamedealsradar.data.mapper.toGiveawayEntity
import com.gamedealsradar.data.model.Giveaway
import com.gamedealsradar.data.model.GiveawayDto
import com.gamedealsradar.data.model.GiveawayDao
import com.gamedealsradar.domain.repository.GiveawayRepository
import com.gamedealsradar.logDebug
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GiveawaysRepositoryImpl(
    private val supabaseClient: SupabaseClient,
    private val giveawayDao: GiveawayDao,
) : GiveawayRepository {

    private companion object {
        const val TAG = "GiveawaysRepositoryImpl"
    }

    override suspend fun getLocalGiveaways(): Flow<List<Giveaway>> {
        return giveawayDao.getAllGiveaways().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override suspend fun refreshGiveaways() {
        try {
            val remoteData = supabaseClient
                .from("gamepower-deals")
                .select()
                .decodeList<GiveawayDto>()

            val entities = remoteData.map { it.toGiveawayEntity() }

            giveawayDao.insertGiveaways(entities)
        } catch (e: Exception) {
            logDebug(TAG,"Error refreshing giveaways: ${e.message}")
        }
    }
}