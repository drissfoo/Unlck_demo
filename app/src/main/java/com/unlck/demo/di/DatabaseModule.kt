package com.unlck.demo.di

import android.app.Application
import androidx.room.Room
import com.unlck.data.source.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}