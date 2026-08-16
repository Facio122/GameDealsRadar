package com.gamedealsradar.presentation.dealsmain

import com.gamedealsradar.data.model.Giveaway

sealed interface DealsUiState {

    data class Success(val deals: List<Giveaway>) : DealsUiState

    data object Idle : DealsUiState

    data object Loading : DealsUiState

    data object Error : DealsUiState
}