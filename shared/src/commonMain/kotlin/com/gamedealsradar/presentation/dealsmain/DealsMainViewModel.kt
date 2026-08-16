package com.gamedealsradar.presentation.dealsmain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamedealsradar.domain.repository.GiveawayRepository
import com.gamedealsradar.logDebug
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DealsMainViewModel(
    private val giveawaysRepository: GiveawayRepository,
) : ViewModel() {

    private companion object {
        private const val TAG = "DealsMainViewModel"
    }

    private val _uiState = MutableStateFlow<DealsUiState>(DealsUiState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = DealsUiState.Loading

            giveawaysRepository.refreshGiveaways()

            try {
                giveawaysRepository.getLocalGiveaways().collect {
                    _uiState.value = DealsUiState.Success(it)
                }
            } catch (e: Exception) {
                _uiState.value = DealsUiState.Error
                logDebug(TAG, "Error fetching deals: ${e.message}")
            }
        }
    }
}