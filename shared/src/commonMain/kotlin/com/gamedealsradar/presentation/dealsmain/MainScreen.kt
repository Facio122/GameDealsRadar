package com.gamedealsradar.presentation.dealsmain

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gamedealsradar.data.model.Giveaway
import com.gamedealsradar.domain.model.GiveawayType
import com.gamedealsradar.domain.model.Platform
import com.gamedealsradar.domain.model.Store
import com.gamedealsradar.presentation.dealsmain.composables.DealTile
import com.gamedealsradar.presentation.dealsmain.composables.DealsAction
import com.gamedealsradar.presentation.utils.AppColors
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    uiState: MainUiState,
    onAction: (DealsAction) -> Unit
) {

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = AppColors.Surface
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState,
        ) {

            item {
                Logo()
                Spacer(modifier = Modifier.height(2.dp))
            }

            stickyHeader {
                Search(
                    onFocused = {
                        scope.launch {
                            listState.scrollToItem(1)
                            onAction(DealsAction.OnSearchFocus)
                        }
                    },
                    handleAction = onAction,
                    uiState = uiState
                )
                Spacer(modifier = Modifier.height(2.dp))
            }


            when (uiState.dealsState.dealStatus) {
                DealsStatus.LOADING -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                DealsStatus.SUCCESS -> {
                    items(uiState.dealsState.deals) { deal ->
                        DealTile(deal = deal)
                    }
                }

                DealsStatus.ERROR -> {
                    item {
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
                }

                DealsStatus.IDLE -> {
                    // Do nothing
                }
            }
        }
    }
}

/** PREVIEWS **/

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen(
        uiState = MainUiState(
            filterPanelConfig = null,
            isFilterPanelOpened = false,
            filterPills = emptyList(),
            dealsState = DealsUiState(
                deals = mockedList(),
                dealStatus = DealsStatus.SUCCESS
            )
        ),
        onAction = { }
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
