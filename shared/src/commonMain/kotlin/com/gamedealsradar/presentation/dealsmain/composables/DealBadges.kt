package com.gamedealsradar.presentation.dealsmain.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gamedealsradar.domain.model.GiveawayType
import com.gamedealsradar.domain.model.Platform
import com.gamedealsradar.domain.model.Store
import com.gamedealsradar.presentation.utils.AppColors

@Composable
fun CombinedBadges(
    type: GiveawayType? = null,
    platforms: List<Platform> = emptyList(),
    stores: List<Store> = emptyList(),
    maxPlatforms: Int = 2,
    maxStores: Int = 1,
) {
    val typeBadges = listOfNotNull(
        type?.toBadgeData()
    )

    val platformBadges = platforms
        .take(maxPlatforms)
        .map { it.toBadgeData() }

    val storeBadges = stores
        .take(maxStores)
        .map { it.toBadgeData() }

    val hiddenCount =
        (platforms.size - maxPlatforms).coerceAtLeast(0) +
                (stores.size - maxStores).coerceAtLeast(0)

    val combined =
        typeBadges +
                platformBadges +
                storeBadges

    BadgeRow(
        badges = combined,
        hiddenCount = hiddenCount
    )
}
@Composable
fun DiscountBadge(
    from: String,
    to: String,
    percentage: String? = null
) {
    Row(
        modifier = Modifier.clip(RoundedCornerShape(4.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        percentage?.let {
            Box(
                modifier = Modifier
                    .background(AppColors.Discount)
                    .padding(horizontal = 4.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "$it%",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Black
                )
            }
        }

        Box(
            modifier = Modifier
                .background(AppColors.Surface)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = from,
                    style = MaterialTheme.typography.labelSmall,
                    color = AppColors.TextMuted,
                    textDecoration = TextDecoration.LineThrough
                )

                Text(
                    text = to,
                    style = MaterialTheme.typography.labelSmallEmphasized,
                    color = AppColors.TextPrimary
                )
            }
        }
    }
}

@Composable
private fun BadgeRow(
    badges: List<BadgeData>,
    hiddenCount: Int = 0,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        badges.forEach {
            Badge(it)
        }

        if (hiddenCount > 0) {
            MoreBadge(hiddenCount)
        }
    }
}
@Composable
private fun Badge(data: BadgeData) {
    Box(
        modifier = Modifier
            .height(18.dp)
            .background(
                color = data.color,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = data.label,
            style = MaterialTheme.typography.labelSmall,
            color = AppColors.TextPrimary,
            maxLines = 1
        )
    }
}

@Composable
private fun MoreBadge(count: Int) {
    Box(
        modifier = Modifier
            .height(18.dp)
            .background(
                color = AppColors.Surface,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "+$count",
            style = MaterialTheme.typography.labelSmall,
            color = AppColors.TextSecondary,
            maxLines = 1
        )
    }
}

@Preview
@Composable
private fun DiscountBadgePreview() {
    DiscountBadge(from = "$50", to = "$30")
}

@Preview
@Composable
private fun CombinedBadgesPreview() {
    CombinedBadges(
        type = GiveawayType.GAME,
        platforms = listOf(Platform.PC, Platform.PLAYSTATION_4, Platform.XBOX_ONE),
        stores = listOf(Store.STEAM, Store.EPIC_GAMES)
    )
}