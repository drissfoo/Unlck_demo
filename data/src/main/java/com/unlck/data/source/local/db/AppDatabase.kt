package com.unlck.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unlck.data.source.local.dao.AlbumDao
import com.unlck.data.source.local.entity.AlbumEntity

@Database(
    entities = [AlbumEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase(){
    abstract val albumDao : AlbumDao

    companion object {
        const val DATABASE_NAME = "app_database"
    }
}