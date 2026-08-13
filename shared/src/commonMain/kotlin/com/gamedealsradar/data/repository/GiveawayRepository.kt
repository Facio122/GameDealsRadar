package com.gamedealsradar.data.repository

import com.gamedealsradar.data.model.Giveaway

interface GiveawayRepository {

    suspend fun getGiveaways(): List<Giveaway>
}