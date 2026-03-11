package com.onlysamhiking.app.ui.mountain

import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.onlysamhiking.app.data.repository.HikingRepository
import com.onlysamhiking.app.data.repository.MountainRepository
import com.onlysamhiking.app.databinding.ActivityMountainMapBinding
import com.onlysamhiking.app.databinding.ItemVisitedMountainBinding
import kotlinx.coroutines.*

data class VisitedMountain(
    val name: String,
    val lat: Double,
    val lng: Double,
    val alt: Int,
    val visitCount: Int,
    val lastVisit: Long
)

class MountainMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMountainMapBinding
    private var naverMap: NaverMap? = null
    private var mapView: MapView? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val markers = mutableListOf<Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMountainMapBinding.inflate(layoutInflater)
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
        // 하단 네비게이션바에 가려지지 않도록 인셋 처리
        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomPanel) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                bottom = insets.bottom + 12,
                left = view.paddingLeft,
                right = view.paddingRight,
                top = view.paddingTop
            )
            windowInsets
        }

        binding.rvVisitedMountains.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )

        mapView = MapView(this).also {
            binding.mountainMapContainer.addView(it)
            it.onCreate(savedInstanceState)
            it.getMapAsync(this)
        }
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map

        map.mapType = NaverMap.MapType.Terrain
        map.setLayerGroupEnabled(NaverMap.LAYER_GROUP_MOUNTAIN, true)
        map.uiSettings.apply {
            isZoomControlEnabled = true
            isCompassEnabled = true
            isScaleBarEnabled = true
        }

        // South Korea bounds
        val southKoreaBounds = LatLngBounds(
            LatLng(33.0, 124.5),
            LatLng(38.6, 131.0)
        )
        map.moveCamera(CameraUpdate.fitBounds(southKoreaBounds, 50))

        loadVisitedMountains()
    }

    private fun loadVisitedMountains() {
        val hikingRepo = HikingRepository(this)
        val mountainRepo = MountainRepository(this)

        scope.launch {
            val records = withContext(Dispatchers.IO) { hikingRepo.getAllRecordsList() }

            val mountainMap = mutableMapOf<String, MutableList<com.onlysamhiking.app.data.model.HikingRecord>>()
            for (rec in records) {
                if (rec.mountainName.isNotEmpty()) {
                    mountainMap.getOrPut(rec.mountainName) { mutableListOf() }.add(rec)
                }
            }

            val visited = mountainMap.map { (name, recs) ->
                val firstRec = recs.first()
                val mountain = mountainRepo.findNearestMountain(firstRec.startLat, firstRec.startLng)
                VisitedMountain(
                    name = name,
                    lat = mountain?.lat ?: firstRec.startLat,
                    lng = mountain?.lng ?: firstRec.startLng,
                    alt = mountain?.alt ?: firstRec.maxAltitude.toInt(),
                    visitCount = recs.size,
                    lastVisit = recs.maxOf { it.startTime }
                )
            }

            // Add markers on map
            val markerIcon = OverlayImage.fromBitmap(createMountainMarkerBitmap())
            for (mt in visited) {
                if (mt.lat == 0.0 && mt.lng == 0.0) continue
                val marker = Marker().apply {
                    position = LatLng(mt.lat, mt.lng)
                    icon = markerIcon
                    captionText = "${mt.name}\n(${mt.visitCount}회)"
                    captionTextSize = 11f
                    map = naverMap
                }
                markers.add(marker)
            }

            binding.tvMountainCount.text = "방문한 산: ${visited.size}개 (총 ${records.size}회)"
            binding.rvVisitedMountains.adapter = VisitedMountainAdapter(visited) { mt ->
                naverMap?.moveCamera(
                    CameraUpdate.scrollAndZoomTo(LatLng(mt.lat, mt.lng), 13.0)
                        .animate(CameraAnimation.Easing)
                )
            }
        }
    }

    private fun createMountainMarkerBitmap(): Bitmap {
        val size = 48
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#2E7D32")
            style = Paint.Style.FILL
        }

        val path = Path().apply {
            moveTo(size / 2f, 4f)
            lineTo(4f, size - 4f)
            lineTo(size - 4f, size - 4f)
            close()
        }
        canvas.drawPath(path, paint)

        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
        canvas.drawPath(path, borderPaint)

        return bitmap
    }

    private inner class VisitedMountainAdapter(
        private val items: List<VisitedMountain>,
        private val onClick: (VisitedMountain) -> Unit
    ) : RecyclerView.Adapter<VisitedMountainAdapter.VH>() {

        inner class VH(val binding: ItemVisitedMountainBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val b = ItemVisitedMountainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return VH(b)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val item = items[position]
            holder.binding.tvMtName.text = item.name
            holder.binding.tvMtInfo.text = "${item.alt}m | ${item.visitCount}회 방문"
            holder.itemView.setOnClickListener { onClick(item) }
        }

        override fun getItemCount() = items.size
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        markers.forEach { it.map = null }
        mapView?.onDestroy()
    }
}
