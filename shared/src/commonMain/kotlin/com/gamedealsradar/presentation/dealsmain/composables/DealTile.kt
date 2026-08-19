package com.gamedealsradar.presentation.dealsmain.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.gamedealsradar.data.model.Giveaway
import com.gamedealsradar.presentation.dealsmain.mockedList
import com.gamedealsradar.presentation.utils.AppColors

@Composable
fun DealTile(deal: Giveaway) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        AppColors.TileGradientStart,
                        AppColors.TileGradientEnd,
                        AppColors.TileGradientStart,
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (LocalInspectionMode.current) {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray)
                )
            } else {
                AsyncImage(
                    model = deal.thumbnail ?: deal.image,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .width(120.dp)
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxHeight()
            ) {
                CombinedBadges(
                    type = deal.type,
                    platforms = deal.platforms,
                    stores = deal.stores,
                    maxPlatforms = 1,
                    maxStores = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Title(title = deal.title)
                Spacer(modifier = Modifier.height(16.dp))
                DiscountBadge(from = deal.worth ?: "$0", to = "$0", percentage = "100")
            }
        }
    }
}

@Composable
private fun Title(
    title: String
) {
    Text(
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = AppColors.TextPrimary,
        text = title
    )
}

@Preview(
    widthDp = 400,
    heightDp = 100
)
@Composable
private fun DealTilePreviewWithTopBadges() {
    DealTile(deal = mockedList().first())
}