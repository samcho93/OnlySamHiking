package com.onlysamhiking.app.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0014B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bJ\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0002J&\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bJ$\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bJ \u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0004H\u0002\u00a8\u0006\u0015"}, d2 = {"Lcom/onlysamhiking/app/util/GpxExporter;", "", "()V", "buildGpxContent", "", "record", "Lcom/onlysamhiking/app/data/model/HikingRecord;", "trackPoints", "", "Lcom/onlysamhiking/app/data/model/TrackPoint;", "escapeXml", "text", "exportAndGetShareIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "exportToGpx", "", "saveToDownloads", "content", "ExportResult", "app_debug"})
public final class GpxExporter {
    @org.jetbrains.annotations.NotNull()
    public static final com.onlysamhiking.app.util.GpxExporter INSTANCE = null;
    
    private GpxExporter() {
        super();
    }
    
    /**
     * GPX 콘텐츠 생성
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String buildGpxContent(@org.jetbrains.annotations.NotNull()
    com.onlysamhiking.app.data.model.HikingRecord record, @org.jetbrains.annotations.NotNull()
    java.util.List<com.onlysamhiking.app.data.model.TrackPoint> trackPoints) {
        return null;
    }
    
    /**
     * GPX를 Downloads에 저장
     */
    public final boolean exportToGpx(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.onlysamhiking.app.data.model.HikingRecord record, @org.jetbrains.annotations.NotNull()
    java.util.List<com.onlysamhiking.app.data.model.TrackPoint> trackPoints) {
        return false;
    }
    
    /**
     * GPX 파일을 앱 내부 저장소에 저장 후 공유 Intent 생성
     */
    @org.jetbrains.annotations.Nullable()
    public final android.content.Intent exportAndGetShareIntent(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.onlysamhiking.app.data.model.HikingRecord record, @org.jetbrains.annotations.NotNull()
    java.util.List<com.onlysamhiking.app.data.model.TrackPoint> trackPoints) {
        return null;
    }
    
    private final boolean saveToDownloads(android.content.Context context, com.onlysamhiking.app.data.model.HikingRecord record, java.lang.String content) {
        return false;
    }
    
    private final java.lang.String escapeXml(java.lang.String text) {
        return null;
    }
    
    /**
     * GPX 내보내기 결과
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J+\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00032\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0007H\u00d6\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0018"}, d2 = {"Lcom/onlysamhiking/app/util/GpxExporter$ExportResult;", "", "success", "", "gpxUri", "Landroid/net/Uri;", "gpxFilePath", "", "(ZLandroid/net/Uri;Ljava/lang/String;)V", "getGpxFilePath", "()Ljava/lang/String;", "getGpxUri", "()Landroid/net/Uri;", "getSuccess", "()Z", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class ExportResult {
        private final boolean success = false;
        @org.jetbrains.annotations.Nullable()
        private final android.net.Uri gpxUri = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String gpxFilePath = null;
        
        public ExportResult(boolean success, @org.jetbrains.annotations.Nullable()
        android.net.Uri gpxUri, @org.jetbrains.annotations.Nullable()
        java.lang.String gpxFilePath) {
            super();
        }
        
        public final boolean getSuccess() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final android.net.Uri getGpxUri() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getGpxFilePath() {
            return null;
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final android.net.Uri component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.onlysamhiking.app.util.GpxExporter.ExportResult copy(boolean success, @org.jetbrains.annotations.Nullable()
        android.net.Uri gpxUri, @org.jetbrains.annotations.Nullable()
        java.lang.String gpxFilePath) {
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