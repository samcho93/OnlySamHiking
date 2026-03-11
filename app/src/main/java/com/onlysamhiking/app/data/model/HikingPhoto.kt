package com.onlysamhiking.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "hiking_photos",
    foreignKeys = [
        ForeignKey(
            entity = HikingRecord::class,
            parentColumns = ["id"],
            childColumns = ["recordId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("recordId")]
)
data class HikingPhoto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val recordId: Long,
    val filePath: String,
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val timestamp: Long
)
