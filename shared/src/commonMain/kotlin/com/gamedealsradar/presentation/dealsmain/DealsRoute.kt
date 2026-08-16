package com.gamedealsradar.presentation.dealsmain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.koinInject

@Composable
fun DealsRoute() {
    val viewModel = koinInject<DealsMainViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    DealsMainScreen(uiState = uiState)
}