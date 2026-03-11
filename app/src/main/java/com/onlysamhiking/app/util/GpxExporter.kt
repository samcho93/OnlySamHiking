package com.onlysamhiking.app.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.onlysamhiking.app.data.model.HikingRecord
import com.onlysamhiking.app.data.model.TrackPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object GpxExporter {

    /**
     * GPX 내보내기 결과
     */
    data class ExportResult(
        val success: Boolean,
        val gpxUri: Uri? = null,
        val gpxFilePath: String? = null
    )

    /**
     * GPX 콘텐츠 생성
     */
    fun buildGpxContent(record: HikingRecord, trackPoints: List<TrackPoint>): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        return buildString {
            appendLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
            appendLine("<gpx version=\"1.1\" creator=\"OnlySamHiking\"")
            appendLine("  xmlns=\"http://www.topografix.com/GPX/1/1\"")
            appendLine("  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"")
            appendLine("  xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\">")
            appendLine("  <metadata>")
            appendLine("    <name>${escapeXml(record.mountainName)}</name>")
            appendLine("    <time>${dateFormat.format(Date(record.startTime))}</time>")
            appendLine("  </metadata>")
            appendLine("  <trk>")
            appendLine("    <name>${escapeXml(record.mountainName)}</name>")
            appendLine("    <trkseg>")

            for (point in trackPoints) {
                appendLine("      <trkpt lat=\"${point.latitude}\" lon=\"${point.longitude}\">")
                appendLine("        <ele>${point.altitude}</ele>")
                appendLine("        <time>${dateFormat.format(Date(point.timestamp))}</time>")
                if (point.speed > 0) {
                    appendLine("        <speed>${point.speed}</speed>")
                }
                appendLine("      </trkpt>")
            }

            appendLine("    </trkseg>")
            appendLine("  </trk>")
            appendLine("</gpx>")
        }
    }

    /**
     * GPX를 Downloads에 저장
     */
    fun exportToGpx(
        context: Context,
        record: HikingRecord,
        trackPoints: List<TrackPoint>
    ): Boolean {
        val gpxContent = buildGpxContent(record, trackPoints)
        return saveToDownloads(context, record, gpxContent)
    }

    /**
     * GPX 파일을 앱 내부 저장소에 저장 후 공유 Intent 생성
     */
    fun exportAndGetShareIntent(
        context: Context,
        record: HikingRecord,
        trackPoints: List<TrackPoint>
    ): Intent? {
        val gpxContent = buildGpxContent(record, trackPoints)

        // 앱 내부 캐시에 임시 저장
        val dateStr = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(Date(record.startTime))
        val fileName = "${record.mountainName}_$dateStr.gpx"

        val cacheDir = File(context.cacheDir, "gpx_export")
        if (!cacheDir.exists()) cacheDir.mkdirs()
        val gpxFile = File(cacheDir, fileName)

        return try {
            gpxFile.writeText(gpxContent, Charsets.UTF_8)

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                gpxFile
            )

            Intent(Intent.ACTION_SEND).apply {
                type = "application/gpx+xml"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_SUBJECT, record.mountainName)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun saveToDownloads(
        context: Context,
        record: HikingRecord,
        content: String
    ): Boolean {
        val dateStr = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(Date(record.startTime))
        val fileName = "${record.mountainName}_$dateStr.gpx"

        val values = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, "application/gpx+xml")
            put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/OnlySamHiking")
        }

        return try {
            val uri = context.contentResolver.insert(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI, values
            )
            uri?.let {
                context.contentResolver.openOutputStream(it)?.use { stream ->
                    stream.write(content.toByteArray(Charsets.UTF_8))
                }
                true
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    private fun escapeXml(text: String): String {
        return text
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&apos;")
    }
}
