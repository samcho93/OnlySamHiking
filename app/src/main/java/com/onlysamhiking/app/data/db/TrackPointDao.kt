package com.onlysamhiking.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.onlysamhiking.app.data.model.TrackPoint

@Dao
interface TrackPointDao {
    @Insert
    suspend fun insert(point: TrackPoint): Long

    @Insert
    suspend fun insertAll(points: List<TrackPoint>)

    @Query("SELECT * FROM track_points WHERE recordId = :recordId ORDER BY timestamp ASC")
    suspend fun getPointsByRecordId(recordId: Long): List<TrackPoint>

    @Query("SELECT * FROM track_points WHERE recordId = :recordId ORDER BY timestamp ASC")
    fun getPointsByRecordIdLive(recordId: Long): LiveData<List<TrackPoint>>

    @Query("SELECT COUNT(*) FROM track_points WHERE recordId = :recordId")
    suspend fun getPointCount(recordId: Long): Int

    @Query("DELETE FROM track_points WHERE recordId = :recordId")
    suspend fun deleteByRecordId(recordId: Long)
}
