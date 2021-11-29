package com.unlck.demo.ui.listalbum

import com.unlck.domain.model.Album

object FakeData {

    val album1: Album = Album(1, 1, "title album 1", "url.thumb1", "url.image1")
    val album2: Album = Album(1, 2, "title album 2", "url.thumb2", "url.image2")
    val albums: List<Album> = listOf(album1, album2)

}
