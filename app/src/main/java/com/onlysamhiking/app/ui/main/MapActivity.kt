package com.onlysamhiking.app.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.onlysamhiking.app.R
import com.onlysamhiking.app.data.model.HikingPhoto
import com.onlysamhiking.app.data.model.MapProvider
import com.onlysamhiking.app.data.repository.HikingRepository
import com.onlysamhiking.app.databinding.ActivityMapBinding
import com.onlysamhiking.app.service.HikingTrackingService
import com.onlysamhiking.app.service.TrackingStats
import com.onlysamhiking.app.ui.history.HistoryActivity
import com.onlysamhiking.app.ui.map.GoogleMapManager
import com.onlysamhiking.app.ui.map.MapManagerInterface
import com.onlysamhiking.app.ui.map.NaverMapManager
import com.onlysamhiking.app.ui.mountain.MountainMapActivity
import com.onlysamhiking.app.util.LocationUtils
import com.onlysamhiking.app.util.PermissionHelper
import kotlinx.coroutines.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    private var mapManager: MapManagerInterface? = null
    private var currentMapProvider = MapProvider.NAVER
    private var trackingService: HikingTrackingService? = null
    private var serviceBound = false
    private lateinit var hikingRepository: HikingRepository

    private val activityScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val timerHandler = Handler(Looper.getMainLooper())
    private var currentPhotoUri: Uri? = null
    private var currentPhotoPath: String? = null
    private var lastKnownLat = 0.0
    private var lastKnownLng = 0.0
    private var isHeadingModeOn = false
    private val recordingPhotos = mutableListOf<HikingPhoto>()

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && currentPhotoPath != null) {
            savePhotoRecord()
        }
    }

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) takePhoto()
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val serviceBinder = binder as HikingTrackingService.TrackingBinder
            trackingService = serviceBinder.getService()
            serviceBound = true

            trackingService?.onLocationUpdate = { location, stats ->
                runOnUiThread {
                    updateStats(stats)
                    mapManager?.addRoutePoint(location.latitude, location.longitude)
                    mapManager?.moveCameraToLocation(location)

                    // Update SAM marker position
                    mapManager?.updateSamMarker(location.latitude, location.longitude)

                    lastKnownLat = location.latitude
                    lastKnownLng = location.longitude

                    val accuracy = location.accuracy.toInt()
                    binding.tvGpsStatus.text = getString(R.string.gps_accuracy, accuracy)
                }
            }

            trackingService?.onPeakNearby = { mountain, distance ->
                runOnUiThread {
                    Toast.makeText(
                        this@MapActivity,
                        "${mountain.name} (${mountain.alt}m) - ${distance.toInt()}m",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            // Restore UI state if already recording
            if (trackingService?.isRecording == true) {
                setRecordingUI(true)
                startTimer()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            trackingService = null
            serviceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hikingRepository = HikingRepository(this)
        initializeMap()
        setupListeners()
    }

    private fun initializeMap() {
        mapManager = when (currentMapProvider) {
            MapProvider.NAVER -> NaverMapManager()
            MapProvider.GOOGLE -> GoogleMapManager()
        }

        mapManager?.initialize(binding.mapContainer) {
            if (PermissionHelper.hasLocationPermission(this)) {
                mapManager?.setMyLocationEnabled(true)
                // 대기 화면에서도 나침반 모드 적용 (지도 전환 시 상태 복원)
                if (isHeadingModeOn) {
                    mapManager?.setHeadingMode(true)
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnStartStop.setOnClickListener {
            if (trackingService?.isRecording == true) {
                showStopDialog()
            } else {
                startRecording()
            }
        }

        binding.btnPause.setOnClickListener {
            trackingService?.let { service ->
                if (service.isPaused) {
                    service.resumeRecording()
                    binding.btnPause.setImageResource(R.drawable.ic_pause)
                } else {
                    service.pauseRecording()
                    binding.btnPause.setImageResource(R.drawable.ic_play)
                }
            }
        }

        binding.btnPhoto.setOnClickListener {
            takePhoto()
        }

        binding.fabSwitchMap.setOnClickListener {
            switchMap()
        }

        binding.fabCompass.setOnClickListener {
            toggleHeadingMode()
        }

        binding.fabHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        binding.fabMountainMap.setOnClickListener {
            startActivity(Intent(this, MountainMapActivity::class.java))
        }
    }

    private fun toggleHeadingMode() {
        isHeadingModeOn = !isHeadingModeOn
        mapManager?.setHeadingMode(isHeadingModeOn)

        if (isHeadingModeOn) {
            binding.fabCompass.backgroundTintList =
                android.content.res.ColorStateList.valueOf(getColor(R.color.accent))
            Toast.makeText(this, R.string.heading_mode_on, Toast.LENGTH_SHORT).show()
        } else {
            binding.fabCompass.backgroundTintList =
                android.content.res.ColorStateList.valueOf(getColor(R.color.primary))
            Toast.makeText(this, R.string.heading_mode_off, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startRecording() {
        if (!PermissionHelper.hasNotificationPermission(this)) {
            val perms = PermissionHelper.getNotificationPermission()
            if (perms.isNotEmpty()) {
                registerForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { doStartRecording() }.launch(perms)
                return
            }
        }
        doStartRecording()
    }

    private fun doStartRecording() {
        mapManager?.clearRoute()

        val serviceIntent = Intent(this, HikingTrackingService::class.java).apply {
            action = HikingTrackingService.ACTION_START
        }
        startForegroundService(serviceIntent)
        bindService(
            Intent(this, HikingTrackingService::class.java),
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )

        setRecordingUI(true)
        startTimer()
    }

    private fun showStopDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_stop_title)
            .setItems(arrayOf(
                getString(R.string.dialog_save),
                getString(R.string.dialog_discard),
                getString(R.string.dialog_cancel)
            )) { _, which ->
                when (which) {
                    0 -> stopRecording(save = true)
                    1 -> stopRecording(save = false)
                    2 -> { /* cancel */ }
                }
            }
            .show()
    }

    private fun stopRecording(save: Boolean = true) {
        stopTimer()

        if (save) {
            trackingService?.stopRecording()
            Toast.makeText(this, R.string.record_saved, Toast.LENGTH_SHORT).show()
        } else {
            val recordId = trackingService?.currentRecordId ?: -1L
            trackingService?.stopRecording()
            if (recordId > 0) {
                activityScope.launch {
                    withContext(Dispatchers.IO) {
                        hikingRepository.deleteRecordById(recordId)
                    }
                }
            }
            Toast.makeText(this, R.string.record_discarded, Toast.LENGTH_SHORT).show()
        }

        if (serviceBound) {
            unbindService(serviceConnection)
            serviceBound = false
        }

        mapManager?.removeSamMarker()
        recordingPhotos.clear()
        setRecordingUI(false)
    }

    private fun setRecordingUI(recording: Boolean) {
        if (recording) {
            binding.btnStartStop.apply {
                background = getDrawable(R.drawable.bg_stop_button)
                setImageResource(R.drawable.ic_stop)
                contentDescription = getString(R.string.btn_stop)
            }
            binding.statsRow1.visibility = View.VISIBLE
            binding.statsRow2.visibility = View.VISIBLE
            binding.btnPhoto.visibility = View.VISIBLE
            binding.btnPause.visibility = View.VISIBLE
        } else {
            binding.btnStartStop.apply {
                background = getDrawable(R.drawable.bg_start_button)
                setImageResource(R.drawable.ic_play)
                contentDescription = getString(R.string.btn_start)
            }
            binding.statsRow1.visibility = View.GONE
            binding.statsRow2.visibility = View.GONE
            binding.btnPhoto.visibility = View.GONE
            binding.btnPause.visibility = View.GONE
        }
    }

    private fun updateStats(stats: TrackingStats) {
        binding.tvDistance.text = LocationUtils.formatDistance(stats.distance)
        binding.tvTime.text = LocationUtils.formatDuration(stats.duration)
        binding.tvAltitude.text = String.format("%d m", stats.altitude.toInt())
        binding.tvSpeed.text = String.format("%.1f km/h", stats.speed)
        binding.tvElevationGain.text = String.format("↑%d m", stats.elevationGain.toInt())
        binding.tvElevationLoss.text = String.format("↓%d m", stats.elevationLoss.toInt())
        binding.tvCalories.text = String.format("%d kcal", stats.calories)
    }

    private val timerRunnable = object : Runnable {
        override fun run() {
            trackingService?.let { service ->
                if (service.isRecording) {
                    val stats = service.getTrackingStats()
                    updateStats(stats)
                }
            }
            timerHandler.postDelayed(this, 1000)
        }
    }

    private fun startTimer() {
        timerHandler.post(timerRunnable)
    }

    private fun stopTimer() {
        timerHandler.removeCallbacks(timerRunnable)
    }

    private fun takePhoto() {
        if (!PermissionHelper.hasCameraPermission(this)) {
            cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            return
        }

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val photoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            ?: filesDir
        val photoFile = File(photoDir, "HIKE_${timeStamp}.jpg")
        currentPhotoPath = photoFile.absolutePath

        currentPhotoUri = FileProvider.getUriForFile(
            this,
            "${packageName}.fileprovider",
            photoFile
        )

        cameraLauncher.launch(currentPhotoUri)
    }

    private fun savePhotoRecord() {
        val service = trackingService ?: return
        if (!service.isRecording) return

        val photo = HikingPhoto(
            recordId = service.currentRecordId,
            filePath = currentPhotoPath ?: return,
            latitude = lastKnownLat,
            longitude = lastKnownLng,
            altitude = service.getTrackingStats().altitude,
            timestamp = System.currentTimeMillis()
        )

        activityScope.launch {
            withContext(Dispatchers.IO) {
                hikingRepository.insertPhoto(photo)
            }
            Toast.makeText(this@MapActivity, R.string.photo_saved, Toast.LENGTH_SHORT).show()
        }

        // 실시간 지도에 카메라 마커 표시
        recordingPhotos.add(photo)
        mapManager?.addPhotoMarkers(recordingPhotos)

        saveToGallery(currentPhotoPath!!)
    }

    private fun saveToGallery(photoPath: String) {
        val values = android.content.ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, File(photoPath).name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/OnlySamHiking")
        }

        try {
            val uri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
            )
            uri?.let { destUri ->
                contentResolver.openOutputStream(destUri)?.use { output ->
                    File(photoPath).inputStream().use { input ->
                        input.copyTo(output)
                    }
                }
            }
        } catch (e: Exception) {
            // Gallery save is best-effort
        }
    }

    private fun switchMap() {
        mapManager?.onDestroy()
        binding.mapContainer.removeAllViews()

        currentMapProvider = when (currentMapProvider) {
            MapProvider.NAVER -> MapProvider.GOOGLE
            MapProvider.GOOGLE -> MapProvider.NAVER
        }

        initializeMap()
        val mapName = when (currentMapProvider) {
            MapProvider.NAVER -> getString(R.string.map_naver)
            MapProvider.GOOGLE -> getString(R.string.map_google)
        }
        Toast.makeText(this, mapName, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        mapManager?.onResume()

        if (!serviceBound) {
            val intent = Intent(this, HikingTrackingService::class.java)
            bindService(intent, serviceConnection, 0)
        }
    }

    override fun onPause() {
        super.onPause()
        mapManager?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
        activityScope.cancel()
        if (serviceBound) {
            unbindService(serviceConnection)
            serviceBound = false
        }
        mapManager?.onDestroy()
    }
}
