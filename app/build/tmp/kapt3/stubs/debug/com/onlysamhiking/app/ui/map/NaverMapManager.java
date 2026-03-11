package com.onlysamhiking.app.ui.map;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0016\u0010\u001b\u001a\u00020\u00112\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0016J\u0018\u0010\u001f\u001a\u00020\u00112\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!H\u0016J \u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020\'H\u0002J\b\u0010)\u001a\u00020\u0011H\u0002J\b\u0010*\u001a\u00020\u0011H\u0016J\b\u0010+\u001a\u00020$H\u0002J\b\u0010,\u001a\u00020$H\u0002J\u0012\u0010-\u001a\u0004\u0018\u00010$2\u0006\u0010.\u001a\u00020/H\u0002J\u0016\u00100\u001a\u00020\u00112\f\u00101\u001a\b\u0012\u0004\u0012\u0002020\u001dH\u0016J\u001e\u00103\u001a\u00020\u00112\u0006\u00104\u001a\u0002052\f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0016J\b\u00107\u001a\u00020\u0007H\u0016J \u00108\u001a\u00020\u00112\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\u0006\u00109\u001a\u00020:H\u0016J\u0010\u0010;\u001a\u00020\u00112\u0006\u0010<\u001a\u00020=H\u0016J\b\u0010>\u001a\u00020\u0011H\u0016J\u0010\u0010?\u001a\u00020\u00112\u0006\u0010@\u001a\u00020\u000eH\u0016J\b\u0010A\u001a\u00020\u0011H\u0016J\b\u0010B\u001a\u00020\u0011H\u0016J\b\u0010C\u001a\u00020\u0011H\u0016J\u0010\u0010D\u001a\u00020\u00112\u0006\u0010E\u001a\u00020\u0007H\u0016J\u0010\u0010F\u001a\u00020\u00112\u0006\u0010G\u001a\u00020\u0007H\u0016J\b\u0010H\u001a\u00020\u0011H\u0002J\u0018\u0010I\u001a\u00020\u00112\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!H\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006J"}, d2 = {"Lcom/onlysamhiking/app/ui/map/NaverMapManager;", "Lcom/onlysamhiking/app/ui/map/MapManagerInterface;", "Lcom/naver/maps/map/OnMapReadyCallback;", "()V", "fallbackMarkerIcon", "Lcom/naver/maps/map/overlay/OverlayImage;", "headingMode", "", "isFollowingUser", "locationSource", "Lcom/naver/maps/map/util/FusedLocationSource;", "mapView", "Lcom/naver/maps/map/MapView;", "naverMap", "Lcom/naver/maps/map/NaverMap;", "onReadyCallback", "Lkotlin/Function0;", "", "pathOverlay", "Lcom/naver/maps/map/overlay/PathOverlay;", "photoMarkers", "", "Lcom/naver/maps/map/overlay/Marker;", "routeCoords", "Lcom/naver/maps/geometry/LatLng;", "samMarker", "samMarkerIcon", "addPhotoMarkers", "photos", "", "Lcom/onlysamhiking/app/data/model/HikingPhoto;", "addRoutePoint", "lat", "", "lng", "centerCropBitmap", "Landroid/graphics/Bitmap;", "source", "targetW", "", "targetH", "clearPhotoMarkers", "clearRoute", "createCameraMarkerBitmap", "createSamMarkerBitmap", "createThumbnailMarkerBitmap", "filePath", "", "drawRoute", "points", "Lcom/onlysamhiking/app/data/model/TrackPoint;", "initialize", "container", "Landroid/view/View;", "onReady", "isHeadingMode", "moveCamera", "zoom", "", "moveCameraToLocation", "location", "Landroid/location/Location;", "onDestroy", "onMapReady", "map", "onPause", "onResume", "removeSamMarker", "setHeadingMode", "followHeading", "setMyLocationEnabled", "enabled", "updatePathOverlay", "updateSamMarker", "app_debug"})
public final class NaverMapManager implements com.onlysamhiking.app.ui.map.MapManagerInterface, com.naver.maps.map.OnMapReadyCallback {
    @org.jetbrains.annotations.Nullable()
    private com.naver.maps.map.MapView mapView;
    @org.jetbrains.annotations.Nullable()
    private com.naver.maps.map.NaverMap naverMap;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function0<kotlin.Unit> onReadyCallback;
    @org.jetbrains.annotations.Nullable()
    private com.naver.maps.map.util.FusedLocationSource locationSource;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.naver.maps.geometry.LatLng> routeCoords = null;
    @org.jetbrains.annotations.Nullable()
    private com.naver.maps.map.overlay.PathOverlay pathOverlay;
    private boolean isFollowingUser = true;
    private boolean headingMode = false;
    @org.jetbrains.annotations.Nullable()
    private com.naver.maps.map.overlay.Marker samMarker;
    @org.jetbrains.annotations.Nullable()
    private com.naver.maps.map.overlay.OverlayImage samMarkerIcon;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.naver.maps.map.overlay.Marker> photoMarkers = null;
    @org.jetbrains.annotations.Nullable()
    private com.naver.maps.map.overlay.OverlayImage fallbackMarkerIcon;
    
    public NaverMapManager() {
        super();
    }
    
    @java.lang.Override()
    public void initialize(@org.jetbrains.annotations.NotNull()
    android.view.View container, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onReady) {
    }
    
    @java.lang.Override()
    public void onMapReady(@org.jetbrains.annotations.NotNull()
    com.naver.maps.map.NaverMap map) {
    }
    
    @java.lang.Override()
    public void moveCamera(double lat, double lng, float zoom) {
    }
    
    @java.lang.Override()
    public void moveCameraToLocation(@org.jetbrains.annotations.NotNull()
    android.location.Location location) {
    }
    
    @java.lang.Override()
    public void addRoutePoint(double lat, double lng) {
    }
    
    @java.lang.Override()
    public void drawRoute(@org.jetbrains.annotations.NotNull()
    java.util.List<com.onlysamhiking.app.data.model.TrackPoint> points) {
    }
    
    private final void updatePathOverlay() {
    }
    
    @java.lang.Override()
    public void clearRoute() {
    }
    
    @java.lang.Override()
    @kotlin.Suppress(names = {"MissingPermission"})
    public void setMyLocationEnabled(boolean enabled) {
    }
    
    @java.lang.Override()
    public void updateSamMarker(double lat, double lng) {
    }
    
    @java.lang.Override()
    public void removeSamMarker() {
    }
    
    @java.lang.Override()
    public void setHeadingMode(boolean followHeading) {
    }
    
    @java.lang.Override()
    public boolean isHeadingMode() {
        return false;
    }
    
    @java.lang.Override()
    public void addPhotoMarkers(@org.jetbrains.annotations.NotNull()
    java.util.List<com.onlysamhiking.app.data.model.HikingPhoto> photos) {
    }
    
    private final void clearPhotoMarkers() {
    }
    
    private final android.graphics.Bitmap createSamMarkerBitmap() {
        return null;
    }
    
    /**
     * 사진 파일에서 썸네일을 읽어 핀 모양 마커 비트맵 생성
     */
    private final android.graphics.Bitmap createThumbnailMarkerBitmap(java.lang.String filePath) {
        return null;
    }
    
    /**
     * 비트맵을 중앙 기준으로 크롭
     */
    private final android.graphics.Bitmap centerCropBitmap(android.graphics.Bitmap source, int targetW, int targetH) {
        return null;
    }
    
    private final android.graphics.Bitmap createCameraMarkerBitmap() {
        return null;
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
}