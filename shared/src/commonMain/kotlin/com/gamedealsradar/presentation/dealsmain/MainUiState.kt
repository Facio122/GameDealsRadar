package com.gamedealsradar.presentation.dealsmain

import com.gamedealsradar.data.model.Giveaway

data class MainUiState(
    val filterPanelConfig: List<FilterCategory>?,
    val isFilterPanelOpened: Boolean,
    val filterPills: List<FilterItem>,
    val dealsState: DealsUiState,
)

data class DealsUiState(
    val deals: List<Giveaway> = emptyList(),
    val dealStatus: DealsStatus = DealsStatus.IDLE,
)

enum class DealsStatus {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR,
}