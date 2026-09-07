package com.gamedealsradar.data.model

data class FilterEntity(
    val stores: String,
    val platforms: String,
    val types: String,
    val discounted: Int,
    val searchQuery: String,
    val minPrice: Float,
    val maxPrice: Float
)