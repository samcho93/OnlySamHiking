package com.onlysamhiking.app.ui.map;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0018\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH&J\b\u0010\u000b\u001a\u00020\u0003H&J\u0016\u0010\f\u001a\u00020\u00032\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0005H&J\u001e\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00030\u0013H&J\b\u0010\u0014\u001a\u00020\u0015H&J\"\u0010\u0016\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u0017\u001a\u00020\u0018H&J\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u001bH&J\b\u0010\u001c\u001a\u00020\u0003H&J\b\u0010\u001d\u001a\u00020\u0003H&J\b\u0010\u001e\u001a\u00020\u0003H&J\b\u0010\u001f\u001a\u00020\u0003H&J\u0010\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u0015H&J\u0010\u0010\"\u001a\u00020\u00032\u0006\u0010#\u001a\u00020\u0015H&J\u0018\u0010$\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH&\u00a8\u0006%"}, d2 = {"Lcom/onlysamhiking/app/ui/map/MapManagerInterface;", "", "addPhotoMarkers", "", "photos", "", "Lcom/onlysamhiking/app/data/model/HikingPhoto;", "addRoutePoint", "lat", "", "lng", "clearRoute", "drawRoute", "points", "Lcom/onlysamhiking/app/data/model/TrackPoint;", "initialize", "container", "Landroid/view/View;", "onReady", "Lkotlin/Function0;", "isHeadingMode", "", "moveCamera", "zoom", "", "moveCameraToLocation", "location", "Landroid/location/Location;", "onDestroy", "onPause", "onResume", "removeSamMarker", "setHeadingMode", "followHeading", "setMyLocationEnabled", "enabled", "updateSamMarker", "app_debug"})
public abstract interface MapManagerInterface {
    
    public abstract void initialize(@org.jetbrains.annotations.NotNull()
    android.view.View container, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onReady);
    
    public abstract void moveCamera(double lat, double lng, float zoom);
    
    public abstract void moveCameraToLocation(@org.jetbrains.annotations.NotNull()
    android.location.Location location);
    
    public abstract void addRoutePoint(double lat, double lng);
    
    public abstract void drawRoute(@org.jetbrains.annotations.NotNull()
    java.util.List<com.onlysamhiking.app.data.model.TrackPoint> points);
    
    public abstract void clearRoute();
    
    public abstract void setMyLocationEnabled(boolean enabled);
    
    public abstract void updateSamMarker(double lat, double lng);
    
    public abstract void removeSamMarker();
    
    public abstract void setHeadingMode(boolean followHeading);
    
    public abstract boolean isHeadingMode();
    
    public abstract void addPhotoMarkers(@org.jetbrains.annotations.NotNull()
    java.util.List<com.onlysamhiking.app.data.model.HikingPhoto> photos);
    
    public abstract void onResume();
    
    public abstract void onPause();
    
    public abstract void onDestroy();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}