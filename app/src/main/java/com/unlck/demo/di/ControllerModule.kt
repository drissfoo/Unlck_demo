package com.unlck.demo.di

import com.unlck.domain.util.RefreshController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ControllerModule {

    @Provides
    @Singleton
    fun provideRefreshController(): RefreshController = RefreshController()
}