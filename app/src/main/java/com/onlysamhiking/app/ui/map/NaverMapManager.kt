package com.onlysamhiking.app.ui.map

import android.app.Activity
import android.graphics.*
import android.location.Location
import android.view.View
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource
import com.onlysamhiking.app.data.model.HikingPhoto
import com.onlysamhiking.app.data.model.TrackPoint

class NaverMapManager : MapManagerInterface, OnMapReadyCallback {

    private var mapView: MapView? = null
    private var naverMap: NaverMap? = null
    private var onReadyCallback: (() -> Unit)? = null
    private var locationSource: FusedLocationSource? = null

    private val routeCoords = mutableListOf<LatLng>()
    private var pathOverlay: PathOverlay? = null
    private var isFollowingUser = true
    private var headingMode = false

    // SAM marker
    private var samMarker: Marker? = null
    private var samMarkerIcon: OverlayImage? = null

    // Photo markers
    private val photoMarkers = mutableListOf<Marker>()
    private var fallbackMarkerIcon: OverlayImage? = null

    override fun initialize(container: View, onReady: () -> Unit) {
        onReadyCallback = onReady
        val context = container.context

        // FusedLocationSource 생성 (Face 모드에 필요)
        (context as? Activity)?.let {
            locationSource = FusedLocationSource(it, 1000)
        }

        mapView = MapView(context).also {
            (container as android.widget.FrameLayout).addView(it)
            it.onCreate(null)
            it.getMapAsync(this)
        }
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map

        // FusedLocationSource 설정 (LocationTrackingMode.Face에 필수)
        locationSource?.let { map.locationSource = it }

        map.uiSettings.apply {
            isZoomControlEnabled = true
            isCompassEnabled = true
            isScaleBarEnabled = true
            isLocationButtonEnabled = true
        }

        map.mapType = NaverMap.MapType.Terrain
        map.setLayerGroupEnabled(NaverMap.LAYER_GROUP_MOUNTAIN, true)

        // Default: Seoul area
        map.moveCamera(CameraUpdate.scrollAndZoomTo(
            LatLng(37.5665, 126.978), 11.0
        ))

        onReadyCallback?.invoke()
    }

    override fun moveCamera(lat: Double, lng: Double, zoom: Float) {
        naverMap?.moveCamera(CameraUpdate.scrollAndZoomTo(
            LatLng(lat, lng), zoom.toDouble()
        ).animate(CameraAnimation.Easing))
    }

    override fun moveCameraToLocation(location: Location) {
        if (isFollowingUser || headingMode) {
            val latLng = LatLng(location.latitude, location.longitude)
            if (headingMode) {
                // 진행방향 모드: bearing 기반 카메라 회전 + 틸트
                val currentZoom = naverMap?.cameraPosition?.zoom ?: 16.0
                val bearing = if (location.hasBearing()) location.bearing.toDouble()
                    else naverMap?.cameraPosition?.bearing ?: 0.0
                val position = CameraPosition(latLng, currentZoom, 40.0, bearing)
                naverMap?.moveCamera(
                    CameraUpdate.toCameraPosition(position)
                        .animate(CameraAnimation.Linear, 500)
                )
            } else {
                moveCamera(location.latitude, location.longitude, 16f)
                isFollowingUser = false
            }
        }
    }

    override fun addRoutePoint(lat: Double, lng: Double) {
        routeCoords.add(LatLng(lat, lng))
        updatePathOverlay()
    }

    override fun drawRoute(points: List<TrackPoint>) {
        routeCoords.clear()
        routeCoords.addAll(points.map { LatLng(it.latitude, it.longitude) })
        updatePathOverlay()

        if (routeCoords.size >= 2) {
            val bounds = com.naver.maps.geometry.LatLngBounds.Builder()
                .include(routeCoords)
                .build()
            naverMap?.moveCamera(CameraUpdate.fitBounds(bounds, 100)
                .animate(CameraAnimation.Easing))
        }
    }

    private fun updatePathOverlay() {
        if (routeCoords.size < 2) return

        if (pathOverlay == null) {
            pathOverlay = PathOverlay().apply {
                color = Color.parseColor("#1565C0")
                outlineColor = Color.parseColor("#0D47A1")
                width = 12
                outlineWidth = 3
            }
        }

        pathOverlay?.apply {
            coords = routeCoords.toList()
            map = naverMap
        }
    }

    override fun clearRoute() {
        pathOverlay?.map = null
        pathOverlay = null
        routeCoords.clear()
    }

    @Suppress("MissingPermission")
    override fun setMyLocationEnabled(enabled: Boolean) {
        naverMap?.locationTrackingMode = if (enabled) {
            if (headingMode) LocationTrackingMode.Face
            else LocationTrackingMode.Follow
        } else {
            LocationTrackingMode.None
        }
    }

    // -- SAM Marker --
    override fun updateSamMarker(lat: Double, lng: Double) {
        if (samMarkerIcon == null) {
            samMarkerIcon = OverlayImage.fromBitmap(createSamMarkerBitmap())
        }

        if (samMarker == null) {
            samMarker = Marker().apply {
                icon = samMarkerIcon!!
                anchor = android.graphics.PointF(0.5f, 0.5f)
                isFlat = true
            }
        }

        samMarker?.apply {
            position = LatLng(lat, lng)
            map = naverMap
        }
    }

    override fun removeSamMarker() {
        samMarker?.map = null
        samMarker = null
    }

    // -- Heading mode --
    override fun setHeadingMode(followHeading: Boolean) {
        headingMode = followHeading
        isFollowingUser = true
        naverMap?.locationTrackingMode = if (followHeading) {
            LocationTrackingMode.Face
        } else {
            LocationTrackingMode.Follow
        }
        if (!followHeading) {
            // 북쪽 고정으로 복원 (bearing=0, tilt=0)
            val current = naverMap?.cameraPosition ?: return
            naverMap?.moveCamera(
                CameraUpdate.toCameraPosition(
                    CameraPosition(current.target, current.zoom, 0.0, 0.0)
                ).animate(CameraAnimation.Easing)
            )
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
                OverlayImage.fromBitmap(markerBitmap)
            } else {
                if (fallbackMarkerIcon == null) {
                    fallbackMarkerIcon = OverlayImage.fromBitmap(createCameraMarkerBitmap())
                }
                fallbackMarkerIcon!!
            }

            val marker = Marker().apply {
                position = LatLng(photo.latitude, photo.longitude)
                this.icon = icon
                anchor = android.graphics.PointF(0.5f, 1.0f)
                zIndex = 5
                map = naverMap
            }
            photoMarkers.add(marker)
        }
    }

    private fun clearPhotoMarkers() {
        photoMarkers.forEach { it.map = null }
        photoMarkers.clear()
    }

    // -- Bitmap generators --
    private fun createSamMarkerBitmap(): Bitmap {
        val size = 80
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Circle background
        val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#2E7D32")
            style = Paint.Style.FILL
        }
        canvas.drawCircle(size / 2f, size / 2f, size / 2f - 2f, circlePaint)

        // White border
        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = 3f
        }
        canvas.drawCircle(size / 2f, size / 2f, size / 2f - 2f, borderPaint)

        // SAM text
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
            val file = java.io.File(filePath)
            if (!file.exists()) return null

            // 원본 크기 확인
            val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeFile(filePath, options)
            if (options.outWidth <= 0 || options.outHeight <= 0) return null

            // 축소 비율 계산 (64px 정도로 축소)
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

            // 핀 꼬리 (삼각형) - 흰 테두리 포함
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

            // 핀 꼬리 (삼각형) - 내부 색상
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

            // 원형 흰색 배경 (테두리 역할)
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

            // 사진을 원형 영역에 맞게 크롭하여 그리기
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

    /**
     * 비트맵을 중앙 기준으로 크롭
     */
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

        // Pin body (circle)
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

        // Camera icon (white rectangle with lens)
        val cameraPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }
        // Camera body
        canvas.drawRoundRect(RectF(28f, 26f, 68f, 52f), 5f, 5f, cameraPaint)
        // Lens circle
        val lensPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#FF5722")
            style = Paint.Style.FILL
        }
        canvas.drawCircle(48f, 39f, 10f, lensPaint)
        // Inner lens
        val innerLensPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }
        canvas.drawCircle(48f, 39f, 4f, innerLensPaint)
        // Small flash/viewfinder
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
