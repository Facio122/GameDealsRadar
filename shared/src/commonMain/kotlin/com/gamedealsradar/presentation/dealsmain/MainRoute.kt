package com.gamedealsradar.presentation.dealsmain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.koinInject

@Composable
fun MainRoute() {
    val viewModel = koinInject<MainViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    MainScreen(uiState = uiState, onAction = viewModel::handleAction)
}