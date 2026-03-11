package com.onlysamhiking.app.ui.detail

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.onlysamhiking.app.data.model.HikingPhoto
import com.onlysamhiking.app.data.model.TrackPoint
import com.onlysamhiking.app.data.repository.HikingRepository
import com.onlysamhiking.app.databinding.ActivityFullscreenMapBinding
import com.onlysamhiking.app.ui.map.NaverMapManager
import com.onlysamhiking.app.util.ExifHelper
import kotlinx.coroutines.*

class FullscreenMapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenMapBinding
    private var mapManager: NaverMapManager? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullscreenMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        // 상단 상태바에 가려지지 않도록 인셋 처리
        ViewCompat.setOnApplyWindowInsetsListener(binding.btnBack) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top + 16
                leftMargin = insets.left + 16
            }
            windowInsets
        }

        val recordId = intent.getLongExtra(EXTRA_RECORD_ID, -1L)
        if (recordId == -1L) {
            finish()
            return
        }

        loadAndDisplayMap(recordId)
    }

    private fun loadAndDisplayMap(recordId: Long) {
        val repository = HikingRepository(this)

        scope.launch {
            val trackPoints = withContext(Dispatchers.IO) { repository.getTrackPoints(recordId) }
            val photos = withContext(Dispatchers.IO) { repository.getPhotos(recordId) }

            if (trackPoints.isEmpty()) {
                finish()
                return@launch
            }

            // EXIF GPS 좌표로 (0,0) 사진 위치 보정
            val correctedPhotos = withContext(Dispatchers.IO) {
                ExifHelper.fillMissingCoordinates(photos)
            }

            mapManager = NaverMapManager()
            mapManager?.initialize(binding.fullscreenMapContainer) {
                mapManager?.drawRoute(trackPoints)
                if (correctedPhotos.isNotEmpty()) {
                    mapManager?.addPhotoMarkers(correctedPhotos)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapManager?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapManager?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        mapManager?.onDestroy()
    }

    companion object {
        const val EXTRA_RECORD_ID = "record_id"
    }
}
