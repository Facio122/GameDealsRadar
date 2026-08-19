package com.gamedealsradar.domain.usecase

import com.gamedealsradar.domain.model.GiveawayType
import com.gamedealsradar.domain.model.Platform
import com.gamedealsradar.domain.model.Store
import com.gamedealsradar.presentation.dealsmain.FilterCategory
import com.gamedealsradar.presentation.dealsmain.FilterItem

class GetFiltersUseCase {

    operator fun invoke(): List<FilterCategory> {
        val storeFilters = Store.entries
            .filter { it != Store.UNKNOWN }
            .map { store ->
                FilterItem.StoreFilterItem(
                    store = store,
                    isSelected = false
                )
            }

        val platformFilters = Platform.entries
            .filter { it != Platform.UNKNOWN }
            .map { platform ->
                FilterItem.PlatformFilterItem(
                    platform = platform,
                    isSelected = false
                )
            }

        val typeFilters = GiveawayType.entries
            .filter { it != GiveawayType.UNKNOWN }
            .map { type ->
                FilterItem.TypeFilterItem(
                    type = type,
                    isSelected = false
                )
            }

        val discountFilters = listOf(
            FilterItem.DiscountedFilterItem(
                percentageDiscountedRange = 0..24,
                isSelected = false
            ),
            FilterItem.DiscountedFilterItem(
                percentageDiscountedRange = 25..49,
                isSelected = false
            ),
            FilterItem.DiscountedFilterItem(
                percentageDiscountedRange = 50..74,
                isSelected = false
            ),
            FilterItem.DiscountedFilterItem(
                percentageDiscountedRange = 75..100,
                isSelected = false
            )
        )

        val priceFilters = listOf(
            FilterItem.PriceFilterItem(
                priceRange = 0.0..150.0
            )
        )

        return listOf(
            FilterCategory.Store(
                displayTitle = "Store",
                filters = storeFilters
            ),
            FilterCategory.Platform(
                displayTitle = "Platform",
                filters = platformFilters
            ),
            FilterCategory.Type(
                displayTitle = "Type",
                filters = typeFilters
            ),
            FilterCategory.Discount(
                displayTitle = "Discount",
                filters = discountFilters
            ),
            FilterCategory.Price(
                displayTitle = "Price",
                filters = priceFilters
            )
        )
    }
}