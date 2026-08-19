package com.gamedealsradar.presentation.dealsmain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamedealsradar.domain.repository.GiveawayRepository
import com.gamedealsradar.domain.usecase.GetFiltersUseCase
import com.gamedealsradar.logDebug
import com.gamedealsradar.presentation.dealsmain.composables.DealsAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val giveawaysRepository: GiveawayRepository,
    private val getFiltersUseCase: GetFiltersUseCase
) : ViewModel() {

    private companion object {
        private const val TAG = "DealsMainViewModel"
    }

    private val _uiState = MutableStateFlow(MainUiState(
        filterPanelConfig = null,
        isFilterPanelOpened = false,
        filterPills = emptyList(),
        dealsState = DealsUiState()
    ))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                filterPanelConfig = getFiltersUseCase(),
                dealsState = DealsUiState(
                    deals = emptyList(),
                    dealStatus = DealsStatus.LOADING
                )
            )

            giveawaysRepository.refreshGiveaways()

            try {
                giveawaysRepository
                    .getLocalGiveaways()
                    .collect {
                        _uiState.value = _uiState.value.copy(
                            dealsState = DealsUiState(
                                deals = it,
                                dealStatus = DealsStatus.SUCCESS
                            ),
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    dealsState = DealsUiState(
                        deals = emptyList(),
                        dealStatus = DealsStatus.ERROR
                    )
                )
                logDebug(TAG, "Error fetching deals: ${e.message}")
            }
        }
    }

    fun handleAction(action: DealsAction) {
        when (action) {
            is DealsAction.OnPillsClicked -> {
                openFilterDialog()
            }

            is DealsAction.OnSearchFocus -> {
                openFilterDialog()
            }
        }
    }

    private fun openFilterDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                isFilterPanelOpened = true
            )
        }
    }
}