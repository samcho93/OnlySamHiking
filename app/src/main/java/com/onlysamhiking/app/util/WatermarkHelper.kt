package com.onlysamhiking.app.util

import android.content.ContentValues
import android.content.Context
import android.graphics.*
import android.graphics.drawable.VectorDrawable
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import com.onlysamhiking.app.R
import com.onlysamhiking.app.data.model.HikingRecord
import com.onlysamhiking.app.data.model.TrackPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object WatermarkHelper {

    fun createWatermarkedPhoto(
        context: Context,
        photoPath: String,
        record: HikingRecord,
        trackPoints: List<TrackPoint> = emptyList()
    ): Boolean {
        return createWatermarkedPhotoAndGetPath(context, photoPath, record, trackPoints) != null
    }

    fun createWatermarkedPhotoAndGetPath(
        context: Context,
        photoPath: String,
        record: HikingRecord,
        trackPoints: List<TrackPoint> = emptyList()
    ): String? {
        // 원본 EXIF 정보 읽기 (GPS, 방향 등)
        val originalExif = try {
            ExifInterface(photoPath)
        } catch (e: Exception) {
            null
        }

        // EXIF 방향 읽기
        val orientation = originalExif?.getAttributeInt(
            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
        ) ?: ExifInterface.ORIENTATION_NORMAL

        // 비트맵 디코딩
        val rawBitmap = BitmapFactory.decodeFile(photoPath) ?: return null

        // EXIF 방향 적용 (사진의 찍은 방향을 변경하지 않고 올바르게 표시)
        val orientedBitmap = applyOrientation(rawBitmap, orientation)
        if (orientedBitmap !== rawBitmap) rawBitmap.recycle()

        val bitmap = orientedBitmap.copy(Bitmap.Config.ARGB_8888, true)
        if (bitmap !== orientedBitmap) orientedBitmap.recycle()

        val canvas = Canvas(bitmap)
        val width = bitmap.width.toFloat()
        val height = bitmap.height.toFloat()

        // 텍스트 크기 - 2배 크게 (기존 0.028f -> 0.056f)
        val textSize = height * 0.056f
        val smallTextSize = textSize * 0.75f

        // 외곽선(검정) + 채우기(흰색) 페인트 - 배경 없음
        val outlinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            this.textSize = textSize
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            style = Paint.Style.STROKE
            strokeWidth = textSize * 0.1f
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }

        val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            this.textSize = textSize
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            style = Paint.Style.FILL
        }

        val smallOutlinePaint = Paint(outlinePaint).apply {
            this.textSize = smallTextSize
            strokeWidth = smallTextSize * 0.1f
        }

        val smallFillPaint = Paint(fillPaint).apply {
            this.textSize = smallTextSize
        }

        val padding = width * 0.03f

        // 텍스트 내용 준비
        val dateFormat = SimpleDateFormat("yyyy.MM.dd (EEE) HH:mm", Locale.KOREAN)
        val mountainName = record.mountainName.ifEmpty { "산행 기록" }
        val dateLine = dateFormat.format(Date(record.startTime))
        val distStr = LocationUtils.formatDistance(record.distance)
        val timeStr = record.durationFormatted
        val statsLine = "$distStr  |  $timeStr"
        val elevLine = "↑${record.elevationGain.toInt()}m  ↓${record.elevationLoss.toInt()}m"
        val altCalLine = "최고 ${record.maxAltitude.toInt()}m  |  ${record.calories}kcal"

        // 왼쪽 하단에 텍스트 그리기 (아래에서 위로)
        var y = height - padding

        // Line 4: 상승고도 + 하강고도 (같은 행)
        drawOutlinedText(canvas, elevLine, padding, y, smallOutlinePaint, smallFillPaint)
        y -= smallTextSize * 1.4f

        // Line 3: 최고고도 + 칼로리
        drawOutlinedText(canvas, altCalLine, padding, y, smallOutlinePaint, smallFillPaint)
        y -= smallTextSize * 1.4f

        // Line 2: 거리 + 시간
        drawOutlinedText(canvas, statsLine, padding, y, smallOutlinePaint, smallFillPaint)
        y -= smallTextSize * 1.4f

        // Line 1: 날짜
        drawOutlinedText(canvas, dateLine, padding, y, smallOutlinePaint, smallFillPaint)
        y -= textSize * 1.4f

        // Line 0: 산 이름 (큰 글씨)
        drawOutlinedText(canvas, mountainName, padding, y, outlinePaint, fillPaint)

        // 오른쪽 하단에 고도 그래프 추가
        if (trackPoints.isNotEmpty()) {
            drawAltitudeGraph(canvas, trackPoints, width, height, padding)
        }

        // 오른쪽 상단 앱 아이콘
        drawAppIcon(context, canvas, width, padding, textSize)

        // 저장
        val savedPath = saveWatermarkedBitmapAndGetPath(context, bitmap, record)

        // EXIF GPS 데이터 복사 (위치정보 유지)
        if (savedPath != null && originalExif != null) {
            copyExifData(originalExif, savedPath)
        }

        return savedPath
    }

    /**
     * 외곽선(검정) + 채우기(흰색) 텍스트 그리기
     */
    private fun drawOutlinedText(
        canvas: Canvas,
        text: String,
        x: Float,
        y: Float,
        outlinePaint: Paint,
        fillPaint: Paint
    ) {
        canvas.drawText(text, x, y, outlinePaint)  // 검정 외곽선
        canvas.drawText(text, x, y, fillPaint)      // 흰색 채우기
    }

    /**
     * 오른쪽 상단에 앱 아이콘 그리기
     */
    private fun drawAppIcon(
        context: Context,
        canvas: Canvas,
        width: Float,
        padding: Float,
        textSize: Float
    ) {
        try {
            val iconSize = (textSize * 1.5f).toInt()
            val drawable = ContextCompat.getDrawable(context, R.mipmap.ic_launcher)
            if (drawable != null) {
                val iconBitmap = Bitmap.createBitmap(iconSize, iconSize, Bitmap.Config.ARGB_8888)
                val iconCanvas = Canvas(iconBitmap)
                drawable.setBounds(0, 0, iconSize, iconSize)
                drawable.draw(iconCanvas)

                val left = width - iconSize - padding
                val top = padding
                canvas.drawBitmap(iconBitmap, left, top, null)
                iconBitmap.recycle()
            }
        } catch (e: Exception) {
            // Icon drawing is best-effort
        }
    }

    /**
     * EXIF 방향에 따라 비트맵 회전/반전
     */
    private fun applyOrientation(bitmap: Bitmap, orientation: Int): Bitmap {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.preScale(-1f, 1f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.preScale(1f, -1f)
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.postRotate(90f)
                matrix.preScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.postRotate(270f)
                matrix.preScale(-1f, 1f)
            }
            else -> return bitmap // 변환 불필요
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    /**
     * 오른쪽 하단에 미니 고도 그래프 그리기
     */
    private fun drawAltitudeGraph(
        canvas: Canvas,
        trackPoints: List<TrackPoint>,
        width: Float,
        height: Float,
        padding: Float
    ) {
        val graphWidth = width * 0.35f
        val graphHeight = height * 0.15f
        val graphLeft = width - graphWidth - padding
        val graphTop = height - graphHeight - padding
        val graphRight = graphLeft + graphWidth
        val graphBottom = graphTop + graphHeight

        // 트랙포인트 샘플링 (그래프용으로 최대 200개)
        val sampled = if (trackPoints.size > 200) {
            val step = trackPoints.size / 200
            trackPoints.filterIndexed { i, _ -> i % step == 0 }
        } else trackPoints

        if (sampled.size < 2) return

        val altitudes = sampled.map { it.altitude }
        val minAlt = altitudes.min()
        val maxAlt = altitudes.max()
        val altRange = (maxAlt - minAlt).coerceAtLeast(1.0)

        // 반투명 배경 (그래프 영역)
        val bgPaint = Paint().apply {
            color = Color.parseColor("#50000000")
            style = Paint.Style.FILL
        }
        canvas.drawRoundRect(
            RectF(graphLeft - 4f, graphTop - 4f, graphRight + 4f, graphBottom + 4f),
            8f, 8f, bgPaint
        )

        // 고도 라인 경로
        val linePath = Path()
        val graphInnerPadding = 8f

        for (i in sampled.indices) {
            val x = graphLeft + graphInnerPadding +
                (i.toFloat() / (sampled.size - 1)) * (graphWidth - 2 * graphInnerPadding)
            val normalized = ((sampled[i].altitude - minAlt) / altRange).toFloat()
            val y = graphBottom - graphInnerPadding -
                normalized * (graphHeight - 2 * graphInnerPadding)

            if (i == 0) linePath.moveTo(x, y)
            else linePath.lineTo(x, y)
        }

        // 채우기 (라인 아래 영역)
        val fillPath = Path(linePath)
        val lastX = graphLeft + graphInnerPadding +
            ((sampled.size - 1).toFloat() / (sampled.size - 1)) * (graphWidth - 2 * graphInnerPadding)
        fillPath.lineTo(lastX, graphBottom - graphInnerPadding)
        fillPath.lineTo(graphLeft + graphInnerPadding, graphBottom - graphInnerPadding)
        fillPath.close()

        val graphFillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#604CAF50")
            style = Paint.Style.FILL
        }
        canvas.drawPath(fillPath, graphFillPaint)

        // 고도 라인
        val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#4CAF50")
            style = Paint.Style.STROKE
            strokeWidth = 3f
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
        canvas.drawPath(linePath, linePaint)

        // 고도 범위 텍스트
        val labelSize = graphHeight * 0.12f
        val labelOutline = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            textSize = labelSize
            style = Paint.Style.STROKE
            strokeWidth = labelSize * 0.15f
            strokeJoin = Paint.Join.ROUND
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        val labelFill = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textSize = labelSize
            style = Paint.Style.FILL
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        val maxLabel = "${maxAlt.toInt()}m"
        val minLabel = "${minAlt.toInt()}m"
        drawOutlinedText(canvas, maxLabel, graphLeft + graphInnerPadding,
            graphTop + graphInnerPadding + labelSize, labelOutline, labelFill)
        drawOutlinedText(canvas, minLabel, graphLeft + graphInnerPadding,
            graphBottom - graphInnerPadding, labelOutline, labelFill)
    }

    /**
     * 원본 EXIF GPS/날짜 데이터를 워터마크 파일에 복사
     */
    private fun copyExifData(sourceExif: ExifInterface, destPath: String) {
        try {
            val destExif = ExifInterface(destPath)

            val tagsToCopy = arrayOf(
                // GPS 데이터
                ExifInterface.TAG_GPS_LATITUDE,
                ExifInterface.TAG_GPS_LATITUDE_REF,
                ExifInterface.TAG_GPS_LONGITUDE,
                ExifInterface.TAG_GPS_LONGITUDE_REF,
                ExifInterface.TAG_GPS_ALTITUDE,
                ExifInterface.TAG_GPS_ALTITUDE_REF,
                ExifInterface.TAG_GPS_TIMESTAMP,
                ExifInterface.TAG_GPS_DATESTAMP,
                ExifInterface.TAG_GPS_PROCESSING_METHOD,
                // 촬영 날짜
                ExifInterface.TAG_DATETIME,
                ExifInterface.TAG_DATETIME_ORIGINAL,
                ExifInterface.TAG_DATETIME_DIGITIZED,
                // 카메라 정보
                ExifInterface.TAG_MAKE,
                ExifInterface.TAG_MODEL
            )

            for (tag in tagsToCopy) {
                val value = sourceExif.getAttribute(tag)
                if (value != null) {
                    destExif.setAttribute(tag, value)
                }
            }

            destExif.saveAttributes()
        } catch (e: Exception) {
            // EXIF copy is best-effort
        }
    }

    private fun saveWatermarkedBitmapAndGetPath(
        context: Context,
        bitmap: Bitmap,
        record: HikingRecord
    ): String? {
        val dateStr = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(Date(record.startTime))
        val timeStr = SimpleDateFormat("HHmmss", Locale.getDefault()).format(Date())
        val fileName = "WM_${record.mountainName}_${dateStr}_$timeStr.jpg"

        // 앱 내부 저장소에 저장
        val appDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "watermark")
        if (!appDir.exists()) appDir.mkdirs()
        val localFile = File(appDir, fileName)

        try {
            localFile.outputStream().use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)
            }
        } catch (e: Exception) {
            bitmap.recycle()
            return null
        }

        // 갤러리에도 저장
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + "/OnlySamHiking")
        }

        try {
            val uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
            )
            uri?.let {
                context.contentResolver.openOutputStream(it)?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)
                }
            }
        } catch (e: Exception) {
            // Gallery save is best-effort
        } finally {
            bitmap.recycle()
        }

        return localFile.absolutePath
    }
}
