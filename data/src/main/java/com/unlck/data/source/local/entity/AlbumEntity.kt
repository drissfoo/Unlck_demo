package com.unlck.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
class AlbumEntity(
    @PrimaryKey
    val id: Int = 0,
    val albumId: Int?,
    val title: String?,
    val url: String?,
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String?
)
