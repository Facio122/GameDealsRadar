package com.gamedealsradar.presentation.dealsmain

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction.Companion.Done
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gamedealsradar.presentation.utils.AppColors


enum class PriceFieldType {
    MIN,
    MAX
}

@Composable
fun PriceRange(
    priceFilter: FilterItem.PriceFilterItem,
    placeDivider: Boolean = true,
    handleAction: (action: MainAction) -> Unit
) {
    Column {
        Text(
            text = "Price Range",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = AppColors.TextPrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PriceField(
                label = "Min",
                priceFilter = priceFilter,
                priceFieldType = PriceFieldType.MIN,
                onValueChange = {
                    val value = it.toFloatOrNull() ?: 0.0f
                    handleAction(MainAction.FilterMinPriceChanged(value))
                }
            )
            HorizontalDivider(
                modifier = Modifier
                    .width(10.dp)
                    .height(1.dp),
                color = AppColors.TextPrimary,
                thickness = 2.dp
            )
            PriceField(
                label = "Max",
                priceFilter = priceFilter,
                priceFieldType = PriceFieldType.MAX,
                onValueChange = {
                    val value = it.toFloatOrNull() ?: 0.0f
                    handleAction(MainAction.FilterMaxPriceChanged(value))
                }
            )
        }
        PriceSlider(filterConfig = priceFilter, handleAction = handleAction)
        if (placeDivider) {
            HorizontalDivider(color = AppColors.Divider, thickness = 1.dp)
        }
    }
}

@Composable
private fun PriceField(
    label: String,
    priceFilter: FilterItem.PriceFilterItem,
    priceFieldType: PriceFieldType,
    onValueChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val currentPrice = when (priceFieldType) {
        PriceFieldType.MIN -> priceFilter.selectedMinPrice
        PriceFieldType.MAX -> priceFilter.selectedMaxPrice
    }

    var text by remember(currentPrice) {
        mutableStateOf(currentPrice.toInt().toString())
    }

    var isFocused by remember { mutableStateOf(false) }

    val allowedRange = when (priceFieldType) {
        PriceFieldType.MIN ->
            priceFilter.availableMinPrice..priceFilter.selectedMaxPrice

        PriceFieldType.MAX ->
            priceFilter.selectedMinPrice..priceFilter.availableMaxPrice
    }

    fun commitValue() {
        val value = text.toFloatOrNull() ?: currentPrice

        val coercedValue = value.coerceIn(
            allowedRange.start,
            allowedRange.endInclusive
        )

        text = coercedValue.toInt().toString()
        onValueChange(coercedValue.toString())
    }

    BasicTextField(
        value = text,
        onValueChange = { newText ->

            if (newText.isBlank()) {
                text = ""
                return@BasicTextField
            }

            if (newText.all { it.isDigit() }) {
                text = newText

                newText.toFloatOrNull()?.let { value ->
                    if (value in allowedRange) {
                        onValueChange(newText)
                    }
                }
            }
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = AppColors.TextPrimary
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                commitValue()
                keyboardController?.hide()
            }
        ),
        cursorBrush = Brush.horizontalGradient(
            colors = listOf(
                AppColors.Secondary,
                AppColors.Primary,
                AppColors.Cyan
            )
        ),
        modifier = Modifier
            .width(110.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                1.dp,
                AppColors.Divider,
                RoundedCornerShape(8.dp)
            )
            .background(AppColors.Surface)
            .padding(
                horizontal = 10.dp,
                vertical = 8.dp
            )
            .onFocusChanged { focusState ->
                if (isFocused && !focusState.isFocused) {
                    commitValue()
                }

                isFocused = focusState.isFocused
            },
        decorationBox = { innerTextField ->
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = AppColors.TextMuted
                )

                Row {
                    Text(
                        text = "$",
                        color = AppColors.TextPrimary
                    )

                    innerTextField()
                }
            }
        }
    )
}

@Composable
private fun PriceSlider(
    filterConfig: FilterItem.PriceFilterItem,
    handleAction: (action: MainAction) -> Unit
) {
    var sliderPosition by remember(filterConfig.selectedMinPrice, filterConfig.selectedMaxPrice) {
        mutableStateOf(filterConfig.selectedMinPrice..filterConfig.selectedMaxPrice)
    }

    val valueRange = maxOf(0f, filterConfig.availableMinPrice)..maxOf(0f, filterConfig.availableMaxPrice)

    RangeSlider(
        value = sliderPosition,
        onValueChange = { range ->
            sliderPosition = range
            handleAction(
                MainAction.FilterMinPriceChanged(range.start)
            )
            handleAction(
                MainAction.FilterMaxPriceChanged(range.endInclusive)
            )
        },
        valueRange = valueRange,
        steps = 0,

        startThumb = {
            PriceSliderThumb(
                value = sliderPosition.start,
                valueRange = valueRange
            )
        },

        endThumb = {
            PriceSliderThumb(
                value = sliderPosition.endInclusive,
                valueRange = valueRange
            )
        },

        track = { sliderState ->
            PriceSliderTrack(sliderState)
        }
    )
}

@Composable
private fun PriceSliderThumb(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>
) {
    val thumbColor = getSliderColor(
        value = value,
        range = valueRange
    )

    Box(
        modifier = Modifier
            .size(18.dp)
            .background(
                color = thumbColor.copy(alpha = 0.18f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(
                    AppColors.Surface,
                    CircleShape
                )
                .border(
                    2.dp,
                    thumbColor,
                    CircleShape
                )
        )
    }
}

@Composable
private fun PriceSliderTrack(
    sliderState: RangeSliderState
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(14.dp)
    ) {
        val trackHeight = 3.dp.toPx()
        val y = (size.height - trackHeight) / 2f

        val startFraction =
            (sliderState.activeRangeStart - sliderState.valueRange.start) /
                    (sliderState.valueRange.endInclusive - sliderState.valueRange.start)

        val endFraction =
            (sliderState.activeRangeEnd - sliderState.valueRange.start) /
                    (sliderState.valueRange.endInclusive - sliderState.valueRange.start)

        val startX = size.width * startFraction
        val endX = size.width * endFraction

        drawRoundRect(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    AppColors.Secondary,
                    AppColors.Primary,
                    AppColors.Cyan
                ),
                startX = 0f,
                endX = size.width
            ),
            topLeft = Offset(
                x = 0f,
                y = y
            ),
            size = Size(
                width = size.width,
                height = trackHeight
            ),
            cornerRadius = CornerRadius(
                x = trackHeight / 2,
                y = trackHeight / 2
            )
        )

        if (startX > 0f) {
            drawRoundRect(
                color = AppColors.Divider,
                topLeft = Offset(
                    x = 0f,
                    y = y
                ),
                size = Size(
                    width = startX,
                    height = trackHeight
                ),
                cornerRadius = CornerRadius(
                    trackHeight / 2
                )
            )
        }

        if (endX < size.width) {
            drawRoundRect(
                color = AppColors.Divider,
                topLeft = Offset(
                    x = endX,
                    y = y
                ),
                size = Size(
                    width = size.width - endX,
                    height = trackHeight
                ),
                cornerRadius = CornerRadius(
                    trackHeight / 2
                )
            )
        }
    }
}

private fun getSliderColor(
    value: Float,
    range: ClosedFloatingPointRange<Float>
): Color {
    val fraction =
        ((value - range.start) /
                (range.endInclusive - range.start))
            .coerceIn(0f, 1f)

    return if (fraction <= 0.5f) {
        lerp(
            AppColors.Secondary,
            AppColors.Primary,
            fraction * 2f
        )
    } else {
        lerp(
            AppColors.Primary,
            AppColors.Cyan,
            (fraction - 0.5f) * 2f
        )
    }
}

@Preview
@Composable
private fun PriceRangePreview() {
    PriceRange(
        priceFilter = FilterItem.PriceFilterItem(
            selectedMinPrice = 10f,
            selectedMaxPrice = 100f,
            availableMinPrice = 0f,
            availableMaxPrice = 200f
        ),
        handleAction = {}
    )
}