package com.onlysamhiking.app.util;

/**
 * 사진 파일의 EXIF 데이터에서 GPS 좌표를 읽는 유틸리티
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0013B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bJ\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\f\u001a\u00020\rJ\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012\u00a8\u0006\u0014"}, d2 = {"Lcom/onlysamhiking/app/util/ExifHelper;", "", "()V", "extractGps", "Lcom/onlysamhiking/app/util/ExifHelper$GpsCoordinates;", "exif", "Landroidx/exifinterface/media/ExifInterface;", "fillMissingCoordinates", "", "Lcom/onlysamhiking/app/data/model/HikingPhoto;", "photos", "getGpsFromFile", "filePath", "", "getGpsFromUri", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "GpsCoordinates", "app_debug"})
public final class ExifHelper {
    @org.jetbrains.annotations.NotNull()
    public static final com.onlysamhiking.app.util.ExifHelper INSTANCE = null;
    
    private ExifHelper() {
        super();
    }
    
    /**
     * 파일 경로에서 EXIF GPS 좌표 읽기
     */
    @org.jetbrains.annotations.Nullable()
    public final com.onlysamhiking.app.util.ExifHelper.GpsCoordinates getGpsFromFile(@org.jetbrains.annotations.NotNull()
    java.lang.String filePath) {
        return null;
    }
    
    /**
     * Uri에서 EXIF GPS 좌표 읽기
     */
    @org.jetbrains.annotations.Nullable()
    public final com.onlysamhiking.app.util.ExifHelper.GpsCoordinates getGpsFromUri(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
        return null;
    }
    
    /**
     * ExifInterface에서 GPS 좌표 추출
     */
    private final com.onlysamhiking.app.util.ExifHelper.GpsCoordinates extractGps(androidx.exifinterface.media.ExifInterface exif) {
        return null;
    }
    
    /**
     * 사진 리스트에서 (0,0) 좌표인 사진들의 EXIF GPS 정보를 읽어 좌표 보정
     * 원본 리스트를 수정하지 않고, 보정된 새 리스트를 반환
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.onlysamhiking.app.data.model.HikingPhoto> fillMissingCoordinates(@org.jetbrains.annotations.NotNull()
    java.util.List<com.onlysamhiking.app.data.model.HikingPhoto> photos) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b\u00a8\u0006\u0016"}, d2 = {"Lcom/onlysamhiking/app/util/ExifHelper$GpsCoordinates;", "", "latitude", "", "longitude", "altitude", "(DDD)V", "getAltitude", "()D", "getLatitude", "getLongitude", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class GpsCoordinates {
        private final double latitude = 0.0;
        private final double longitude = 0.0;
        private final double altitude = 0.0;
        
        public GpsCoordinates(double latitude, double longitude, double altitude) {
            super();
        }
        
        public final double getLatitude() {
            return 0.0;
        }
        
        public final double getLongitude() {
            return 0.0;
        }
        
        public final double getAltitude() {
            return 0.0;
        }
        
        public final double component1() {
            return 0.0;
        }
        
        public final double component2() {
            return 0.0;
        }
        
        public final double component3() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.onlysamhiking.app.util.ExifHelper.GpsCoordinates copy(double latitude, double longitude, double altitude) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}