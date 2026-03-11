package com.onlysamhiking.app.data.repository

import android.content.Context
import com.onlysamhiking.app.data.db.HikingDatabase
import com.onlysamhiking.app.data.model.HikingPhoto
import com.onlysamhiking.app.data.model.HikingRecord
import com.onlysamhiking.app.data.model.TrackPoint

class HikingRepository(context: Context) {

    private val db = HikingDatabase.getDatabase(context)
    private val recordDao = db.hikingRecordDao()
    private val trackPointDao = db.trackPointDao()
    private val photoDao = db.hikingPhotoDao()

    // Records
    fun getAllRecords() = recordDao.getAllRecords()
    fun getAppRecords() = recordDao.getAppRecords()
    fun getUserImportedRecords() = recordDao.getUserImportedRecords()
    suspend fun getAllRecordsList() = recordDao.getAllRecordsList()
    suspend fun getRecordById(id: Long) = recordDao.getRecordById(id)
    fun getRecordByIdLive(id: Long) = recordDao.getRecordByIdLive(id)

    suspend fun insertRecord(record: HikingRecord): Long = recordDao.insert(record)
    suspend fun updateRecord(record: HikingRecord) = recordDao.update(record)
    suspend fun deleteRecord(record: HikingRecord) = recordDao.delete(record)
    suspend fun deleteRecordById(id: Long) = recordDao.deleteById(id)

    // Track Points
    suspend fun insertTrackPoint(point: TrackPoint) = trackPointDao.insert(point)
    suspend fun insertTrackPoints(points: List<TrackPoint>) = trackPointDao.insertAll(points)
    suspend fun getTrackPoints(recordId: Long) = trackPointDao.getPointsByRecordId(recordId)
    fun getTrackPointsLive(recordId: Long) = trackPointDao.getPointsByRecordIdLive(recordId)

    // Photos
    suspend fun insertPhoto(photo: HikingPhoto) = photoDao.insert(photo)
    suspend fun deletePhoto(photo: HikingPhoto) = photoDao.delete(photo)
    suspend fun getPhotos(recordId: Long) = photoDao.getPhotosByRecordId(recordId)
    fun getPhotosLive(recordId: Long) = photoDao.getPhotosByRecordIdLive(recordId)
    suspend fun getPhotoCount(recordId: Long) = photoDao.getPhotoCount(recordId)
    suspend fun getFirstPhoto(recordId: Long) = photoDao.getFirstPhoto(recordId)
}
