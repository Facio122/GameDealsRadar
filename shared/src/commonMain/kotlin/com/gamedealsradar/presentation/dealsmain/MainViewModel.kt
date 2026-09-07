package com.gamedealsradar.presentation.dealsmain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamedealsradar.domain.model.FilterModel
import com.gamedealsradar.domain.repository.DealRepository
import com.gamedealsradar.domain.usecase.GetFiltersUseCase
import com.gamedealsradar.logDebug
import com.gamedealsradar.presentation.utils.replaceFilter
import com.gamedealsradar.presentation.utils.toggleSelection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val giveawaysRepository: DealRepository,
    private val getFiltersUseCase: GetFiltersUseCase
) : ViewModel() {

    private companion object {
        private const val TAG = "DealsMainViewModel"
    }

    private val _uiState = MutableStateFlow(
        MainUiState(
            filterPanelConfig = null,
            isFilterPanelOpened = false,
            filterPills = emptyList(),
            searchValue = "",
            dealsState = DealsUiState()
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    filterPanelConfig = getFiltersUseCase(),
                    dealsState = DealsUiState(
                        deals = emptyList(),
                        dealStatus = DealsStatus.LOADING
                    )
                )
            }

            giveawaysRepository.refreshDeals()

            try {
                giveawaysRepository
                    .getAllLocalDeals()
                    .collect { deals ->
                        _uiState.update {
                            it.copy(
                                dealsState = DealsUiState(
                                    deals = deals,
                                    dealStatus = DealsStatus.SUCCESS
                                ),
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        dealsState = DealsUiState(
                            deals = emptyList(),
                            dealStatus = DealsStatus.ERROR
                        )
                    )
                }
                logDebug(TAG, "Error fetching deals: ${e.message}")
            }
        }
    }

    fun handleAction(action: MainAction) {
        when (action) {
            is MainAction.SearchValueChanged -> {
                updateSearchQuery(action.searchValue)
            }

            is MainAction.FiltersClick -> {
                openFilterDialog()
            }

            is MainAction.FilterSelected -> {
                selectFilter(action.filterItem)
            }

            is MainAction.FilterMaxPriceChanged -> {
                val currentMinPrice = _uiState.value
                    .filterPanelConfig
                    ?.find { it.filters.firstOrNull() is FilterItem.PriceFilterItem }
                    ?.filters
                    ?.filterIsInstance<FilterItem.PriceFilterItem>()
                    ?.firstOrNull()
                    ?.selectedMinPrice
                    ?: 0.0f

                changeFilterPrice(
                    minPrice = currentMinPrice,
                    maxPrice = action.maxPrice
                )
            }

            is MainAction.FilterMinPriceChanged -> {
                val currentMaxPrice = _uiState.value
                    .filterPanelConfig
                    ?.find { it.filters.firstOrNull() is FilterItem.PriceFilterItem }
                    ?.filters
                    ?.filterIsInstance<FilterItem.PriceFilterItem>()
                    ?.firstOrNull()
                    ?.selectedMaxPrice
                    ?: 0.0f

                changeFilterPrice(
                    minPrice = action.minPrice,
                    maxPrice = currentMaxPrice,
                )
            }

            is MainAction.FilterChanged -> {
                reloadFilteredGiveaways()
            }
        }
    }

    private fun openFilterDialog() {
        _uiState.update { currentState ->
            val newState = currentState.copy(
                isFilterPanelOpened = !currentState.isFilterPanelOpened
            )
            newState
        }
    }

    private fun selectFilter(filterItem: FilterItem.SelectableFilterItem) {
        val newFilterItem = filterItem.toggleSelection()

        _uiState.update { currentState ->
            currentState.copy(
                filterPanelConfig = currentState.filterPanelConfig?.map { category ->
                    category.replaceFilter(filterItem, newFilterItem)
                }
            )
        }
    }

    private fun reloadFilteredGiveaways() {
        viewModelScope.launch {
            try {
                giveawaysRepository
                    .getFilteredDeals(filters = _uiState.value.toFilterModel())
                    .collect { deals ->
                        _uiState.update {
                            it.copy(
                                dealsState = DealsUiState(
                                    deals = deals,
                                    dealStatus = DealsStatus.SUCCESS
                                ),
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        dealsState = DealsUiState(
                            deals = emptyList(),
                            dealStatus = DealsStatus.ERROR
                        )
                    )
                }
                logDebug(TAG, "Error fetching deals: ${e.message}")
            }
        }
    }

    private fun changeFilterPrice(minPrice: Float, maxPrice: Float) {
        val safeMin = maxOf(0.0f, minOf(minPrice, maxPrice))
        val safeMax = maxOf(0.0f, maxOf(minPrice, maxPrice))
        _uiState.update { currentState ->
            currentState.copy(
                filterPanelConfig = currentState.filterPanelConfig?.map { category ->
                    if (category.filters.firstOrNull() is FilterItem.PriceFilterItem) {
                        category.copy(
                            filters = category.filters.map { filter ->
                                if (filter is FilterItem.PriceFilterItem) {
                                    filter.copy(
                                        selectedMinPrice = safeMin,
                                        selectedMaxPrice = safeMax,
                                    )
                                } else {
                                    filter
                                }
                            }
                        )
                    } else {
                        category
                    }
                }
            )

        }
    }

    private fun MainUiState.toFilterModel(): FilterModel {
        return FilterModel(
            searchQuery = searchValue,
            stores = filterPanelConfig?.flatMap { it.filters }
                ?.filterIsInstance<FilterItem.StoreFilterItem>()
                ?.filter { it.isSelected }
                ?.map { it.store.label }
                ?: emptyList(),
            platforms = filterPanelConfig?.flatMap { it.filters }
                ?.filterIsInstance<FilterItem.PlatformFilterItem>()
                ?.filter { it.isSelected }
                ?.map { it.platform.label }
                ?: emptyList(),
            types = filterPanelConfig?.flatMap { it.filters }
                ?.filterIsInstance<FilterItem.TypeFilterItem>()
                ?.filter { it.isSelected }
                ?.map { it.type.label }
                ?: emptyList(),
            priceRange = filterPanelConfig?.flatMap { it.filters }
                ?.filterIsInstance<FilterItem.PriceFilterItem>()
                ?.firstOrNull()
                ?.let { it.selectedMinPrice..it.selectedMaxPrice }
                ?: 0.0f..0.0f,
            discounted = filterPanelConfig?.flatMap { it.filters }
                ?.filterIsInstance<FilterItem.DiscountedFilterItem>()
                ?.filter { it.isSelected }
                ?.maxOfOrNull { it.percentageDiscountedAtLeast }
                ?: 0
        )
    }

    private fun updateSearchQuery(searchValue: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchValue = searchValue
            )
        }
    }
}