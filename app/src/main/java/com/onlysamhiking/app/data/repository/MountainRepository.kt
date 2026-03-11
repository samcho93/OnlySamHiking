package com.onlysamhiking.app.data.repository

import android.content.Context
import com.onlysamhiking.app.data.model.Mountain
import com.onlysamhiking.app.data.model.TrackPoint
import com.onlysamhiking.app.util.LocationUtils
import org.json.JSONArray

class MountainRepository(private val context: Context) {

    private var mountains: List<Mountain>? = null

    fun loadMountains(): List<Mountain> {
        mountains?.let { return it }

        val json = context.assets.open("mountains.json")
            .bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(json)
        val list = mutableListOf<Mountain>()

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            list.add(
                Mountain(
                    name = obj.getString("name"),
                    nameEn = obj.optString("nameEn", ""),
                    lat = obj.getDouble("lat"),
                    lng = obj.getDouble("lng"),
                    alt = obj.getInt("alt"),
                    region = obj.optString("region", "")
                )
            )
        }
        mountains = list
        return list
    }

    fun findNearestMountain(lat: Double, lng: Double): Mountain? {
        val allMountains = loadMountains()
        return allMountains
            .filter { LocationUtils.distanceInMeters(lat, lng, it.lat, it.lng) < 10000 } // 10km
            .minByOrNull { LocationUtils.distanceInMeters(lat, lng, it.lat, it.lng) }
    }

    fun findNearbyPeaks(lat: Double, lng: Double, radiusMeters: Double = 50.0): List<Pair<Mountain, Double>> {
        val allMountains = loadMountains()
        return allMountains
            .map { it to LocationUtils.distanceInMeters(lat, lng, it.lat, it.lng) }
            .filter { it.second <= radiusMeters }
            .sortedBy { it.second }
    }

    fun findMountainsInRadius(lat: Double, lng: Double, radiusMeters: Double = 5000.0): List<Pair<Mountain, Double>> {
        val allMountains = loadMountains()
        return allMountains
            .map { it to LocationUtils.distanceInMeters(lat, lng, it.lat, it.lng) }
            .filter { it.second <= radiusMeters }
            .sortedBy { it.second }
    }

    fun getMountainName(lat: Double, lng: Double): String {
        val nearest = findNearestMountain(lat, lng)
        return nearest?.name ?: "알 수 없는 위치"
    }

    /**
     * 경로(트랙포인트)를 따라 100m 이내에 있는 산을 찾음
     * 없으면 null 반환 (호출자가 날짜 형식으로 대체)
     */
    fun findMountainAlongRoute(points: List<TrackPoint>): Mountain? {
        if (points.isEmpty()) return null
        val allMountains = loadMountains()

        // 포인트가 많으면 샘플링하여 성능 최적화
        val sampledPoints = if (points.size > 100) {
            points.filterIndexed { i, _ -> i % 10 == 0 } + listOf(points.last())
        } else points

        var closestMountain: Mountain? = null
        var closestDistance = Double.MAX_VALUE

        for (mountain in allMountains) {
            for (point in sampledPoints) {
                val distance = LocationUtils.distanceInMeters(
                    point.latitude, point.longitude, mountain.lat, mountain.lng
                )
                if (distance <= 100.0 && distance < closestDistance) {
                    closestMountain = mountain
                    closestDistance = distance
                }
            }
        }
        return closestMountain
    }
}
