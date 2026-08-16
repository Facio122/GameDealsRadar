package com.gamedealsradar.data.mapper

import com.gamedealsradar.data.model.Giveaway
import com.gamedealsradar.data.model.GiveawayDto
import com.gamedealsradar.Logger
import com.gamedealsradar.data.model.GiveawayEntity
import com.gamedealsradar.domain.model.GiveawayType
import com.gamedealsradar.domain.model.Platform
import com.gamedealsradar.domain.model.Store
import kotlin.time.Instant

fun GiveawayDto.toGiveawayEntity(): GiveawayEntity {
    val parsedPlatforms =
        platforms?.splitPlatformAndStores() ?: ParsedPlatforms(emptyList(), emptyList())

    return GiveawayEntity(
        id = id,
        title = title,
        description = description ?: "",
        thumbnail = thumbnailUrl ?: "",
        imageUrl = imageUrl ?: "",
        worth = worth ?: "",
        giveawayUrl = giveawayUrl ?: "",
        type = type ?: "",
        platforms = parsedPlatforms.platforms.joinToString(","),
        stores = parsedPlatforms.stores.joinToString(","),
        publishedDate = publishedDate ?: "",
        endDate = endDate ?: "",
        updatedAt = updatedAt ?: "",
    )
}

fun GiveawayEntity.toDomain(): Giveaway {
    return Giveaway(
        id = id,
        title = title,
        description = description,
        thumbnail = thumbnail,
        image = imageUrl,
        worth = worth,
        giveawayUrl = giveawayUrl,
        type = type.toGiveawayType(),
        platforms = platforms.toPlatformList(),
        stores = stores.toStoreList(),
        publishedDate = publishedDate.toInstant(),
        endDate = endDate.toInstant(),
        updatedAt = updatedAt.toInstant()
    )
}

private fun String.toGiveawayType(): GiveawayType? {
    return when (this) {
        "Game" -> GiveawayType.GAME
        "DLC" -> GiveawayType.DLC
        "Early Access" -> GiveawayType.EARLY_ACCESS
        else -> null
    }
}

private fun String?.toPlatformList(): List<Platform> {
    if (this.isNullOrBlank()) return emptyList()
    return this.split(",").mapNotNull { it.trim().toPlatform() }
}

private fun String?.toStoreList(): List<Store> {
    if (this.isNullOrBlank()) return emptyList()
    return this.split(",").mapNotNull { it.trim().toStore() }
}

private fun String.toPlatform(): Platform? {
    return when (this) {
        "PC" -> Platform.PC
        "Playstation 4" -> Platform.PLAYSTATION_4
        "Playstation 5" -> Platform.PLAYSTATION_5
        "Xbox One" -> Platform.XBOX_ONE
        "Xbox Series X|S" -> Platform.XBOX_SERIES_X_S
        "Switch" -> Platform.SWITCH
        "Android" -> Platform.ANDROID
        "iOS" -> Platform.IOS
        else -> null
    }
}

private fun String.toStore(): Store? {
    return when (this) {
        "DRM-Free" -> Store.DRM_FREE
        "Steam" -> Store.STEAM
        "Epic Games Store" -> Store.EPIC_GAMES
        "GOG" -> Store.GOG
        "Itch.io" -> Store.ITCH_IO
        else -> null
    }
}

private fun String.splitPlatformAndStores(): ParsedPlatforms {
    val parts = split(",")
        .map { it.trim() }
        .filter { it.isNotBlank() }

    return ParsedPlatforms(
        platforms = parts.filter { it.isPlatform() },
        stores = parts.filter { it.isStore() }
    )
}

private fun String.toInstant(): Instant? {
    return try {
        Instant.parse(this)
    } catch (e: Exception) {
        Logger.d("GiveawayMapper", "Failed to parse date: $this", e)
        null
    }
}

private data class ParsedPlatforms(
    val platforms: List<String>,
    val stores: List<String>
)

private fun String.isPlatform(): Boolean {
    return when (this) {
        "PC",
        "Playstation 4",
        "Playstation 5",
        "Xbox One",
        "Xbox Series X|S",
        "Switch",
        "Android",
        "iOS" -> true

        else -> false
    }
}

private fun String.isStore(): Boolean {
    return when (this) {
        "DRM-Free",
        "Steam",
        "Epic Games Store",
        "GOG",
        "Itch.io" -> true

        else -> false
    }
}