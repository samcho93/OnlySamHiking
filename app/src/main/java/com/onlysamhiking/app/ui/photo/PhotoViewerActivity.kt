package com.onlysamhiking.app.ui.photo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.bumptech.glide.Glide
import com.onlysamhiking.app.R
import com.onlysamhiking.app.data.model.HikingRecord
import com.onlysamhiking.app.data.model.TrackPoint
import com.onlysamhiking.app.data.repository.HikingRepository
import com.onlysamhiking.app.databinding.ActivityPhotoViewerBinding
import com.onlysamhiking.app.util.WatermarkHelper
import kotlinx.coroutines.*
import java.io.File

class PhotoViewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoViewerBinding
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var currentPhotoPath: String? = null
    private var record: HikingRecord? = null
    private var trackPoints: List<TrackPoint> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentPhotoPath = intent.getStringExtra(EXTRA_PHOTO_PATH)
        val recordId = intent.getLongExtra(EXTRA_RECORD_ID, -1L)

        if (currentPhotoPath == null) {
            finish()
            return
        }

        // 상단 상태바에 가려지지 않도록 인셋 처리
        ViewCompat.setOnApplyWindowInsetsListener(binding.topBar) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                top = insets.top + 8,
                left = view.paddingLeft,
                right = view.paddingRight,
                bottom = view.paddingBottom
            )
            windowInsets
        }

        loadPhoto(currentPhotoPath!!)
        loadRecord(recordId)

        binding.btnBack.setOnClickListener { finish() }

        binding.btnAddWatermark.setOnClickListener {
            addWatermark()
        }
    }

    private fun loadPhoto(path: String) {
        val file = File(path)
        if (file.exists()) {
            Glide.with(this)
                .load(file)
                .into(binding.ivFullPhoto)
        }
    }

    private fun loadRecord(recordId: Long) {
        if (recordId == -1L) return
        val repository = HikingRepository(this)
        scope.launch {
            record = withContext(Dispatchers.IO) { repository.getRecordById(recordId) }
            trackPoints = withContext(Dispatchers.IO) { repository.getTrackPoints(recordId) }
        }
    }

    private fun addWatermark() {
        val rec = record ?: run {
            Toast.makeText(this, "기록 정보를 불러오는 중입니다", Toast.LENGTH_SHORT).show()
            return
        }
        val photoPath = currentPhotoPath ?: return

        scope.launch {
            val success = withContext(Dispatchers.IO) {
                WatermarkHelper.createWatermarkedPhoto(
                    this@PhotoViewerActivity, photoPath, rec, trackPoints
                )
            }
            if (success) {
                Toast.makeText(this@PhotoViewerActivity, R.string.watermark_save_success, Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
            } else {
                Toast.makeText(this@PhotoViewerActivity, "워터마크 저장 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        const val EXTRA_PHOTO_PATH = "photo_path"
        const val EXTRA_RECORD_ID = "record_id"
    }
}
