package com.gamedealsradar.data.model

import com.gamedealsradar.domain.model.GiveawayType
import com.gamedealsradar.domain.model.Platforms
import kotlin.time.Instant

data class Giveaway(
    val id: Int,
    val title: String,
    val description: String?,
    val thumbnail: String?,
    val image: String?,
    val worth: String?,
    val giveawayUrl: String?,
    val type: GiveawayType?,
    val platforms: List<Platforms>,
    val publishedDate: Instant?,
    val endDate: Instant?,
    val updatedAt: Instant?
)