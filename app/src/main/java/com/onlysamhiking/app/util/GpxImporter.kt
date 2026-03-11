package com.onlysamhiking.app.util

import android.content.Context
import android.net.Uri
import com.onlysamhiking.app.data.model.HikingRecord
import com.onlysamhiking.app.data.model.TrackPoint
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * GPX 파일 파싱 유틸리티 (사용자 등록 모드)
 */
object GpxImporter {

    data class GpxData(
        val name: String,
        val trackPoints: List<GpxTrackPoint>
    )

    data class GpxTrackPoint(
        val latitude: Double,
        val longitude: Double,
        val altitude: Double,
        val timestamp: Long,
        val speed: Float = 0f
    )

    /**
     * URI에서 GPX 파일 파싱
     */
    fun parseGpxFromUri(context: Context, uri: Uri): GpxData? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { stream ->
                parseGpx(stream)
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * GPX XML 파싱
     */
    fun parseGpx(inputStream: InputStream): GpxData? {
        return try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = false
            val parser = factory.newPullParser()
            parser.setInput(inputStream, "UTF-8")

            var trackName = ""
            val trackPoints = mutableListOf<GpxTrackPoint>()

            var currentLat = 0.0
            var currentLon = 0.0
            var currentAlt = 0.0
            var currentTime = 0L
            var currentSpeed = 0f
            var inTrkPt = false
            var inTrk = false
            var currentTag = ""

            val dateFormats = listOf(
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                },
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US),
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }
            )

            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        currentTag = parser.name
                        when (currentTag) {
                            "trk" -> inTrk = true
                            "trkpt" -> {
                                inTrkPt = true
                                currentLat = parser.getAttributeValue(null, "lat")?.toDoubleOrNull() ?: 0.0
                                currentLon = parser.getAttributeValue(null, "lon")?.toDoubleOrNull() ?: 0.0
                                currentAlt = 0.0
                                currentTime = 0L
                                currentSpeed = 0f
                            }
                        }
                    }

                    XmlPullParser.TEXT -> {
                        val text = parser.text?.trim() ?: ""
                        if (text.isNotEmpty()) {
                            when {
                                currentTag == "name" && inTrk && !inTrkPt -> {
                                    if (trackName.isEmpty()) trackName = text
                                }
                                currentTag == "ele" && inTrkPt -> {
                                    currentAlt = text.toDoubleOrNull() ?: 0.0
                                }
                                currentTag == "time" && inTrkPt -> {
                                    currentTime = parseTime(text, dateFormats)
                                }
                                currentTag == "speed" && inTrkPt -> {
                                    currentSpeed = text.toFloatOrNull() ?: 0f
                                }
                            }
                        }
                    }

                    XmlPullParser.END_TAG -> {
                        when (parser.name) {
                            "trkpt" -> {
                                inTrkPt = false
                                trackPoints.add(
                                    GpxTrackPoint(
                                        latitude = currentLat,
                                        longitude = currentLon,
                                        altitude = currentAlt,
                                        timestamp = currentTime,
                                        speed = currentSpeed
                                    )
                                )
                            }
                            "trk" -> inTrk = false
                        }
                        currentTag = ""
                    }
                }
                eventType = parser.next()
            }

            if (trackPoints.isEmpty()) return null

            // 이름이 없으면 첫 번째 시간 기반으로 생성
            if (trackName.isEmpty()) {
                val firstTime = trackPoints.firstOrNull()?.timestamp ?: System.currentTimeMillis()
                trackName = SimpleDateFormat("yyyy.MM.dd 산행", Locale.KOREAN).format(Date(firstTime))
            }

            GpxData(name = trackName, trackPoints = trackPoints)
        } catch (e: Exception) {
            null
        }
    }

    private fun parseTime(text: String, formats: List<SimpleDateFormat>): Long {
        for (format in formats) {
            try {
                return format.parse(text)?.time ?: continue
            } catch (_: Exception) {
                continue
            }
        }
        return 0L
    }

    /**
     * GpxData를 HikingRecord + TrackPoint 리스트로 변환
     */
    fun convertToRecordData(gpxData: GpxData): Pair<HikingRecord, List<TrackPoint>> {
        val points = gpxData.trackPoints

        val startTime = points.firstOrNull()?.timestamp ?: System.currentTimeMillis()
        val endTime = points.lastOrNull()?.timestamp ?: startTime

        val altitudes = points.map { it.altitude }
        val maxAlt = altitudes.maxOrNull() ?: 0.0
        val minAlt = altitudes.minOrNull() ?: 0.0

        // 거리 계산
        var totalDistance = 0.0
        var elevGain = 0.0
        var elevLoss = 0.0
        var maxSpeed = 0.0

        for (i in 1 until points.size) {
            val prev = points[i - 1]
            val curr = points[i]

            totalDistance += LocationUtils.distanceInMeters(
                prev.latitude, prev.longitude,
                curr.latitude, curr.longitude
            )

            val altDiff = curr.altitude - prev.altitude
            if (altDiff > 0) elevGain += altDiff
            else elevLoss += -altDiff

            if (curr.speed > maxSpeed) maxSpeed = curr.speed.toDouble()
        }

        val durationMs = if (endTime > startTime) endTime - startTime else 0L
        val durationHours = durationMs / 3600000.0
        val avgSpeed = if (durationHours > 0) (totalDistance / 1000.0) / durationHours else 0.0
        val calories = (totalDistance / 1000.0 * 60).toInt()  // 대략 60kcal/km

        val record = HikingRecord(
            mountainName = gpxData.name,
            startTime = startTime,
            endTime = endTime,
            distance = totalDistance,
            maxAltitude = maxAlt,
            minAltitude = minAlt,
            elevationGain = elevGain,
            elevationLoss = elevLoss,
            avgSpeed = avgSpeed,
            maxSpeed = maxSpeed * 3.6,  // m/s → km/h
            calories = calories,
            startLat = points.firstOrNull()?.latitude ?: 0.0,
            startLng = points.firstOrNull()?.longitude ?: 0.0,
            endLat = points.lastOrNull()?.latitude ?: 0.0,
            endLng = points.lastOrNull()?.longitude ?: 0.0,
            isUserImported = true
        )

        // TrackPoint 변환 (recordId는 나중에 설정)
        val trackPointList = points.map { gpt ->
            TrackPoint(
                recordId = 0,  // 저장 시 갱신
                latitude = gpt.latitude,
                longitude = gpt.longitude,
                altitude = gpt.altitude,
                speed = gpt.speed,
                timestamp = gpt.timestamp
            )
        }

        return Pair(record, trackPointList)
    }
}
