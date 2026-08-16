package com.gamedealsradar.domain.repository

import com.gamedealsradar.data.model.Giveaway
import kotlinx.coroutines.flow.Flow

interface GiveawayRepository {

    suspend fun getLocalGiveaways(): Flow<List<Giveaway>>
    suspend fun refreshGiveaways()
}