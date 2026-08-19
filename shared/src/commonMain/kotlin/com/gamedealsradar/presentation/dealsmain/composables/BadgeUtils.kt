package com.gamedealsradar.presentation.dealsmain.composables

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.gamedealsradar.domain.model.GiveawayType
import com.gamedealsradar.domain.model.Platform
import com.gamedealsradar.domain.model.Store
import com.gamedealsradar.presentation.utils.AppColors

data class BadgeData(
    val label: String,
    val color: Color,
    val width: TextUnit
)

fun Platform.toBadgeData(): BadgeData =
    when (this) {
        Platform.PC ->
            BadgeData("PC", AppColors.Surface, 28.sp)

        Platform.PLAYSTATION_4 ->
            BadgeData("PS4", AppColors.Primary, 32.sp)

        Platform.PLAYSTATION_5 ->
            BadgeData("PS5", AppColors.Primary, 32.sp)

        Platform.XBOX_ONE ->
            BadgeData("Xbox One", AppColors.Discount, 62.sp)

        Platform.XBOX_SERIES_X_S ->
            BadgeData("Xbox Series", AppColors.Discount, 90.sp)

        Platform.SWITCH ->
            BadgeData("Switch", AppColors.Error, 52.sp)

        Platform.ANDROID ->
            BadgeData("Android", AppColors.Surface, 58.sp)

        Platform.IOS ->
            BadgeData("iOS", AppColors.Surface, 28.sp)

        Platform.UNKNOWN ->
            BadgeData("Unknown", AppColors.Surface, 62.sp)
    }

fun Store.toBadgeData(): BadgeData =
    when (this) {
        Store.DRM_FREE ->
            BadgeData("DRM Free", AppColors.Surface, 68.sp)

        Store.STEAM ->
            BadgeData("Steam", AppColors.Primary, 48.sp)

        Store.EPIC_GAMES ->
            BadgeData("Epic", AppColors.Secondary, 72.sp)

        Store.GOG ->
            BadgeData("GOG", AppColors.HotDeal, 34.sp)

        Store.ITCH_IO ->
            BadgeData("itch.io", AppColors.Surface, 48.sp)

        Store.UNKNOWN ->
            BadgeData("Unknown", AppColors.Surface, 62.sp)
    }

fun GiveawayType.toBadgeData(): BadgeData =
    when (this) {
        GiveawayType.GAME ->
            BadgeData("Game", AppColors.Free, 48.sp)

        GiveawayType.DLC ->
            BadgeData("DLC", AppColors.Discount, 36.sp)

        GiveawayType.EARLY_ACCESS ->
            BadgeData("Early Access", AppColors.HotDeal, 120.sp)

        GiveawayType.UNKNOWN ->
            BadgeData("Unknown", AppColors.Surface, 62.sp)
    }