package com.onlysamhiking.app.util

import android.content.Context
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import com.onlysamhiking.app.data.model.HikingPhoto
import java.io.File
import java.io.InputStream

/**
 * 사진 파일의 EXIF 데이터에서 GPS 좌표를 읽는 유틸리티
 */
object ExifHelper {

    data class GpsCoordinates(
        val latitude: Double,
        val longitude: Double,
        val altitude: Double = 0.0
    )

    /**
     * 파일 경로에서 EXIF GPS 좌표 읽기
     */
    fun getGpsFromFile(filePath: String): GpsCoordinates? {
        return try {
            val file = File(filePath)
            if (!file.exists()) return null

            val exif = ExifInterface(filePath)
            extractGps(exif)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Uri에서 EXIF GPS 좌표 읽기
     */
    fun getGpsFromUri(context: Context, uri: Uri): GpsCoordinates? {
        return try {
            val inputStream: InputStream = context.contentResolver.openInputStream(uri) ?: return null
            inputStream.use {
                val exif = ExifInterface(it)
                extractGps(exif)
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * ExifInterface에서 GPS 좌표 추출
     */
    private fun extractGps(exif: ExifInterface): GpsCoordinates? {
        val latLong = exif.latLong ?: return null

        val latitude = latLong[0]
        val longitude = latLong[1]

        // 유효한 좌표인지 확인
        if (latitude == 0.0 && longitude == 0.0) return null

        val altitude = exif.getAltitude(0.0)

        return GpsCoordinates(latitude, longitude, altitude)
    }

    /**
     * 사진 리스트에서 (0,0) 좌표인 사진들의 EXIF GPS 정보를 읽어 좌표 보정
     * 원본 리스트를 수정하지 않고, 보정된 새 리스트를 반환
     */
    fun fillMissingCoordinates(photos: List<HikingPhoto>): List<HikingPhoto> {
        return photos.map { photo ->
            if (photo.latitude == 0.0 && photo.longitude == 0.0) {
                val gps = getGpsFromFile(photo.filePath)
                if (gps != null) {
                    photo.copy(
                        latitude = gps.latitude,
                        longitude = gps.longitude,
                        altitude = if (photo.altitude == 0.0) gps.altitude else photo.altitude
                    )
                } else {
                    photo
                }
            } else {
                photo
            }
        }
    }
}
