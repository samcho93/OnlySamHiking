package com.onlysamhiking.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.onlysamhiking.app.data.model.HikingPhoto

@Dao
interface HikingPhotoDao {
    @Insert
    suspend fun insert(photo: HikingPhoto): Long

    @Delete
    suspend fun delete(photo: HikingPhoto)

    @Query("SELECT * FROM hiking_photos WHERE recordId = :recordId ORDER BY timestamp ASC")
    suspend fun getPhotosByRecordId(recordId: Long): List<HikingPhoto>

    @Query("SELECT * FROM hiking_photos WHERE recordId = :recordId ORDER BY timestamp ASC")
    fun getPhotosByRecordIdLive(recordId: Long): LiveData<List<HikingPhoto>>

    @Query("SELECT COUNT(*) FROM hiking_photos WHERE recordId = :recordId")
    suspend fun getPhotoCount(recordId: Long): Int

    @Query("SELECT * FROM hiking_photos WHERE recordId = :recordId ORDER BY timestamp ASC LIMIT 1")
    suspend fun getFirstPhoto(recordId: Long): HikingPhoto?
}
