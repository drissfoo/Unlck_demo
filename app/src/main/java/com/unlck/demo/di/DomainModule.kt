package com.unlck.demo.di

import com.unlck.data.repository.AlbumsRepositoryImpl
import com.unlck.data.source.local.dao.AlbumDao
import com.unlck.data.source.remote.api.ApiService
import com.unlck.domain.repository.AlbumsRepository
import com.unlck.domain.usecases.GetListAlbumsUseCase
import com.unlck.domain.util.RefreshController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideAlbumsRepository(
        albumDao: AlbumDao,
        apiService: ApiService,
        refreshController: RefreshController
    ): AlbumsRepository =
        AlbumsRepositoryImpl(albumDao, apiService, refreshController)

    @Provides
    @Singleton
    fun provideGetListAlbumsUseCase(repository: AlbumsRepository): GetListAlbumsUseCase =
        GetListAlbumsUseCase(repository)
}