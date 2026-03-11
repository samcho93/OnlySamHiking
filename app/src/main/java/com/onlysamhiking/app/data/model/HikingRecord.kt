package com.onlysamhiking.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hiking_records")
data class HikingRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val mountainName: String = "",
    val startTime: Long = 0L,
    val endTime: Long = 0L,
    val distance: Double = 0.0,          // meters
    val maxAltitude: Double = 0.0,       // meters
    val minAltitude: Double = 0.0,       // meters
    val elevationGain: Double = 0.0,     // meters (누적 상승)
    val elevationLoss: Double = 0.0,     // meters (누적 하강)
    val avgSpeed: Double = 0.0,          // km/h
    val maxSpeed: Double = 0.0,          // km/h
    val calories: Int = 0,               // kcal
    val startLat: Double = 0.0,
    val startLng: Double = 0.0,
    val endLat: Double = 0.0,
    val endLng: Double = 0.0,
    val memo: String = "",
    val isUserImported: Boolean = false  // 사용자 등록 모드 (GPX 가져오기)
) {
    val durationMillis: Long get() = endTime - startTime

    val durationFormatted: String get() {
        val totalSec = durationMillis / 1000
        val h = totalSec / 3600
        val m = (totalSec % 3600) / 60
        val s = totalSec % 60
        return String.format("%02d:%02d:%02d", h, m, s)
    }

    val distanceKm: Double get() = distance / 1000.0
}
