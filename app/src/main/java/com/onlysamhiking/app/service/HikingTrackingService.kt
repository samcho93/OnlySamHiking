package com.onlysamhiking.app.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import android.speech.tts.TextToSpeech
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.onlysamhiking.app.OnlySamHikingApp
import com.onlysamhiking.app.R
import com.onlysamhiking.app.data.model.HikingRecord
import com.onlysamhiking.app.data.model.Mountain
import com.onlysamhiking.app.data.model.TrackPoint
import com.onlysamhiking.app.data.repository.HikingRepository
import com.onlysamhiking.app.data.repository.MountainRepository
import com.onlysamhiking.app.ui.main.MapActivity
import com.onlysamhiking.app.util.CalorieCalculator
import com.onlysamhiking.app.util.LocationUtils
import kotlinx.coroutines.*
import java.util.Locale

class HikingTrackingService : Service(), TextToSpeech.OnInitListener {

    private val binder = TrackingBinder()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var hikingRepository: HikingRepository
    private lateinit var mountainRepository: MountainRepository
    private var tts: TextToSpeech? = null

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // Recording state
    var isRecording = false
        private set
    var isPaused = false
        private set
    var currentRecordId: Long = -1L
        private set

    // Tracking data
    private val trackPoints = mutableListOf<TrackPoint>()
    private var lastLocation: Location? = null
    private var totalDistance = 0.0
    private var maxAltitude = Double.MIN_VALUE
    private var minAltitude = Double.MAX_VALUE
    private var elevationGain = 0.0
    private var elevationLoss = 0.0
    private var maxSpeed = 0.0
    private var startTime = 0L
    private var pausedDuration = 0L
    private var pauseStartTime = 0L

    // Peak alert tracking
    private val alertedPeaks = mutableSetOf<String>()
    private var ttsReady = false

    // Listeners
    var onLocationUpdate: ((Location, TrackingStats) -> Unit)? = null
    var onPeakNearby: ((Mountain, Double) -> Unit)? = null

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            if (isPaused) return
            result.lastLocation?.let { processLocation(it) }
        }
    }

    inner class TrackingBinder : Binder() {
        fun getService(): HikingTrackingService = this@HikingTrackingService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        hikingRepository = HikingRepository(this)
        mountainRepository = MountainRepository(this)
        tts = TextToSpeech(this, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.KOREAN
            ttsReady = true
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startRecording()
            ACTION_PAUSE -> pauseRecording()
            ACTION_RESUME -> resumeRecording()
            ACTION_STOP -> stopRecording()
        }
        return START_STICKY
    }

    fun startRecording() {
        if (isRecording) return

        isRecording = true
        isPaused = false
        startTime = System.currentTimeMillis()
        totalDistance = 0.0
        maxAltitude = Double.MIN_VALUE
        minAltitude = Double.MAX_VALUE
        elevationGain = 0.0
        elevationLoss = 0.0
        maxSpeed = 0.0
        pausedDuration = 0L
        trackPoints.clear()
        alertedPeaks.clear()
        lastLocation = null

        serviceScope.launch {
            val record = HikingRecord(startTime = startTime)
            currentRecordId = hikingRepository.insertRecord(record)
        }

        startForeground(NOTIFICATION_ID, createNotification("등산 기록 시작"))
        startLocationUpdates()
    }

    fun pauseRecording() {
        if (!isRecording || isPaused) return
        isPaused = true
        pauseStartTime = System.currentTimeMillis()
        updateNotification("일시정지됨")
    }

    fun resumeRecording() {
        if (!isRecording || !isPaused) return
        isPaused = false
        pausedDuration += System.currentTimeMillis() - pauseStartTime
        updateNotification("기록 중")
    }

    fun stopRecording() {
        if (!isRecording) return
        isRecording = false
        isPaused = false

        fusedLocationClient.removeLocationUpdates(locationCallback)

        val endTime = System.currentTimeMillis()
        val activeDuration = endTime - startTime - pausedDuration

        serviceScope.launch {
            // Save remaining points
            if (trackPoints.isNotEmpty()) {
                hikingRepository.insertTrackPoints(trackPoints.toList())
            }

            // 경로 반경 100m 이내의 산을 검색, 없으면 날짜로 표시
            val allPoints = hikingRepository.getTrackPoints(currentRecordId)
            val firstPoint = allPoints.firstOrNull()
            val lastPoint = allPoints.lastOrNull()
            val mountain = mountainRepository.findMountainAlongRoute(allPoints)
            val mountainName = if (mountain != null) {
                mountain.name
            } else {
                val dateFormat = java.text.SimpleDateFormat("yyyy.MM.dd", java.util.Locale.getDefault())
                dateFormat.format(java.util.Date(startTime)) + " 산행"
            }

            val avgSpeed = if (activeDuration > 0) {
                (totalDistance / 1000.0) / (activeDuration / 3600000.0)
            } else 0.0

            val calories = CalorieCalculator.calculateCalories(
                activeDuration, elevationGain, totalDistance
            )

            val record = HikingRecord(
                id = currentRecordId,
                mountainName = mountainName,
                startTime = startTime,
                endTime = endTime,
                distance = totalDistance,
                maxAltitude = if (maxAltitude == Double.MIN_VALUE) 0.0 else maxAltitude,
                minAltitude = if (minAltitude == Double.MAX_VALUE) 0.0 else minAltitude,
                elevationGain = elevationGain,
                elevationLoss = elevationLoss,
                avgSpeed = avgSpeed,
                maxSpeed = maxSpeed,
                calories = calories,
                startLat = firstPoint?.latitude ?: 0.0,
                startLng = firstPoint?.longitude ?: 0.0,
                endLat = lastPoint?.latitude ?: 0.0,
                endLng = lastPoint?.longitude ?: 0.0
            )
            hikingRepository.updateRecord(record)
            trackPoints.clear()
        }

        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    @Suppress("MissingPermission")
    private fun startLocationUpdates() {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3000L)
            .setMinUpdateIntervalMillis(1000L)
            .setMinUpdateDistanceMeters(2f)
            .build()

        fusedLocationClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
    }

    private fun processLocation(location: Location) {
        if (!isRecording || isPaused) return

        val point = TrackPoint(
            recordId = currentRecordId,
            latitude = location.latitude,
            longitude = location.longitude,
            altitude = location.altitude,
            speed = location.speed,
            accuracy = location.accuracy,
            timestamp = System.currentTimeMillis()
        )
        trackPoints.add(point)

        // Calculate distance
        lastLocation?.let { prev ->
            if (location.accuracy < 30) { // Only use accurate readings
                val dist = prev.distanceTo(location).toDouble()
                if (dist < 100) { // Filter out GPS jumps
                    totalDistance += dist
                }

                // Altitude tracking
                val altDiff = location.altitude - prev.altitude
                if (kotlin.math.abs(altDiff) < 50) { // Filter altitude spikes
                    if (altDiff > 0) elevationGain += altDiff
                    else elevationLoss += kotlin.math.abs(altDiff)
                }
            }
        }

        // Update altitude extremes
        if (location.altitude > 0) {
            if (location.altitude > maxAltitude) maxAltitude = location.altitude
            if (location.altitude < minAltitude) minAltitude = location.altitude
        }

        // Speed tracking
        val speedKmh = location.speed * 3.6
        if (speedKmh > maxSpeed && speedKmh < 30) maxSpeed = speedKmh

        lastLocation = location

        // Save batch of points
        if (trackPoints.size >= 10) {
            val pointsToSave = trackPoints.toList()
            trackPoints.clear()
            serviceScope.launch {
                hikingRepository.insertTrackPoints(pointsToSave)
            }
        }

        // Check nearby peaks
        checkNearbyPeaks(location)

        // Update notification
        val elapsed = System.currentTimeMillis() - startTime - pausedDuration
        val distStr = LocationUtils.formatDistance(totalDistance)
        val timeStr = LocationUtils.formatDuration(elapsed)
        updateNotification("$distStr | $timeStr")

        // Notify listeners
        val stats = getTrackingStats()
        onLocationUpdate?.invoke(location, stats)
    }

    private fun checkNearbyPeaks(location: Location) {
        val nearbyPeaks = mountainRepository.findNearbyPeaks(
            location.latitude, location.longitude, 50.0
        )
        for ((peak, distance) in nearbyPeaks) {
            if (peak.name !in alertedPeaks) {
                alertedPeaks.add(peak.name)
                onPeakNearby?.invoke(peak, distance)
                speakPeakAlert(peak)
            }
        }
    }

    private fun speakPeakAlert(mountain: Mountain) {
        if (!ttsReady) return
        val text = getString(R.string.tts_peak_nearby, mountain.name, mountain.alt)
        tts?.speak(text, TextToSpeech.QUEUE_ADD, null, "peak_${mountain.name}")
    }

    fun getTrackingStats(): TrackingStats {
        val elapsed = if (isRecording) {
            val now = System.currentTimeMillis()
            val paused = if (isPaused) now - pauseStartTime else 0L
            now - startTime - pausedDuration - paused
        } else 0L

        val avgSpeed = if (elapsed > 0) {
            (totalDistance / 1000.0) / (elapsed / 3600000.0)
        } else 0.0

        return TrackingStats(
            distance = totalDistance,
            duration = elapsed,
            altitude = lastLocation?.altitude ?: 0.0,
            maxAltitude = if (maxAltitude == Double.MIN_VALUE) 0.0 else maxAltitude,
            minAltitude = if (minAltitude == Double.MAX_VALUE) 0.0 else minAltitude,
            elevationGain = elevationGain,
            elevationLoss = elevationLoss,
            speed = (lastLocation?.speed?.times(3.6)) ?: 0.0,
            avgSpeed = avgSpeed,
            maxSpeed = maxSpeed,
            calories = CalorieCalculator.calculateCalories(elapsed, elevationGain, totalDistance),
            accuracy = lastLocation?.accuracy ?: 0f
        )
    }

    private fun createNotification(content: String): Notification {
        val intent = Intent(this, MapActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, OnlySamHikingApp.CHANNEL_TRACKING)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_hiking)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    private fun updateNotification(content: String) {
        val notification = createNotification(content)
        val manager = getSystemService(NOTIFICATION_SERVICE) as android.app.NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        tts?.stop()
        tts?.shutdown()
    }

    companion object {
        const val ACTION_START = "com.onlysamhiking.ACTION_START"
        const val ACTION_PAUSE = "com.onlysamhiking.ACTION_PAUSE"
        const val ACTION_RESUME = "com.onlysamhiking.ACTION_RESUME"
        const val ACTION_STOP = "com.onlysamhiking.ACTION_STOP"
        const val NOTIFICATION_ID = 1001
    }
}

data class TrackingStats(
    val distance: Double = 0.0,
    val duration: Long = 0L,
    val altitude: Double = 0.0,
    val maxAltitude: Double = 0.0,
    val minAltitude: Double = 0.0,
    val elevationGain: Double = 0.0,
    val elevationLoss: Double = 0.0,
    val speed: Double = 0.0,
    val avgSpeed: Double = 0.0,
    val maxSpeed: Double = 0.0,
    val calories: Int = 0,
    val accuracy: Float = 0f
)
