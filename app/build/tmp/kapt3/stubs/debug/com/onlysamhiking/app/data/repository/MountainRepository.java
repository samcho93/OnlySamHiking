package com.onlysamhiking.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\b\u001a\u0004\u0018\u00010\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0006J2\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r0\f0\u00062\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\b\b\u0002\u0010\u0010\u001a\u00020\rJ2\u0010\u0011\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r0\f0\u00062\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\b\b\u0002\u0010\u0010\u001a\u00020\rJ\u0018\u0010\u0012\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rJ\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rJ\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/onlysamhiking/app/data/repository/MountainRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mountains", "", "Lcom/onlysamhiking/app/data/model/Mountain;", "findMountainAlongRoute", "points", "Lcom/onlysamhiking/app/data/model/TrackPoint;", "findMountainsInRadius", "Lkotlin/Pair;", "", "lat", "lng", "radiusMeters", "findNearbyPeaks", "findNearestMountain", "getMountainName", "", "loadMountains", "app_debug"})
public final class MountainRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private java.util.List<com.onlysamhiking.app.data.model.Mountain> mountains;
    
    public MountainRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.onlysamhiking.app.data.model.Mountain> loadMountains() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.onlysamhiking.app.data.model.Mountain findNearestMountain(double lat, double lng) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<com.onlysamhiking.app.data.model.Mountain, java.lang.Double>> findNearbyPeaks(double lat, double lng, double radiusMeters) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<com.onlysamhiking.app.data.model.Mountain, java.lang.Double>> findMountainsInRadius(double lat, double lng, double radiusMeters) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMountainName(double lat, double lng) {
        return null;
    }
    
    /**
     * 경로(트랙포인트)를 따라 100m 이내에 있는 산을 찾음
     * 없으면 null 반환 (호출자가 날짜 형식으로 대체)
     */
    @org.jetbrains.annotations.Nullable()
    public final com.onlysamhiking.app.data.model.Mountain findMountainAlongRoute(@org.jetbrains.annotations.NotNull()
    java.util.List<com.onlysamhiking.app.data.model.TrackPoint> points) {
        return null;
    }
}