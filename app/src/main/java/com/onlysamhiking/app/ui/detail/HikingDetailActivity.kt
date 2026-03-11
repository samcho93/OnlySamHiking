package com.onlysamhiking.app.ui.detail

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.widget.FrameLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.onlysamhiking.app.R
import com.onlysamhiking.app.data.model.HikingPhoto
import com.onlysamhiking.app.data.model.HikingRecord
import com.onlysamhiking.app.data.model.TrackPoint
import com.onlysamhiking.app.data.repository.HikingRepository
import com.onlysamhiking.app.databinding.ActivityHikingDetailBinding
import com.onlysamhiking.app.databinding.ItemHikingPhotoBinding
import com.onlysamhiking.app.ui.map.NaverMapManager
import com.onlysamhiking.app.ui.photo.PhotoViewerActivity
import com.onlysamhiking.app.util.ExifHelper
import com.onlysamhiking.app.util.GpxExporter
import com.onlysamhiking.app.util.LocationUtils
import com.onlysamhiking.app.util.WatermarkHelper
import kotlinx.coroutines.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class HikingDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHikingDetailBinding
    private lateinit var repository: HikingRepository
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private var record: HikingRecord? = null
    private var recordId: Long = -1L
    private var trackPoints: List<TrackPoint> = emptyList()
    private var photos: MutableList<HikingPhoto> = mutableListOf()
    private var mapManager: NaverMapManager? = null

    private val photoViewerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Watermark was added - reload photos
            reloadPhotos()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHikingDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = HikingRepository(this)

        binding.toolbar.setNavigationOnClickListener { finish() }

        recordId = intent.getLongExtra(EXTRA_RECORD_ID, -1L)
        if (recordId == -1L) {
            finish()
            return
        }

        loadData(recordId)
        setupButtons()
    }

    private fun loadData(recordId: Long) {
        scope.launch {
            record = withContext(Dispatchers.IO) { repository.getRecordById(recordId) }
            trackPoints = withContext(Dispatchers.IO) { repository.getTrackPoints(recordId) }
            val photoList = withContext(Dispatchers.IO) { repository.getPhotos(recordId) }

            // EXIF GPS 좌표로 (0,0) 사진 위치 보정
            val correctedPhotos = withContext(Dispatchers.IO) {
                ExifHelper.fillMissingCoordinates(photoList)
            }
            photos = correctedPhotos.toMutableList()

            record?.let { rec ->
                displayRecord(rec)
                displayMap(trackPoints)
                displayAltitudeChart(trackPoints, photos)
                displayPhotos(photos)
            } ?: finish()
        }
    }

    private fun reloadPhotos() {
        scope.launch {
            val photoList = withContext(Dispatchers.IO) { repository.getPhotos(recordId) }
            photos = photoList.toMutableList()
            displayPhotos(photos)
        }
    }

    private fun displayRecord(rec: HikingRecord) {
        binding.tvDetailName.text = rec.mountainName.ifEmpty { "산행 기록" }

        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 (EEE) HH:mm", Locale.KOREAN)
        binding.tvDetailDate.text = dateFormat.format(Date(rec.startTime))

        binding.tvStatDistance.text = LocationUtils.formatDistance(rec.distance)
        binding.tvStatTime.text = rec.durationFormatted
        binding.tvStatCalories.text = "${rec.calories} kcal"
        binding.tvStatMaxAlt.text = "${rec.maxAltitude.toInt()} m"
        binding.tvStatElevGain.text = "↑${rec.elevationGain.toInt()} m"
        binding.tvStatElevLoss.text = "↓${rec.elevationLoss.toInt()} m"
        binding.tvStatAvgSpeed.text = String.format("%.1f km/h", rec.avgSpeed)

        binding.toolbar.title = rec.mountainName.ifEmpty { "산행 상세" }

        // 타이틀 길게 누르면 수정 가능
        binding.tvDetailName.setOnLongClickListener {
            showEditTitleDialog()
            true
        }

        // Memo
        binding.etMemo.setText(rec.memo)
        binding.btnSaveMemo.setOnClickListener {
            saveMemo()
        }
    }

    private fun showEditTitleDialog() {
        val rec = record ?: return
        val editText = EditText(this).apply {
            setText(rec.mountainName)
            hint = "산행 이름을 입력하세요"
            setPadding(60, 40, 60, 40)
            setSelection(text.length)
        }

        AlertDialog.Builder(this)
            .setTitle("산행 이름 수정")
            .setView(editText)
            .setPositiveButton("저장") { _, _ ->
                val newTitle = editText.text.toString().trim()
                if (newTitle.isNotEmpty()) {
                    updateTitle(newTitle)
                }
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .show()
    }

    private fun updateTitle(newTitle: String) {
        val rec = record ?: return
        val updatedRecord = rec.copy(mountainName = newTitle)

        scope.launch {
            withContext(Dispatchers.IO) {
                repository.updateRecord(updatedRecord)
            }
            record = updatedRecord
            binding.tvDetailName.text = newTitle
            binding.toolbar.title = newTitle
            Toast.makeText(this@HikingDetailActivity, "이름이 수정되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveMemo() {
        val rec = record ?: return
        val memo = binding.etMemo.text.toString()
        val updatedRecord = rec.copy(memo = memo)

        scope.launch {
            withContext(Dispatchers.IO) {
                repository.updateRecord(updatedRecord)
            }
            record = updatedRecord
            Toast.makeText(this@HikingDetailActivity, "메모가 저장되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayMap(points: List<TrackPoint>) {
        if (points.isEmpty()) return

        mapManager = NaverMapManager()
        mapManager?.initialize(binding.detailMapContainer) {
            mapManager?.drawRoute(points)
            // Add camera markers for photo locations
            if (photos.isNotEmpty()) {
                mapManager?.addPhotoMarkers(photos)
            }
        }
    }

    private fun displayAltitudeChart(points: List<TrackPoint>, photoList: List<HikingPhoto> = emptyList()) {
        if (points.isEmpty()) return

        // 누적 거리 배열 계산
        val distances = FloatArray(points.size)
        for (i in points.indices) {
            if (i > 0) {
                distances[i] = distances[i - 1] + LocationUtils.distanceInMeters(
                    points[i - 1].latitude, points[i - 1].longitude,
                    points[i].latitude, points[i].longitude
                ).toFloat()
            }
        }

        val entries = points.indices.map { i ->
            Entry(distances[i] / 1000f, points[i].altitude.toFloat())
        }

        val dataSet = LineDataSet(entries, "고도 (m)").apply {
            color = Color.parseColor("#2E7D32")
            setDrawFilled(true)
            fillColor = Color.parseColor("#4CAF50")
            fillAlpha = 50
            lineWidth = 2f
            setDrawCircles(false)
            setDrawValues(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        binding.chartAltitude.apply {
            data = LineData(dataSet)
            description.isEnabled = false
            legend.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return String.format("%.1fkm", value)
                    }
                }
                textSize = 10f
            }

            axisLeft.apply {
                setDrawGridLines(true)
                gridColor = Color.parseColor("#E0E0E0")
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return "${value.toInt()}m"
                    }
                }
                textSize = 10f
            }

            axisRight.isEnabled = false
            invalidate()
        }

        // 차트 렌더링 후 사진 썸네일을 오버레이로 배치
        if (photoList.isNotEmpty()) {
            binding.chartAltitude.post {
                addPhotoOverlays(points, distances, photoList)
            }
        }
    }

    /**
     * 차트 위에 사진 썸네일을 ImageView 오버레이로 배치
     */
    private fun addPhotoOverlays(
        points: List<TrackPoint>,
        distances: FloatArray,
        photoList: List<HikingPhoto>
    ) {
        val chart = binding.chartAltitude
        val overlay = binding.chartOverlay
        overlay.removeAllViews()

        val transformer = chart.getTransformer(com.github.mikephil.charting.components.YAxis.AxisDependency.LEFT)
        val viewPort = chart.viewPortHandler

        for (photo in photoList) {
            try {
                var nearestIdx = -1

                if (photo.latitude != 0.0 || photo.longitude != 0.0) {
                    var minDist = Double.MAX_VALUE
                    for (i in points.indices) {
                        val d = LocationUtils.distanceInMeters(
                            photo.latitude, photo.longitude,
                            points[i].latitude, points[i].longitude
                        )
                        if (d < minDist) {
                            minDist = d
                            nearestIdx = i
                        }
                    }
                } else if (photo.timestamp > 0) {
                    var minTimeDiff = Long.MAX_VALUE
                    for (i in points.indices) {
                        val diff = kotlin.math.abs(points[i].timestamp - photo.timestamp)
                        if (diff < minTimeDiff) {
                            minTimeDiff = diff
                            nearestIdx = i
                        }
                    }
                }

                if (nearestIdx < 0) continue

                val xVal = distances[nearestIdx] / 1000f
                val yVal = points[nearestIdx].altitude.toFloat()

                // 차트 좌표 → 픽셀 좌표 변환
                val pts = floatArrayOf(xVal, yVal)
                transformer.pointValuesToPixel(pts)
                val px = pts[0]
                val py = pts[1]

                // 차트 영역 안에 있는지 확인
                if (px < viewPort.contentLeft() || px > viewPort.contentRight()) continue
                if (py < viewPort.contentTop() || py > viewPort.contentBottom()) continue

                // 썸네일 비트맵 생성
                val markerBitmap = createChartThumbnailBitmap(photo.filePath)
                val markerSize = markerBitmap?.width ?: 40

                val imageView = android.widget.ImageView(this)
                if (markerBitmap != null) {
                    imageView.setImageBitmap(markerBitmap)
                } else {
                    // 대체 마커: 주황 원
                    val fallback = createFallbackMarkerBitmap()
                    imageView.setImageBitmap(fallback)
                }

                val params = FrameLayout.LayoutParams(markerSize, markerSize)
                imageView.layoutParams = params
                imageView.x = px - markerSize / 2f
                imageView.y = py - markerSize - 5f  // 마커를 데이터 포인트 위에 배치
                overlay.addView(imageView)
            } catch (_: Exception) { }
        }
    }

    /**
     * 차트용 원형 사진 썸네일 Bitmap 생성 (dp 기반)
     */
    private fun createChartThumbnailBitmap(filePath: String): Bitmap? {
        return try {
            val file = File(filePath)
            if (!file.exists()) return null

            val opts = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeFile(filePath, opts)
            if (opts.outWidth <= 0 || opts.outHeight <= 0) return null

            val sizeDp = 28
            val density = resources.displayMetrics.density
            val sizePx = (sizeDp * density).toInt()

            val sampleSize = maxOf(opts.outWidth / sizePx, opts.outHeight / sizePx, 1)
            val decodeOpts = BitmapFactory.Options().apply { inSampleSize = sampleSize }
            val thumbnail = BitmapFactory.decodeFile(filePath, decodeOpts) ?: return null

            val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            val border = 3f * density
            val clipPath = android.graphics.Path().apply {
                addCircle(sizePx / 2f, sizePx / 2f, sizePx / 2f - border, android.graphics.Path.Direction.CW)
            }
            canvas.save()
            canvas.clipPath(clipPath)

            val cropSize = (sizePx - border * 2).toInt()
            val scaled = centerCropBitmap(thumbnail, cropSize, cropSize)
            canvas.drawBitmap(scaled, border, border, null)
            canvas.restore()

            // 흰색 테두리
            canvas.drawCircle(sizePx / 2f, sizePx / 2f, sizePx / 2f - border / 2,
                Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.WHITE; style = Paint.Style.STROKE; strokeWidth = border
                })

            // 주황색 외곽 테두리
            canvas.drawCircle(sizePx / 2f, sizePx / 2f, sizePx / 2f - 1f,
                Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.parseColor("#FF5722"); style = Paint.Style.STROKE; strokeWidth = 2f * density
                })

            thumbnail.recycle()
            if (scaled !== thumbnail) scaled.recycle()
            bitmap
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 사진 파일이 없을 때 대체 카메라 마커 Bitmap
     */
    private fun createFallbackMarkerBitmap(): Bitmap {
        val density = resources.displayMetrics.density
        val sizePx = (24 * density).toInt()
        val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        canvas.drawCircle(sizePx / 2f, sizePx / 2f, sizePx / 2f - 1f,
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.parseColor("#FF5722"); style = Paint.Style.FILL
            })
        canvas.drawCircle(sizePx / 2f, sizePx / 2f, sizePx / 2f - 1f,
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.WHITE; style = Paint.Style.STROKE; strokeWidth = 2f * density
            })

        return bitmap
    }

    private fun centerCropBitmap(source: Bitmap, targetW: Int, targetH: Int): Bitmap {
        val sourceW = source.width
        val sourceH = source.height
        val scale = maxOf(targetW.toFloat() / sourceW, targetH.toFloat() / sourceH)
        val scaledW = (sourceW * scale).toInt()
        val scaledH = (sourceH * scale).toInt()
        val scaled = Bitmap.createScaledBitmap(source, scaledW, scaledH, true)
        val x = (scaledW - targetW) / 2
        val y = (scaledH - targetH) / 2
        val cropped = Bitmap.createBitmap(scaled, x, y, targetW, targetH)
        if (scaled !== source && scaled !== cropped) scaled.recycle()
        return cropped
    }

    private fun displayPhotos(photoList: List<HikingPhoto>) {
        if (photoList.isEmpty()) {
            binding.tvPhotosLabel.visibility = View.GONE
            binding.rvPhotos.visibility = View.GONE
            return
        }

        binding.tvPhotosLabel.visibility = View.VISIBLE
        binding.rvPhotos.visibility = View.VISIBLE

        // 워터마크 사진을 맨 앞에 정렬
        val sortedPhotos = photoList.sortedByDescending { photo ->
            photo.filePath.contains("/watermark/") || File(photo.filePath).name.startsWith("WM_")
        }

        binding.rvPhotos.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )
        binding.rvPhotos.adapter = PhotoAdapter(sortedPhotos)
    }

    private fun showDeletePhotoDialog(photo: HikingPhoto) {
        AlertDialog.Builder(this)
            .setTitle("사진 삭제")
            .setMessage("이 사진을 삭제하시겠습니까?")
            .setPositiveButton("삭제") { _, _ ->
                deletePhoto(photo)
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .show()
    }

    private fun deletePhoto(photo: HikingPhoto) {
        scope.launch {
            withContext(Dispatchers.IO) {
                repository.deletePhoto(photo)
                // 파일도 삭제
                try {
                    val file = File(photo.filePath)
                    if (file.exists()) file.delete()
                } catch (e: Exception) {
                    // 파일 삭제 실패는 무시
                }
            }
            Toast.makeText(this@HikingDetailActivity, "사진이 삭제되었습니다", Toast.LENGTH_SHORT).show()
            reloadPhotos()
        }
    }

    private fun setupButtons() {
        binding.btnExportGpx.setOnClickListener {
            exportGpx()
        }

        binding.btnWatermark.setOnClickListener {
            showPhotoSelectionForWatermark()
        }

        binding.btnDeleteRecord.setOnClickListener {
            showDeleteDialog()
        }

        binding.btnFullscreenMap.setOnClickListener {
            val intent = Intent(this, FullscreenMapActivity::class.java).apply {
                putExtra(FullscreenMapActivity.EXTRA_RECORD_ID, recordId)
            }
            startActivity(intent)
        }
    }

    private fun showPhotoSelectionForWatermark() {
        val rec = record ?: return
        // 워터마크가 아닌 원본 사진만 필터링
        val originalPhotos = photos.filter { !it.filePath.contains("/watermark/") && !File(it.filePath).name.startsWith("WM_") }
        if (originalPhotos.isEmpty()) {
            Toast.makeText(this, "사진이 없습니다", Toast.LENGTH_SHORT).show()
            return
        }

        val dialogView = layoutInflater.inflate(R.layout.dialog_photo_select, null)
        val rvPhotos = dialogView.findViewById<RecyclerView>(R.id.rvDialogPhotos)
        rvPhotos.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 3)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setNegativeButton(R.string.dialog_cancel, null)
            .create()

        rvPhotos.adapter = WatermarkSelectAdapter(originalPhotos) { selectedPhoto ->
            dialog.dismiss()
            applyWatermark(selectedPhoto.filePath, rec)
        }

        dialog.show()
    }

    /**
     * 워터마크 사진 선택용 썸네일 그리드 어댑터
     */
    inner class WatermarkSelectAdapter(
        private val items: List<HikingPhoto>,
        private val onSelect: (HikingPhoto) -> Unit
    ) : RecyclerView.Adapter<WatermarkSelectAdapter.ViewHolder>() {

        inner class ViewHolder(val binding: ItemHikingPhotoBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val b = ItemHikingPhotoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ViewHolder(b)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val photo = items[position]
            Glide.with(holder.itemView)
                .load(File(photo.filePath))
                .centerCrop()
                .into(holder.binding.ivPhoto)

            holder.itemView.setOnClickListener {
                onSelect(photo)
            }
        }

        override fun getItemCount() = items.size
    }

    private fun applyWatermark(photoPath: String, rec: HikingRecord) {
        if (!File(photoPath).exists()) {
            Toast.makeText(this, "사진 파일을 찾을 수 없습니다", Toast.LENGTH_SHORT).show()
            return
        }

        scope.launch {
            val watermarkPath = withContext(Dispatchers.IO) {
                WatermarkHelper.createWatermarkedPhotoAndGetPath(
                    this@HikingDetailActivity, photoPath, rec, trackPoints
                )
            }
            if (watermarkPath != null) {
                Toast.makeText(this@HikingDetailActivity, R.string.watermark_save_success, Toast.LENGTH_SHORT).show()
                // Add watermarked photo to DB and move to front
                val watermarkPhoto = HikingPhoto(
                    recordId = recordId,
                    filePath = watermarkPath,
                    latitude = 0.0,
                    longitude = 0.0,
                    altitude = 0.0,
                    timestamp = System.currentTimeMillis()
                )
                withContext(Dispatchers.IO) {
                    repository.insertPhoto(watermarkPhoto)
                }
                reloadPhotos()
            } else {
                Toast.makeText(this@HikingDetailActivity, "워터마크 저장 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_delete_title)
            .setMessage(R.string.dialog_delete_message)
            .setPositiveButton(R.string.dialog_delete) { _, _ ->
                scope.launch {
                    record?.let {
                        withContext(Dispatchers.IO) {
                            repository.deleteRecord(it)
                        }
                        Toast.makeText(this@HikingDetailActivity, "기록이 삭제되었습니다", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .show()
    }

    private fun exportGpx() {
        val rec = record ?: return
        if (trackPoints.isEmpty()) {
            Toast.makeText(this, "경로 데이터가 없습니다", Toast.LENGTH_SHORT).show()
            return
        }

        // 저장 또는 외부앱 연결 선택
        AlertDialog.Builder(this)
            .setTitle(R.string.gpx_export)
            .setItems(arrayOf(
                getString(R.string.gpx_export_save),
                getString(R.string.gpx_export_share)
            )) { _, which ->
                when (which) {
                    0 -> doGpxSave(rec)
                    1 -> doGpxShare(rec)
                }
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .show()
    }

    private fun doGpxSave(rec: HikingRecord) {
        scope.launch {
            val success = withContext(Dispatchers.IO) {
                GpxExporter.exportToGpx(this@HikingDetailActivity, rec, trackPoints)
            }
            if (success) {
                Toast.makeText(this@HikingDetailActivity, R.string.gpx_export_success, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@HikingDetailActivity, R.string.gpx_export_fail, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun doGpxShare(rec: HikingRecord) {
        scope.launch {
            val shareIntent = withContext(Dispatchers.IO) {
                GpxExporter.exportAndGetShareIntent(
                    this@HikingDetailActivity, rec, trackPoints
                )
            }
            if (shareIntent != null) {
                try {
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.gpx_export_choose_app)))
                } catch (e: Exception) {
                    Toast.makeText(this@HikingDetailActivity, R.string.gpx_export_no_app, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@HikingDetailActivity, R.string.gpx_export_fail, Toast.LENGTH_SHORT).show()
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

    // Photo adapter with click for fullscreen view
    inner class PhotoAdapter(private val items: List<HikingPhoto>) :
        RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

        inner class ViewHolder(val binding: ItemHikingPhotoBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val b = ItemHikingPhotoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ViewHolder(b)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val photo = items[position]
            Glide.with(holder.itemView)
                .load(File(photo.filePath))
                .centerCrop()
                .into(holder.binding.ivPhoto)

            // Click to open fullscreen photo viewer
            holder.itemView.setOnClickListener {
                val intent = Intent(this@HikingDetailActivity, PhotoViewerActivity::class.java).apply {
                    putExtra(PhotoViewerActivity.EXTRA_PHOTO_PATH, photo.filePath)
                    putExtra(PhotoViewerActivity.EXTRA_RECORD_ID, recordId)
                }
                photoViewerLauncher.launch(intent)
            }

            // 길게 누르면 사진 삭제
            holder.itemView.setOnLongClickListener {
                showDeletePhotoDialog(photo)
                true
            }
        }

        override fun getItemCount() = items.size
    }

    companion object {
        const val EXTRA_RECORD_ID = "record_id"
    }
}
