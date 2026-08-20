package com.gamedealsradar.presentation.utils

import com.gamedealsradar.presentation.dealsmain.FilterCategory
import com.gamedealsradar.presentation.dealsmain.FilterItem

fun FilterCategory.replaceFilter(
    oldFilter: FilterItem,
    newFilter: FilterItem
): FilterCategory {
    return copy(
        filters = filters.map {
            if (it == oldFilter) newFilter else it
        }
    )
}

fun FilterItem.SelectableFilterItem.toggleSelection(): FilterItem.SelectableFilterItem {
    return when (this) {
        is FilterItem.StoreFilterItem ->
            copy(isSelected = !isSelected)

        is FilterItem.PlatformFilterItem ->
            copy(isSelected = !isSelected)

        is FilterItem.TypeFilterItem ->
            copy(isSelected = !isSelected)

        is FilterItem.DiscountedFilterItem ->
            copy(isSelected = !isSelected)
    }
}