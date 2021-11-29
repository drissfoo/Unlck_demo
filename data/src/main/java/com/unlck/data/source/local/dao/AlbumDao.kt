package com.unlck.data.source.local.dao

import androidx.room.*
import com.unlck.data.source.local.entity.AlbumEntity

@Dao
interface AlbumDao {
    @Query("SELECT * FROM album")
    suspend fun getListAlbums(): Array<AlbumEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumEntity>)

    @Query("DELETE from album")
    suspend fun deleteAll()
}

@Transaction
suspend fun AlbumDao.replaceAll(albums: List<AlbumEntity>){
    deleteAll()
    insertAlbums(albums)
}