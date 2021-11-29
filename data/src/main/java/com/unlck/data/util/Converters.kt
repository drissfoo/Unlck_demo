package com.unlck.data.util

import com.unlck.data.source.local.entity.AlbumEntity
import com.unlck.data.source.remote.entity.AlbumResponse
import com.unlck.domain.model.Album

fun AlbumResponse.toAlbumModel(): Album = Album(
    albumId = albumId,
    id = id,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)

fun AlbumEntity.toAlbumModel(): Album = Album(
    albumId = albumId,
    id = id,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)

fun AlbumResponse.toAlbumEntity(): AlbumEntity = AlbumEntity(
    albumId = albumId,
    id = id ?: 0,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)

