package com.onlysamhiking.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "track_points",
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
data class TrackPoint(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val recordId: Long,
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val speed: Float = 0f,        // m/s
    val accuracy: Float = 0f,     // meters
    val timestamp: Long           // epoch millis
)
