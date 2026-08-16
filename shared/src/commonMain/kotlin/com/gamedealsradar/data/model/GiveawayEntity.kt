package com.gamedealsradar.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "giveaways")
data class GiveawayEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "worth") val worth: String,
    @ColumnInfo(name = "giveaway_url") val giveawayUrl: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "platforms") val platforms: String,
    @ColumnInfo(name = "stores") val stores: String,
    @ColumnInfo(name = "published_date") val publishedDate: String,
    @ColumnInfo(name = "end_date") val endDate: String,
    @ColumnInfo(name = "updated_at") val updatedAt: String,
)