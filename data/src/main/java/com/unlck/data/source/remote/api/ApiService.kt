package com.unlck.data.source.remote.api

import com.unlck.data.source.remote.entity.AlbumResponse
import retrofit2.http.GET

interface ApiService {

    @GET("/img/shared/technical-test.json")
    suspend fun getAlbums(): List<AlbumResponse>?
}