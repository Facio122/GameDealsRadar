package com.gamedealsradar.presentation.dealsmain

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gamedealsradar.presentation.dealsmain.composables.DealsAction
import com.gamedealsradar.presentation.utils.AppColors
import com.gamedealsradar.presentation.utils.AppIcons
import org.jetbrains.compose.resources.painterResource

@Composable
fun Search(
    uiState: MainUiState,
    onFocused: () -> Unit,
    handleAction: (action: DealsAction) -> Unit
) {
    var filterPanelExpanded by remember { mutableStateOf(uiState.isFilterPanelOpened) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = AppColors.Surface),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        SearchField(
            query = "",
            onQueryChange = { },
            onFocused = onFocused,
        )

        Spacer(modifier = Modifier.height(8.dp))

        FilterPills(
            filterList = listOf("PC", "Steam", "Epic Games Store", "PlayStation", "Xbox"),
            onClick = {
                handleAction(DealsAction.OnPillsClicked)
                filterPanelExpanded = !filterPanelExpanded
            }
        )
            AnimatedVisibility(
                visible = filterPanelExpanded,
                enter = expandVertically(
                    expandFrom = Alignment.Top,
                    animationSpec = tween(200)
                ) + fadeIn(
                    animationSpec = tween(150)
                ),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top,
                    animationSpec = tween(200)
                ) + fadeOut(
                    animationSpec = tween(150)
                )
            ) {
                Column {
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    FilterPanel(uiState.filterPanelConfig) { }
                }
            }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            AppColors.Secondary,
                            AppColors.Cyan,
                            AppColors.Discount,
                            Color.Transparent
                        )
                    )
                )
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    onFocused: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var text by remember { mutableStateOf("") }

    var isFocused by remember {
        mutableStateOf(false)
    }

    val borderBrush = if (isFocused) {
        Brush.horizontalGradient(
            colors = listOf(
                AppColors.BorderGradientStartFocused,
                AppColors.BorderGradientEndFocused,
                AppColors.BorderGradientStartFocused
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                AppColors.BorderGradientStart,
                AppColors.BorderGradientEnd,
                AppColors.BorderGradientStart
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        AppColors.TileGradientStart,
                        AppColors.TileGradientEnd,
                        AppColors.TileGradientStart,
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = borderBrush,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { newText ->
                onQueryChange(newText)
                text = newText
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isFocused = it.isFocused
                    if (it.isFocused) {
                        onFocused()
                    }
                },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            placeholder = {
                Text(
                    text = "Search deals...",
                    color = AppColors.TextMuted
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(AppIcons.Search),
                    contentDescription = null,
                    tint = AppColors.TextSecondary
                )
            },
            trailingIcon = {
                if (query.isNotBlank()) {
                    IconButton(
                        onClick = { onQueryChange("") }
                    ) {
                        Icon(
                            painter = painterResource(AppIcons.Close),
                            contentDescription = "Clear search",
                            tint = AppColors.TextMuted
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,

                focusedTextColor = AppColors.TextPrimary,
                unfocusedTextColor = AppColors.TextPrimary,

                cursorColor = AppColors.TextMuted,

                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            )
        )
    }
}

@Preview
@Composable
private fun SearchComponentPreview() {
    Search(
        uiState = MainUiState(
            filterPanelConfig = null,
            isFilterPanelOpened = true,
            filterPills = emptyList(),
            dealsState = DealsUiState(
                deals = emptyList(),
                dealStatus = DealsStatus.SUCCESS
            )
        ),
        onFocused = { },
        handleAction = { }
    )
}
