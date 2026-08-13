package com.gamedealsradar.data.mapper

import com.gamedealsradar.data.model.Giveaway
import com.gamedealsradar.data.model.GiveawayDto
import com.gamedealsradar.Logger
import com.gamedealsradar.domain.model.GiveawayType
import com.gamedealsradar.domain.model.Platforms
import kotlin.time.Instant

fun GiveawayDto.toGiveaway(): Giveaway {
    return Giveaway(
        id = id,
        title = title,
        description = description,
        thumbnail = thumbnail,
        image = image,
        worth = worth,
        giveawayUrl = giveawayUrl,
        type = type?.toGiveawayType(),
        platforms = platforms.toPlatformsList(),
        publishedDate = publishedDate?.toInstant(),
        endDate = endDate?.toInstant(),
        updatedAt = updatedAt?.toInstant()
    )
}

fun Giveaway.toDto(): GiveawayDto {
    return GiveawayDto(
        id = id,
        title = title,
        description = description,
        thumbnail = thumbnail,
        image = image,
        worth = worth,
        giveawayUrl = giveawayUrl,
        type = type?.toDtoStatus(),
        platforms = platforms.toDtoPlatforms(),
        publishedDate = publishedDate?.toString(),
        endDate = endDate?.toString(),
        updatedAt = updatedAt?.toString()
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

private fun GiveawayType.toDtoStatus(): String {
    return when (this) {
        GiveawayType.GAME -> "Game"
        GiveawayType.DLC -> "DLC"
        GiveawayType.EARLY_ACCESS -> "Early Access"
    }
}

private fun String?.toPlatformsList(): List<Platforms> {
    if (this.isNullOrBlank()) return emptyList()
    return this.split(",").map { it.trim().toPlatform() }
}

private fun List<Platforms>.toDtoPlatforms(): String {
    return this.joinToString(", ") { it.toDtoName() }
}

private fun String.toPlatform(): Platforms {
    return when (this) {
        "PC" -> Platforms.PC
        "Playstation 4" -> Platforms.PLAYSTATION_4
        "Playstation 5" -> Platforms.PLAYSTATION_5
        "Xbox One" -> Platforms.XBOX_ONE
        "Xbox Series X|S" -> Platforms.XBOX_SERIES_X_S
        "Switch" -> Platforms.SWITCH
        "Android" -> Platforms.ANDROID
        "iOS" -> Platforms.IOS
        "DRM-Free" -> Platforms.DRM_FREE
        "Steam" -> Platforms.STEAM
        "Epic Games" -> Platforms.EPIC_GAMES
        "GOG" -> Platforms.GOG
        "Itch.io" -> Platforms.ITCH_IO
        else -> Platforms.UNKNOWN
    }
}

private fun Platforms.toDtoName(): String {
    return when (this) {
        Platforms.PC -> "PC"
        Platforms.PLAYSTATION_4 -> "Playstation 4"
        Platforms.PLAYSTATION_5 -> "Playstation 5"
        Platforms.XBOX_ONE -> "Xbox One"
        Platforms.XBOX_SERIES_X_S -> "Xbox Series X|S"
        Platforms.SWITCH -> "Switch"
        Platforms.ANDROID -> "Android"
        Platforms.IOS -> "iOS"
        Platforms.DRM_FREE -> "DRM-Free"
        Platforms.STEAM -> "Steam"
        Platforms.EPIC_GAMES -> "Epic Games"
        Platforms.GOG -> "GOG"
        Platforms.ITCH_IO -> "Itch.io"
        Platforms.UNKNOWN -> "Unknown"
    }
}

private fun String.toInstant(): Instant? {
    return try {
        Instant.parse(this)
    } catch (e: Exception) {
        Logger.d("GiveawayMapper", "Failed to parse date: $this", e)
        null
    }
}
