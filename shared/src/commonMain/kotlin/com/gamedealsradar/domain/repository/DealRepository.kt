package com.gamedealsradar.domain.repository

import com.gamedealsradar.data.model.Giveaway
import com.gamedealsradar.domain.model.FilterModel
import kotlinx.coroutines.flow.Flow

interface DealRepository {

    suspend fun getAllLocalDeals(): Flow<List<Giveaway>>
    suspend fun refreshDeals()
    suspend fun getFilteredDeals(filters: FilterModel): Flow<List<Giveaway>>
}