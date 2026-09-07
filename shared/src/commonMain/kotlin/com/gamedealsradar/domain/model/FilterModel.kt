package com.gamedealsradar.domain.model

data class FilterModel(
    val stores: List<String>,
    val platforms: List<String>,
    val types: List<String>,
    val discounted: Int,
    val priceRange: ClosedRange<Float>,
    val searchQuery: String,
)
