package com.gamedealsradar.presentation.dealsmain

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gamedealsradar.domain.usecase.GetFiltersUseCase
import com.gamedealsradar.presentation.utils.AppColors

@Composable
internal fun FilterPanel(
    config: List<FilterCategory>?,
    handleAction: (action: MainAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(AppColors.TileGradientStart)
            .border(
                width = 1.dp,
                color = AppColors.Divider,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 8.dp)
    ) {
        Column(
            verticalArrangement = spacedBy(8.dp)
        ) {
            config?.forEachIndexed { index, category ->
                val isLast = index == config.size - 1
                when (val firstFilter = category.filters.firstOrNull()) {
                    is FilterItem.SelectableFilterItem -> {
                        FilterCategoryLayout(
                            title = category.title,
                            filterList = category.filters.filterIsInstance<FilterItem.SelectableFilterItem>(),
                            placeDivider = !isLast,
                            handleAction = handleAction,
                        )
                    }

                    is FilterItem.PriceFilterItem -> {
                        PriceRange(
                            priceFilter = firstFilter,
                            placeDivider = !isLast,
                            handleAction = handleAction,
                        )
                    }

                    else -> {}
                }
            }
            FilterActionButtons(
                selectedCount = 3,
                onReset = { },
                onApply = { }
            )
        }
    }
}

@Composable
internal fun FilterPills(filterList: List<FilterItem>, onClick: () -> Unit, maxItems: Int = 4) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = spacedBy(2.dp),
    ) {
        filterList.take(maxItems).forEach { pill ->
            Pill(text = pill.toLabel(), onClick = onClick)
        }
        if (filterList.size > maxItems) {
            Pill(text = "+${filterList.size - maxItems}", onClick = onClick)
        }
    }
}

@Composable
internal fun Pill(text: String, isChecked: Boolean = false, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .clip(RoundedCornerShape(16.dp))
            .background(if (isChecked) AppColors.Primary.copy(alpha = 0.2f) else AppColors.TileGradientStart)
            .border(
                width = 1.dp,
                color = AppColors.Divider,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = AppColors.TextPrimary,
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
private fun FilterCategoryLayout(
    title: String,
    filterList: List<FilterItem.SelectableFilterItem>,
    placeDivider: Boolean = true,
    handleAction: (action: MainAction) -> Unit,
) {
    Column(
        verticalArrangement = spacedBy(4.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = AppColors.TextPrimary
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = spacedBy(4.dp),
            verticalArrangement = spacedBy(4.dp)
        ) {
            filterList.forEach { pill ->
                Pill(
                    text = when (pill) {
                        is FilterItem.StoreFilterItem -> pill.store.label
                        is FilterItem.PlatformFilterItem -> pill.platform.label
                        is FilterItem.TypeFilterItem -> pill.type.label
                        is FilterItem.DiscountedFilterItem -> {
                            pill.percentageDiscountedRange.let {
                                if (it.first == 0 && it.last == 100) "All"
                                else if (it.last == 100) "${it.first}%+"
                                else "${it.first}-${it.last}%"
                            }
                        }
                    },
                    isChecked = pill.isSelected,
                    onClick = { handleAction(MainAction.FilterSelected(pill)) })
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        if (placeDivider) {
            HorizontalDivider(color = AppColors.Divider, thickness = 1.dp)
        }
    }
}

@Composable
fun FilterActionButtons(
    selectedCount: Int,
    onReset: () -> Unit,
    onApply: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ClearButton(
            onClick = onReset,
            modifier = Modifier.weight(1f)
        )

        ApplyButton(
            selectedCount = selectedCount,
            onClick = onApply,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ApplyButton(
    selectedCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        AppColors.Secondary,
                        AppColors.Primary,
                        AppColors.Cyan,
                        AppColors.Discount
                    )
                )
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Apply ($selectedCount)",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = AppColors.TextPrimary
        )
    }
}

@Composable
private fun ClearButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = AppColors.BorderGradientEnd,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Clear all",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = AppColors.Secondary
        )
    }
}

private fun FilterItem.toLabel(): String {
    return when (this) {
        is FilterItem.StoreFilterItem ->
            store.label

        is FilterItem.PlatformFilterItem ->
            platform.label

        is FilterItem.TypeFilterItem ->
            type.label

        is FilterItem.DiscountedFilterItem ->
            "${percentageDiscountedRange.first}-${percentageDiscountedRange.last}%"

        is FilterItem.PriceFilterItem ->
            "$${selectedMinPrice.toInt()}-$${selectedMaxPrice.toInt()}"
    }
}

@Preview
@Composable
private fun FilterPanelPreview() {
    FilterPanel(
        config = GetFiltersUseCase().invoke(),
        handleAction = {}
    )
}
