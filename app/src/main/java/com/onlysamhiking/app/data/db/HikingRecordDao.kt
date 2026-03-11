package com.onlysamhiking.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.onlysamhiking.app.data.model.HikingRecord

@Dao
interface HikingRecordDao {
    @Insert
    suspend fun insert(record: HikingRecord): Long

    @Update
    suspend fun update(record: HikingRecord)

    @Delete
    suspend fun delete(record: HikingRecord)

    @Query("SELECT * FROM hiking_records ORDER BY startTime DESC")
    fun getAllRecords(): LiveData<List<HikingRecord>>

    @Query("SELECT * FROM hiking_records ORDER BY startTime DESC")
    suspend fun getAllRecordsList(): List<HikingRecord>

    @Query("SELECT * FROM hiking_records WHERE id = :id")
    suspend fun getRecordById(id: Long): HikingRecord?

    @Query("SELECT * FROM hiking_records WHERE id = :id")
    fun getRecordByIdLive(id: Long): LiveData<HikingRecord?>

    @Query("DELETE FROM hiking_records WHERE id = :id")
    suspend fun deleteById(id: Long)

    // 앱 기록 (isUserImported = false)
    @Query("SELECT * FROM hiking_records WHERE isUserImported = 0 ORDER BY startTime DESC")
    fun getAppRecords(): LiveData<List<HikingRecord>>

    // 사용자 등록 기록 (isUserImported = true)
    @Query("SELECT * FROM hiking_records WHERE isUserImported = 1 ORDER BY startTime DESC")
    fun getUserImportedRecords(): LiveData<List<HikingRecord>>
}
