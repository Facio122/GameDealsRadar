package com.gamedealsradar.presentation.dealsmain

sealed class FilterCategory(
    val title: String,
    open val filters: List<FilterItem>
) {
    data class Store(
        val displayTitle: String,
        override val filters: List<FilterItem.StoreFilterItem>
    ) : FilterCategory(displayTitle, filters)

    data class Platform(
        val displayTitle: String,
        override val filters: List<FilterItem.PlatformFilterItem>
    ) : FilterCategory(displayTitle, filters)

    data class Type(
        val displayTitle: String,
        override val filters: List<FilterItem.TypeFilterItem>
    ) : FilterCategory(displayTitle, filters)

    data class Discount(
        val displayTitle: String,
        override val filters: List<FilterItem.DiscountedFilterItem>
    ) : FilterCategory(displayTitle, filters)

    data class Price(
        val displayTitle: String,
        override val filters: List<FilterItem.PriceFilterItem>
    ) : FilterCategory(displayTitle, filters)
}

