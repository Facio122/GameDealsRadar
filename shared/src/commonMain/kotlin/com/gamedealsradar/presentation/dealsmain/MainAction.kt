package com.gamedealsradar.presentation.dealsmain

sealed interface MainAction {

    data object FiltersClick : MainAction
    data class FilterSelected(val filterItem: FilterItem.SelectableFilterItem) : MainAction
    data object FilterChanged : MainAction
    data class SearchValueChanged(val searchValue: String) : MainAction
    data class FilterMinPriceChanged(val minPrice: Float) : MainAction
    data class FilterMaxPriceChanged(val maxPrice: Float) : MainAction
}