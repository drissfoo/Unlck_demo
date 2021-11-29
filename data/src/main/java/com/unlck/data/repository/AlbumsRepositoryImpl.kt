package com.unlck.data.repository

import com.unlck.data.source.local.dao.AlbumDao
import com.unlck.data.source.local.dao.replaceAll
import com.unlck.data.source.local.entity.AlbumEntity
import com.unlck.data.source.remote.api.ApiService
import com.unlck.data.source.remote.entity.AlbumResponse
import com.unlck.data.util.toAlbumEntity
import com.unlck.data.util.toAlbumModel
import com.unlck.domain.model.Album
import com.unlck.domain.repository.AlbumsRepository
import com.unlck.domain.util.RefreshController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.unlck.domain.util.Result

class AlbumsRepositoryImpl @Inject constructor(
    private val albumDao: AlbumDao,
    private val apiService: ApiService,
    private val refreshController: RefreshController
) : AlbumsRepository {

    override fun getListAlbums(forceRefresh: Boolean): Flow<Result<List<Album>>> = flow {
        getFromLocal().run { emit(Result.fromNullable(this)) }
        if (refreshController.isExpired() || forceRefresh) {
            getFromRemote()?.map(AlbumResponse::toAlbumModel).run { emit(Result.fromNullable(this)) }
        }
    }

    private suspend fun getFromLocal(): List<Album>? = kotlin.runCatching {
        albumDao.getListAlbums().map(AlbumEntity::toAlbumModel)
    }.getOrNull()

    private suspend fun getFromRemote(): List<AlbumResponse>? = kotlin.runCatching {
        apiService.getAlbums()
    }.getOrNull()?.also {
        kotlin.runCatching {
            albumDao.replaceAll(it.map(AlbumResponse::toAlbumEntity))
            refreshController.refresh()
        }
    }
}