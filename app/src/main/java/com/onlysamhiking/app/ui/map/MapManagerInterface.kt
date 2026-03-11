package com.onlysamhiking.app.ui.map

import android.location.Location
import android.view.View
import com.onlysamhiking.app.data.model.HikingPhoto
import com.onlysamhiking.app.data.model.TrackPoint

interface MapManagerInterface {
    fun initialize(container: View, onReady: () -> Unit)
    fun moveCamera(lat: Double, lng: Double, zoom: Float = 15f)
    fun moveCameraToLocation(location: Location)
    fun addRoutePoint(lat: Double, lng: Double)
    fun drawRoute(points: List<TrackPoint>)
    fun clearRoute()
    fun setMyLocationEnabled(enabled: Boolean)

    // SAM marker
    fun updateSamMarker(lat: Double, lng: Double)
    fun removeSamMarker()

    // Heading toggle
    fun setHeadingMode(followHeading: Boolean)
    fun isHeadingMode(): Boolean

    // Photo markers (camera icons on detail map)
    fun addPhotoMarkers(photos: List<HikingPhoto>)

    fun onResume()
    fun onPause()
    fun onDestroy()
}
