package com.gamedealsradar.presentation.dealsmain

import com.gamedealsradar.domain.model.GiveawayType
import com.gamedealsradar.domain.model.Platform
import com.gamedealsradar.domain.model.Store

sealed interface FilterItem {

    sealed interface SelectableFilterItem : FilterItem {
        val isSelected: Boolean
    }

    data class StoreFilterItem(
        val store: Store,
        override val isSelected: Boolean
    ) : SelectableFilterItem

    data class PlatformFilterItem(
        val platform: Platform,
        override val isSelected: Boolean
    ) : SelectableFilterItem

    data class TypeFilterItem(
        val type: GiveawayType,
        override val isSelected: Boolean
    ) : SelectableFilterItem

    data class DiscountedFilterItem(
        val percentageDiscountedRange: IntRange,
        override val isSelected: Boolean
    ) : SelectableFilterItem

    data class PriceFilterItem(
        val priceRange: ClosedFloatingPointRange<Double>,
    ) : FilterItem
}