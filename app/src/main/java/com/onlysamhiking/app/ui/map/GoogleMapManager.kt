package com.onlysamhiking.app.ui.map

import android.graphics.*
import android.location.Location
import android.view.View
import java.io.File
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.onlysamhiking.app.data.model.HikingPhoto
import com.onlysamhiking.app.data.model.TrackPoint

class GoogleMapManager : MapManagerInterface, OnMapReadyCallback {

    private var mapView: MapView? = null
    private var googleMap: GoogleMap? = null
    private var onReadyCallback: (() -> Unit)? = null

    private var routePolyline: Polyline? = null
    private val routePoints = mutableListOf<LatLng>()
    private var isFollowingUser = true
    private var headingMode = false

    // SAM marker
    private var samMarker: Marker? = null
    private var samMarkerDescriptor: BitmapDescriptor? = null

    // Photo markers
    private val photoMarkers = mutableListOf<Marker>()
    private var fallbackMarkerDescriptor: BitmapDescriptor? = null

    override fun initialize(container: View, onReady: () -> Unit) {
        onReadyCallback = onReady
        val context = container.context

        mapView = MapView(context).also {
            (container as android.widget.FrameLayout).addView(it)
            it.onCreate(null)
            it.getMapAsync(this)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        map.mapType = GoogleMap.MAP_TYPE_TERRAIN
        map.uiSettings.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMyLocationButtonEnabled = true
        }

        // Default: Seoul area
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
            LatLng(37.5665, 126.978), 11f
        ))

        onReadyCallback?.invoke()
    }

    override fun moveCamera(lat: Double, lng: Double, zoom: Float) {
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(
            LatLng(lat, lng), zoom
        ))
    }

    override fun moveCameraToLocation(location: Location) {
        if (isFollowingUser || headingMode) {
            if (headingMode) {
                // 진행방향 모드: bearing 기반 카메라 회전 + 틸트
                val currentZoom = googleMap?.cameraPosition?.zoom ?: 16f
                val bearing = if (location.hasBearing()) location.bearing
                    else googleMap?.cameraPosition?.bearing ?: 0f
                val cameraPosition = com.google.android.gms.maps.model.CameraPosition.Builder()
                    .target(LatLng(location.latitude, location.longitude))
                    .zoom(currentZoom)
                    .tilt(40f)
                    .bearing(bearing)
                    .build()
                googleMap?.animateCamera(
                    CameraUpdateFactory.newCameraPosition(cameraPosition), 500, null
                )
            } else {
                moveCamera(location.latitude, location.longitude, 16f)
                isFollowingUser = false
            }
        }
    }

    override fun addRoutePoint(lat: Double, lng: Double) {
        routePoints.add(LatLng(lat, lng))
        updatePolyline()
    }

    override fun drawRoute(points: List<TrackPoint>) {
        routePoints.clear()
        routePoints.addAll(points.map { LatLng(it.latitude, it.longitude) })
        updatePolyline()

        if (routePoints.size >= 2) {
            val bounds = LatLngBounds.Builder()
            routePoints.forEach { bounds.include(it) }
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
        }
    }

    private fun updatePolyline() {
        if (routePoints.size < 2) return

        routePolyline?.remove()
        routePolyline = googleMap?.addPolyline(
            PolylineOptions()
                .addAll(routePoints)
                .color(Color.parseColor("#1565C0"))
                .width(10f)
                .jointType(JointType.ROUND)
                .startCap(RoundCap())
                .endCap(RoundCap())
        )
    }

    override fun clearRoute() {
        routePolyline?.remove()
        routePolyline = null
        routePoints.clear()
    }

    @Suppress("MissingPermission")
    override fun setMyLocationEnabled(enabled: Boolean) {
        googleMap?.isMyLocationEnabled = enabled
    }

    // -- SAM Marker --
    override fun updateSamMarker(lat: Double, lng: Double) {
        if (samMarkerDescriptor == null) {
            samMarkerDescriptor = BitmapDescriptorFactory.fromBitmap(createSamMarkerBitmap())
        }

        if (samMarker == null) {
            samMarker = googleMap?.addMarker(
                MarkerOptions()
                    .position(LatLng(lat, lng))
                    .icon(samMarkerDescriptor)
                    .anchor(0.5f, 0.5f)
                    .flat(true)
                    .zIndex(10f)
            )
        } else {
            samMarker?.position = LatLng(lat, lng)
        }
    }

    override fun removeSamMarker() {
        samMarker?.remove()
        samMarker = null
    }

    // -- Heading mode --
    override fun setHeadingMode(followHeading: Boolean) {
        headingMode = followHeading
        isFollowingUser = true
        if (!followHeading) {
            // 북쪽 고정으로 복원 (bearing=0, tilt=0)
            googleMap?.let { map ->
                val current = map.cameraPosition
                val northUp = com.google.android.gms.maps.model.CameraPosition.Builder()
                    .target(current.target)
                    .zoom(current.zoom)
                    .tilt(0f)
                    .bearing(0f)
                    .build()
                map.animateCamera(CameraUpdateFactory.newCameraPosition(northUp))
            }
        }
    }

    override fun isHeadingMode(): Boolean = headingMode

    // -- Photo markers (썸네일) --
    override fun addPhotoMarkers(photos: List<HikingPhoto>) {
        clearPhotoMarkers()

        for (photo in photos) {
            if (photo.latitude == 0.0 && photo.longitude == 0.0) continue

            val markerBitmap = createThumbnailMarkerBitmap(photo.filePath)
            val icon = if (markerBitmap != null) {
                BitmapDescriptorFactory.fromBitmap(markerBitmap)
            } else {
                if (fallbackMarkerDescriptor == null) {
                    fallbackMarkerDescriptor = BitmapDescriptorFactory.fromBitmap(createCameraMarkerBitmap())
                }
                fallbackMarkerDescriptor!!
            }

            val marker = googleMap?.addMarker(
                MarkerOptions()
                    .position(LatLng(photo.latitude, photo.longitude))
                    .icon(icon)
                    .anchor(0.5f, 1.0f)
                    .zIndex(5f)
            )
            marker?.let { photoMarkers.add(it) }
        }
    }

    private fun clearPhotoMarkers() {
        photoMarkers.forEach { it.remove() }
        photoMarkers.clear()
    }

    // -- Bitmap generators --
    private fun createSamMarkerBitmap(): Bitmap {
        val size = 80
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#2E7D32")
            style = Paint.Style.FILL
        }
        canvas.drawCircle(size / 2f, size / 2f, size / 2f - 2f, circlePaint)

        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = 3f
        }
        canvas.drawCircle(size / 2f, size / 2f, size / 2f - 2f, borderPaint)

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textSize = 24f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        val textBounds = Rect()
        textPaint.getTextBounds("SAM", 0, 3, textBounds)
        canvas.drawText("SAM", size / 2f, size / 2f + textBounds.height() / 2f, textPaint)

        return bitmap
    }

    /**
     * 사진 파일에서 썸네일을 읽어 핀 모양 마커 비트맵 생성
     */
    private fun createThumbnailMarkerBitmap(filePath: String): Bitmap? {
        return try {
            val file = File(filePath)
            if (!file.exists()) return null

            val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeFile(filePath, options)
            if (options.outWidth <= 0 || options.outHeight <= 0) return null

            val targetSize = 64
            val sampleSize = maxOf(options.outWidth / targetSize, options.outHeight / targetSize, 1)
            val decodeOptions = BitmapFactory.Options().apply { inSampleSize = sampleSize }
            val thumbnail = BitmapFactory.decodeFile(filePath, decodeOptions) ?: return null

            val width = 96
            val height = 112
            val circleRadius = 36f
            val centerX = width / 2f
            val centerY = 40f

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            // 그림자
            val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.parseColor("#40000000")
                style = Paint.Style.FILL
            }
            canvas.drawCircle(centerX + 2f, centerY + 2f, circleRadius + 2f, shadowPaint)

            // 핀 꼬리 - 흰 테두리
            val pinTailPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.WHITE
                style = Paint.Style.FILL
            }
            val tailPath = Path().apply {
                moveTo(centerX - 22f, 64f)
                lineTo(centerX, height.toFloat() - 2f)
                lineTo(centerX + 22f, 64f)
                close()
            }
            canvas.drawPath(tailPath, pinTailPaint)

            // 핀 꼬리 - 내부 색상
            val pinInnerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.parseColor("#FF5722")
                style = Paint.Style.FILL
            }
            val innerTailPath = Path().apply {
                moveTo(centerX - 18f, 64f)
                lineTo(centerX, height.toFloat() - 8f)
                lineTo(centerX + 18f, 64f)
                close()
            }
            canvas.drawPath(innerTailPath, pinInnerPaint)

            // 원형 흰색 배경
            val whiteBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.WHITE
                style = Paint.Style.FILL
            }
            canvas.drawCircle(centerX, centerY, circleRadius, whiteBgPaint)

            // 원형 클리핑으로 썸네일 그리기
            val saveCount = canvas.save()
            val clipPath = Path().apply {
                addCircle(centerX, centerY, circleRadius - 3f, Path.Direction.CW)
            }
            canvas.clipPath(clipPath)

            val thumbSize = ((circleRadius - 3f) * 2).toInt()
            val scaledThumb = centerCropBitmap(thumbnail, thumbSize, thumbSize)
            canvas.drawBitmap(
                scaledThumb,
                centerX - thumbSize / 2f,
                centerY - thumbSize / 2f,
                null
            )
            canvas.restoreToCount(saveCount)

            // 원형 테두리
            val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.WHITE
                style = Paint.Style.STROKE
                strokeWidth = 4f
            }
            canvas.drawCircle(centerX, centerY, circleRadius - 1f, borderPaint)

            thumbnail.recycle()
            if (scaledThumb !== thumbnail) scaledThumb.recycle()

            bitmap
        } catch (e: Exception) {
            null
        }
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

    private fun createCameraMarkerBitmap(): Bitmap {
        val width = 96
        val height = 112
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // 그림자
        val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#40000000")
            style = Paint.Style.FILL
        }
        canvas.drawCircle(width / 2f + 2f, 42f, 38f, shadowPaint)

        // Pin shape
        val pinPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#FF5722")
            style = Paint.Style.FILL
        }
        canvas.drawCircle(width / 2f, 40f, 36f, pinPaint)

        // Pin point (triangle)
        val path = Path().apply {
            moveTo(width / 2f - 20f, 64f)
            lineTo(width / 2f, height.toFloat() - 4f)
            lineTo(width / 2f + 20f, 64f)
            close()
        }
        canvas.drawPath(path, pinPaint)

        // 흰색 테두리
        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }
        canvas.drawCircle(width / 2f, 40f, 36f, borderPaint)

        // Camera icon
        val cameraPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }
        canvas.drawRoundRect(RectF(28f, 26f, 68f, 52f), 5f, 5f, cameraPaint)

        val lensPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#FF5722")
            style = Paint.Style.FILL
        }
        canvas.drawCircle(48f, 39f, 10f, lensPaint)

        val innerLensPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }
        canvas.drawCircle(48f, 39f, 4f, innerLensPaint)
        canvas.drawRoundRect(RectF(36f, 20f, 52f, 27f), 3f, 3f, cameraPaint)

        return bitmap
    }

    override fun onResume() {
        mapView?.onResume()
    }

    override fun onPause() {
        mapView?.onPause()
    }

    override fun onDestroy() {
        clearPhotoMarkers()
        removeSamMarker()
        mapView?.onDestroy()
    }
}
