package com.gamedealsradar.presentation.dealsmain

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gamedealsradar.data.model.Giveaway
import com.gamedealsradar.domain.model.GiveawayType
import com.gamedealsradar.domain.model.Platform
import com.gamedealsradar.domain.model.Store
import com.gamedealsradar.presentation.dealsmain.composables.DealTile
import com.gamedealsradar.presentation.utils.AppColors

@Composable
fun DealsMainScreen(uiState: DealsUiState) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppColors.Surface
    ) {
        when (uiState) {
            is DealsUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is DealsUiState.Success -> {
                DealsList(deals = uiState.deals)
            }

            is DealsUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error loading deals. Please try again later.",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            is DealsUiState.Idle -> {
                // Do nothing
            }
        }
    }
}

@Composable
fun DealsList(deals: List<Giveaway>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(deals) { deal ->
            DealTile(deal = deal)
        }
    }
}

/** PREVIEWS **/

@Preview
@Composable
private fun DealsMainScreenPreview() {
    DealsMainScreen(
        uiState = DealsUiState.Success(
            deals = mockedList()
        )
    )
}

fun mockedList() = listOf(
    Giveaway(
        title = "Sample Deal 1",
        description = "This is a sample deal description.",
        worth = "$10",
        id = 1,
        thumbnail = null,
        image = null,
        giveawayUrl = null,
        type = GiveawayType.GAME,
        platforms = listOf(
            Platform.PLAYSTATION_4
        ),
        stores = listOf(
            Store.STEAM,
            Store.EPIC_GAMES,
            Store.GOG
        ),
        publishedDate = null,
        endDate = null,
        updatedAt = null
    ),
    Giveaway(
        title = "Sample Deal 2",
        description = "This is another sample deal description.",
        worth = "$20",
        id = 2,
        thumbnail = null,
        image = null,
        giveawayUrl = null,
        type = GiveawayType.DLC,
        platforms = emptyList(),
        stores = listOf(Store.STEAM),
        publishedDate = null,
        endDate = null,
        updatedAt = null
    ),
    Giveaway(
        title = "Sample Deal 3",
        description = "This is yet another sample deal description.",
        worth = "$30",
        id = 3,
        thumbnail = null,
        image = null,
        giveawayUrl = null,
        type = GiveawayType.EARLY_ACCESS,
        platforms = emptyList(),
        stores = listOf(Store.STEAM, Store.EPIC_GAMES, Store.GOG),
        publishedDate = null,
        endDate = null,
        updatedAt = null
    )
)
