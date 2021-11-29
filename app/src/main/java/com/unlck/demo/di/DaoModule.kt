package com.unlck.demo.di

import com.unlck.data.source.local.dao.AlbumDao
import com.unlck.data.source.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    @Singleton
    fun provideAlbumDao(db: AppDatabase): AlbumDao = db.albumDao
}