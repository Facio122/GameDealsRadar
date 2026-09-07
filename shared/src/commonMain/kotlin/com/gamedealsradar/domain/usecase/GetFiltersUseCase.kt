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
                percentageDiscountedAtLeast = 25,
                isSelected = false
            ),
            FilterItem.DiscountedFilterItem(
                percentageDiscountedAtLeast = 50,
                isSelected = false
            ),
            FilterItem.DiscountedFilterItem(
                percentageDiscountedAtLeast = 75,
                isSelected = false
            ),
            FilterItem.DiscountedFilterItem(
                percentageDiscountedAtLeast = 100,
                isSelected = false
            )
        )

        val priceFilters = listOf(
            FilterItem.PriceFilterItem(
                selectedMinPrice = 0f,
                selectedMaxPrice = 200f,
                availableMinPrice = 0f,
                availableMaxPrice = 200f
            )
        )

        return listOf(
            FilterCategory(
                title = "Store",
                filters = storeFilters
            ),
            FilterCategory(
                title = "Platform",
                filters = platformFilters
            ),
            FilterCategory(
                title = "Type",
                filters = typeFilters
            ),
            FilterCategory(
                title = "Price",
                filters = priceFilters
            ),
            FilterCategory(
                title = "Discount",
                filters = discountFilters
            ),
        )
    }
}