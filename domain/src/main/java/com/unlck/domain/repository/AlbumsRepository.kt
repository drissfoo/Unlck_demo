package com.unlck.domain.repository

import com.unlck.domain.model.Album
import com.unlck.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {

    fun getListAlbums(forceRefresh : Boolean): Flow<Result<List<Album>>>
}