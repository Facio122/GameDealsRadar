package com.gamedealsradar.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GiveawayDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String? = null,
    @SerialName("thumbnail") val thumbnailUrl: String? = null,
    @SerialName("image") val imageUrl: String? = null,
    @SerialName("worth") val worth: String? = null,
    @SerialName("giveaway_url") val giveawayUrl: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("platforms") val platforms: String? = null,
    @SerialName("published_date") val publishedDate: String? = null,
    @SerialName("end_date") val endDate: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
)