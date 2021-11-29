package com.unlck.domain.usecases

import com.unlck.domain.model.Album
import com.unlck.domain.repository.AlbumsRepository
import com.unlck.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListAlbumsUseCase @Inject constructor(private val repository: AlbumsRepository){

    operator fun invoke(forceRefresh: Boolean): Flow<Result<List<Album>>> =
        repository.getListAlbums(forceRefresh)
}