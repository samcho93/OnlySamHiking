package com.onlysamhiking.app.ui.`import`

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
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
import com.onlysamhiking.app.databinding.ActivityImportGpxBinding
import com.onlysamhiking.app.databinding.ItemHikingPhotoBinding
import com.onlysamhiking.app.ui.map.NaverMapManager
import com.onlysamhiking.app.util.ExifHelper
import com.onlysamhiking.app.util.GpxImporter
import com.onlysamhiking.app.util.LocationUtils
import kotlinx.coroutines.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ImportGpxActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImportGpxBinding
    private lateinit var repository: HikingRepository
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private var gpxData: GpxImporter.GpxData? = null
    private var parsedRecord: HikingRecord? = null
    private var parsedTrackPoints: List<TrackPoint> = emptyList()
    private val importedPhotoPaths = mutableListOf<String>()
    private var mapManager: NaverMapManager? = null

    // 자동 검색된 사진 데이터
    data class MatchedPhoto(
        val uri: Uri,
        val path: String,
        val latitude: Double,
        val longitude: Double,
        val dateTaken: Long,
        var selected: Boolean = true
    )
    private val matchedPhotos = mutableListOf<MatchedPhoto>()

    private val gpxFilePickerIntent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                val fileName = getFileNameFromUri(uri) ?: ""
                if (fileName.lowercase().endsWith(".gpx")) {
                    onGpxFileSelected(uri)
                } else {
                    Toast.makeText(this, "GPX 파일(.gpx)만 선택할 수 있습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val photoPicker = registerForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        uris?.let { onPhotosSelected(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImportGpxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = HikingRepository(this)

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnSelectGpx.setOnClickListener {
            // GPX 파일 전용 Intent (확장자 필터링)
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(
                    "application/gpx+xml",
                    "application/xml",
                    "text/xml",
                    "application/octet-stream"
                ))
            }
            gpxFilePickerIntent.launch(intent)
        }

        binding.btnAddPhotos.setOnClickListener {
            photoPicker.launch(arrayOf("image/*"))
        }

        binding.btnSaveImport.setOnClickListener {
            saveImportedRecord()
        }
    }

    private fun onGpxFileSelected(uri: Uri) {
        scope.launch {
            val data = withContext(Dispatchers.IO) {
                GpxImporter.parseGpxFromUri(this@ImportGpxActivity, uri)
            }

            if (data == null || data.trackPoints.isEmpty()) {
                Toast.makeText(this@ImportGpxActivity, R.string.import_parse_fail, Toast.LENGTH_SHORT).show()
                return@launch
            }

            gpxData = data

            // 파일 이름 표시
            val fileName = getFileNameFromUri(uri) ?: "GPX 파일"
            binding.tvGpxFileName.text = fileName
            binding.tvGpxFileName.visibility = View.VISIBLE

            // Record 데이터 변환
            val (record, trackPoints) = GpxImporter.convertToRecordData(data)
            parsedRecord = record
            parsedTrackPoints = trackPoints

            // UI 표시
            displayParsedInfo(record, trackPoints)

            // GPX 날짜 범위에 해당하는 사진 자동 검색
            searchMatchingPhotos(trackPoints)
        }
    }

    private fun displayParsedInfo(record: HikingRecord, trackPoints: List<TrackPoint>) {
        binding.cardInfo.visibility = View.VISIBLE
        binding.cardPhotos.visibility = View.VISIBLE
        binding.cardMemo.visibility = View.VISIBLE
        binding.btnSaveImport.visibility = View.VISIBLE

        binding.etRecordName.setText(record.mountainName)
        binding.tvImportDistance.text = LocationUtils.formatDistance(record.distance)
        binding.tvImportTime.text = record.durationFormatted
        binding.tvImportElevGain.text = "↑${record.elevationGain.toInt()}m"

        // 지도에 경로 표시
        displayMap(trackPoints)

        // 고도 그래프
        displayAltitudeChart(trackPoints)
    }

    private fun displayMap(points: List<TrackPoint>) {
        if (points.isEmpty()) return

        mapManager = NaverMapManager()
        mapManager?.initialize(binding.importMapContainer) {
            mapManager?.drawRoute(points)
        }
    }

    private fun displayAltitudeChart(points: List<TrackPoint>) {
        if (points.isEmpty()) return

        val entries = mutableListOf<Entry>()
        var cumulativeDistance = 0f

        for (i in points.indices) {
            if (i > 0) {
                cumulativeDistance += LocationUtils.distanceInMeters(
                    points[i - 1].latitude, points[i - 1].longitude,
                    points[i].latitude, points[i].longitude
                ).toFloat()
            }
            entries.add(Entry(cumulativeDistance / 1000f, points[i].altitude.toFloat()))
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

        binding.chartImportAltitude.apply {
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
    }

    /**
     * 사진 접근 권한 확인 후 검색 시작
     */
    private fun searchMatchingPhotos(trackPoints: List<TrackPoint>) {
        if (trackPoints.isEmpty()) return

        // 런타임 권한 확인
        val neededPermissions = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {
                neededPermissions.add(Manifest.permission.READ_MEDIA_IMAGES)
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                neededPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_MEDIA_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            neededPermissions.add(Manifest.permission.ACCESS_MEDIA_LOCATION)
        }

        if (neededPermissions.isNotEmpty()) {
            android.util.Log.d("ImportGpx", "Requesting permissions: $neededPermissions")
            pendingTrackPoints = trackPoints
            permissionLauncher.launch(neededPermissions.toTypedArray())
        } else {
            doSearchMatchingPhotos(trackPoints)
        }
    }

    private var pendingTrackPoints: List<TrackPoint>? = null

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        android.util.Log.d("ImportGpx", "Permission results: $permissions")
        val allGranted = permissions.values.all { it }
        val trackPoints = pendingTrackPoints ?: return@registerForActivityResult
        if (allGranted || permissions.any { it.value }) {
            // 최소한 일부 권한 승인 시 검색 진행
            doSearchMatchingPhotos(trackPoints)
        } else {
            binding.tvAutoPhotoStatus.text = "사진 접근 권한이 필요합니다"
            binding.tvAutoPhotoStatus.visibility = View.VISIBLE
        }
        pendingTrackPoints = null
    }

    private fun doSearchMatchingPhotos(trackPoints: List<TrackPoint>) {
        binding.pbPhotoSearch.visibility = View.VISIBLE
        binding.tvAutoPhotoStatus.text = "사진 검색 중..."
        binding.tvAutoPhotoStatus.visibility = View.VISIBLE

        scope.launch {
            val photos = withContext(Dispatchers.IO) {
                findMatchingPhotosFromGallery(trackPoints)
            }

            binding.pbPhotoSearch.visibility = View.GONE

            matchedPhotos.clear()
            matchedPhotos.addAll(photos)

            if (photos.isEmpty()) {
                binding.tvAutoPhotoStatus.text = "경로 근처에서 촬영한 사진을 찾지 못했습니다"
            } else {
                binding.tvAutoPhotoStatus.text = "경로 근처 사진 ${photos.size}장 발견 (탭하여 선택/해제)"
                displayMatchedPhotos()
            }
        }
    }

    /**
     * MediaStore에서 GPX 날짜 범위의 사진을 조회하고 위치 매칭
     */
    private fun findMatchingPhotosFromGallery(trackPoints: List<TrackPoint>): List<MatchedPhoto> {
        val result = mutableListOf<MatchedPhoto>()

        // GPX 시간 범위 (전후 1시간 여유)
        val gpxMinTime = trackPoints.minOf { it.timestamp }
        val gpxMaxTime = trackPoints.maxOf { it.timestamp }

        android.util.Log.d("ImportGpx", "GPX time range: $gpxMinTime ~ $gpxMaxTime")
        android.util.Log.d("ImportGpx", "GPX time range: ${Date(gpxMinTime)} ~ ${Date(gpxMaxTime)}")
        android.util.Log.d("ImportGpx", "GPX track points: ${trackPoints.size}")

        // GPX에 시간 데이터가 없으면 날짜 기반으로 검색 불가 → 위치만으로 검색
        val hasTimeData = gpxMinTime > 0 && gpxMaxTime > 0

        val startTime: Long
        val endTime: Long

        if (hasTimeData) {
            startTime = gpxMinTime - 3600_000L  // 1시간 전
            endTime = gpxMaxTime + 3600_000L    // 1시간 후
        } else {
            // 시간 데이터 없으면 최근 1년 사진에서 위치로만 검색
            endTime = System.currentTimeMillis()
            startTime = endTime - 365L * 24 * 3600 * 1000
            android.util.Log.d("ImportGpx", "No time data in GPX, searching last 1 year by location only")
        }

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.DATE_ADDED
        )

        // DATE_TAKEN 또는 DATE_ADDED로 검색 (DATE_TAKEN은 ms, DATE_ADDED는 초 단위)
        val selection = "(${MediaStore.Images.Media.DATE_TAKEN} BETWEEN ? AND ?) OR " +
                "(${MediaStore.Images.Media.DATE_ADDED} BETWEEN ? AND ?)"
        val selectionArgs = arrayOf(
            startTime.toString(), endTime.toString(),
            (startTime / 1000).toString(), (endTime / 1000).toString()
        )
        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} ASC"

        var cursor: Cursor? = null
        try {
            cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )

            android.util.Log.d("ImportGpx", "MediaStore query result: ${cursor?.count ?: 0} photos found in date range")

            cursor?.let {
                val idCol = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val pathCol = it.getColumnIndex(MediaStore.Images.Media.DATA)
                val dateCol = it.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)
                val dateAddedCol = it.getColumnIndex(MediaStore.Images.Media.DATE_ADDED)

                while (it.moveToNext()) {
                    val id = it.getLong(idCol)
                    val path = if (pathCol >= 0) it.getString(pathCol) ?: "" else ""
                    var dateTaken = if (dateCol >= 0) it.getLong(dateCol) else 0L
                    val dateAdded = if (dateAddedCol >= 0) it.getLong(dateAddedCol) * 1000 else 0L // 초→ms
                    if (dateTaken == 0L) dateTaken = dateAdded

                    // Content URI로 EXIF GPS 읽기 (Android 10+ 호환)
                    val contentUri = android.content.ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
                    )

                    var lat = 0.0
                    var lng = 0.0

                    // 1) Content URI로 EXIF 읽기 (Android 10+ 권장)
                    try {
                        val exifGps = ExifHelper.getGpsFromUri(this@ImportGpxActivity, contentUri)
                        if (exifGps != null) {
                            lat = exifGps.latitude
                            lng = exifGps.longitude
                        }
                    } catch (e: Exception) {
                        android.util.Log.w("ImportGpx", "EXIF from URI failed: ${e.message}")
                    }

                    // 2) URI 실패 시 파일 경로로 시도
                    if (lat == 0.0 && lng == 0.0 && path.isNotEmpty()) {
                        try {
                            val exifGps = ExifHelper.getGpsFromFile(path)
                            if (exifGps != null) {
                                lat = exifGps.latitude
                                lng = exifGps.longitude
                            }
                        } catch (_: Exception) {}
                    }

                    // GPS 좌표가 있으면 GPX 경로와 거리 비교
                    if (lat != 0.0 || lng != 0.0) {
                        val nearTrack = isNearTrackPoints(lat, lng, trackPoints, 500.0)
                        android.util.Log.d("ImportGpx", "Photo id=$id, lat=$lat, lng=$lng, near=$nearTrack, path=$path")
                        if (nearTrack) {
                            result.add(MatchedPhoto(
                                uri = contentUri,
                                path = path,
                                latitude = lat,
                                longitude = lng,
                                dateTaken = dateTaken,
                                selected = true
                            ))
                        }
                    } else if (hasTimeData && dateTaken in gpxMinTime..gpxMaxTime) {
                        // GPS 없지만 시간이 정확히 GPX 구간 내인 경우
                        android.util.Log.d("ImportGpx", "Photo id=$id, no GPS, time match, path=$path")
                        result.add(MatchedPhoto(
                            uri = contentUri,
                            path = path,
                            latitude = 0.0,
                            longitude = 0.0,
                            dateTaken = dateTaken,
                            selected = false
                        ))
                    }
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("ImportGpx", "Photo search error: ${e.message}", e)
        } finally {
            cursor?.close()
        }

        android.util.Log.d("ImportGpx", "Total matched photos: ${result.size}")
        return result
    }

    /**
     * 주어진 좌표가 트랙포인트 중 하나와 maxDistance(m) 이내인지 확인
     */
    private fun isNearTrackPoints(
        lat: Double, lng: Double,
        trackPoints: List<TrackPoint>,
        maxDistance: Double
    ): Boolean {
        // 성능을 위해 10포인트마다 샘플링 (경로가 길면 모든 포인트 비교는 비효율)
        val step = maxOf(1, trackPoints.size / 200)
        for (i in trackPoints.indices step step) {
            val d = LocationUtils.distanceInMeters(
                lat, lng,
                trackPoints[i].latitude, trackPoints[i].longitude
            )
            if (d <= maxDistance) return true
        }
        return false
    }

    /**
     * 자동 매칭된 사진을 RecyclerView에 표시
     */
    private fun displayMatchedPhotos() {
        binding.rvAutoPhotos.visibility = View.VISIBLE
        binding.rvAutoPhotos.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )
        binding.rvAutoPhotos.adapter = MatchedPhotoAdapter(matchedPhotos)
    }

    private fun onPhotosSelected(uris: List<Uri>) {
        for (uri in uris) {
            val localPath = copyPhotoToLocal(uri)
            if (localPath != null) {
                importedPhotoPaths.add(localPath)
            }
        }
        displayImportedPhotos()
    }

    private fun copyPhotoToLocal(uri: Uri): String? {
        return try {
            val photoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: filesDir
            val fileName = "IMPORT_${System.currentTimeMillis()}_${importedPhotoPaths.size}.jpg"
            val destFile = File(photoDir, fileName)

            contentResolver.openInputStream(uri)?.use { input ->
                destFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            destFile.absolutePath
        } catch (e: Exception) {
            null
        }
    }

    private fun displayImportedPhotos() {
        if (importedPhotoPaths.isEmpty()) return

        binding.rvImportPhotos.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )
        binding.rvImportPhotos.adapter = ImportPhotoAdapter(importedPhotoPaths)
    }

    private fun saveImportedRecord() {
        val record = parsedRecord ?: return

        val name = binding.etRecordName.text.toString().trim().ifEmpty { record.mountainName }
        val memo = binding.etImportMemo.text.toString().trim()

        val finalRecord = record.copy(
            mountainName = name,
            memo = memo
        )

        scope.launch {
            try {
                val recordId = withContext(Dispatchers.IO) {
                    val id = repository.insertRecord(finalRecord)

                    // TrackPoint 저장 (recordId 설정)
                    val pointsWithId = parsedTrackPoints.map { it.copy(recordId = id) }
                    repository.insertTrackPoints(pointsWithId)

                    // 1) 자동 매칭에서 선택된 사진 저장
                    for (matched in matchedPhotos) {
                        if (!matched.selected) continue
                        val localPath = copyPhotoToLocal(matched.uri) ?: continue
                        val photo = HikingPhoto(
                            recordId = id,
                            filePath = localPath,
                            latitude = matched.latitude,
                            longitude = matched.longitude,
                            altitude = 0.0,
                            timestamp = matched.dateTaken
                        )
                        repository.insertPhoto(photo)
                    }

                    // 2) 수동으로 추가한 사진 저장
                    for (path in importedPhotoPaths) {
                        val gps = ExifHelper.getGpsFromFile(path)
                        val photo = HikingPhoto(
                            recordId = id,
                            filePath = path,
                            latitude = gps?.latitude ?: 0.0,
                            longitude = gps?.longitude ?: 0.0,
                            altitude = gps?.altitude ?: 0.0,
                            timestamp = System.currentTimeMillis()
                        )
                        repository.insertPhoto(photo)
                    }

                    id
                }

                Toast.makeText(this@ImportGpxActivity, R.string.import_save_success, Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@ImportGpxActivity, R.string.import_save_fail, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getFileNameFromUri(uri: Uri): String? {
        return try {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val nameIndex = it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                    if (nameIndex >= 0) it.getString(nameIndex) else null
                } else null
            }
        } catch (e: Exception) {
            null
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

    // 자동 매칭 사진 어댑터 (선택/해제 가능)
    inner class MatchedPhotoAdapter(private val items: MutableList<MatchedPhoto>) :
        RecyclerView.Adapter<MatchedPhotoAdapter.ViewHolder>() {

        inner class ViewHolder(val binding: com.onlysamhiking.app.databinding.ItemSelectablePhotoBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val b = com.onlysamhiking.app.databinding.ItemSelectablePhotoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ViewHolder(b)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]

            if (item.path.isNotEmpty()) {
                Glide.with(holder.itemView)
                    .load(File(item.path))
                    .centerCrop()
                    .into(holder.binding.ivPhoto)
            } else {
                Glide.with(holder.itemView)
                    .load(item.uri)
                    .centerCrop()
                    .into(holder.binding.ivPhoto)
            }

            // 선택 상태 표시
            holder.binding.ivCheck.visibility = if (item.selected) View.VISIBLE else View.GONE
            holder.binding.cardPhoto.strokeWidth = if (item.selected) 3 else 0
            holder.binding.cardPhoto.strokeColor = if (item.selected)
                Color.parseColor("#FF5722") else Color.TRANSPARENT

            // 시간 표시
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            holder.binding.tvPhotoTime.text = if (item.dateTaken > 0)
                timeFormat.format(Date(item.dateTaken)) else ""

            // 탭하여 선택/해제
            holder.itemView.setOnClickListener {
                item.selected = !item.selected
                notifyItemChanged(position)
                updateAutoPhotoStatus()
            }
        }

        override fun getItemCount() = items.size
    }

    private fun updateAutoPhotoStatus() {
        val selected = matchedPhotos.count { it.selected }
        val total = matchedPhotos.size
        binding.tvAutoPhotoStatus.text = "경로 근처 사진 ${total}장 발견 (${selected}장 선택됨)"
    }

    // 수동 추가 사진 어댑터
    inner class ImportPhotoAdapter(private val paths: List<String>) :
        RecyclerView.Adapter<ImportPhotoAdapter.ViewHolder>() {

        inner class ViewHolder(val binding: ItemHikingPhotoBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val b = ItemHikingPhotoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ViewHolder(b)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val path = paths[position]
            Glide.with(holder.itemView)
                .load(File(path))
                .centerCrop()
                .into(holder.binding.ivPhoto)

            holder.itemView.setOnLongClickListener {
                importedPhotoPaths.removeAt(position)
                notifyDataSetChanged()
                true
            }
        }

        override fun getItemCount() = paths.size
    }
}
