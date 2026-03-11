package com.onlysamhiking.app.util

object CalorieCalculator {

    // MET (Metabolic Equivalent of Task) for hiking
    private const val MET_HIKING_GENERAL = 6.0
    private const val MET_HIKING_STEEP = 8.0
    private const val DEFAULT_WEIGHT_KG = 70.0

    fun calculateCalories(
        durationMillis: Long,
        elevationGain: Double,
        distanceMeters: Double,
        weightKg: Double = DEFAULT_WEIGHT_KG
    ): Int {
        val hours = durationMillis / 3600000.0
        if (hours <= 0) return 0

        val slope = if (distanceMeters > 0) elevationGain / distanceMeters else 0.0
        val met = if (slope > 0.1) MET_HIKING_STEEP else MET_HIKING_GENERAL

        return (met * weightKg * hours).toInt()
    }
}
