package com.onlysamhiking.app.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J.\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00142\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016J0\u0010\u0018\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00142\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016J6\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u001dH\u0002J0\u0010 \u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\u001dH\u0002J8\u0010\"\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\r2\u0006\u0010$\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020\'H\u0002J\"\u0010)\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014H\u0002\u00a8\u0006*"}, d2 = {"Lcom/onlysamhiking/app/util/WatermarkHelper;", "", "()V", "applyOrientation", "Landroid/graphics/Bitmap;", "bitmap", "orientation", "", "copyExifData", "", "sourceExif", "Landroidx/exifinterface/media/ExifInterface;", "destPath", "", "createWatermarkedPhoto", "", "context", "Landroid/content/Context;", "photoPath", "record", "Lcom/onlysamhiking/app/data/model/HikingRecord;", "trackPoints", "", "Lcom/onlysamhiking/app/data/model/TrackPoint;", "createWatermarkedPhotoAndGetPath", "drawAltitudeGraph", "canvas", "Landroid/graphics/Canvas;", "width", "", "height", "padding", "drawAppIcon", "textSize", "drawOutlinedText", "text", "x", "y", "outlinePaint", "Landroid/graphics/Paint;", "fillPaint", "saveWatermarkedBitmapAndGetPath", "app_debug"})
public final class WatermarkHelper {
    @org.jetbrains.annotations.NotNull()
    public static final com.onlysamhiking.app.util.WatermarkHelper INSTANCE = null;
    
    private WatermarkHelper() {
        super();
    }
    
    public final boolean createWatermarkedPhoto(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String photoPath, @org.jetbrains.annotations.NotNull()
    com.onlysamhiking.app.data.model.HikingRecord record, @org.jetbrains.annotations.NotNull()
    java.util.List<com.onlysamhiking.app.data.model.TrackPoint> trackPoints) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String createWatermarkedPhotoAndGetPath(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String photoPath, @org.jetbrains.annotations.NotNull()
    com.onlysamhiking.app.data.model.HikingRecord record, @org.jetbrains.annotations.NotNull()
    java.util.List<com.onlysamhiking.app.data.model.TrackPoint> trackPoints) {
        return null;
    }
    
    /**
     * 외곽선(검정) + 채우기(흰색) 텍스트 그리기
     */
    private final void drawOutlinedText(android.graphics.Canvas canvas, java.lang.String text, float x, float y, android.graphics.Paint outlinePaint, android.graphics.Paint fillPaint) {
    }
    
    /**
     * 오른쪽 상단에 앱 아이콘 그리기
     */
    private final void drawAppIcon(android.content.Context context, android.graphics.Canvas canvas, float width, float padding, float textSize) {
    }
    
    /**
     * EXIF 방향에 따라 비트맵 회전/반전
     */
    private final android.graphics.Bitmap applyOrientation(android.graphics.Bitmap bitmap, int orientation) {
        return null;
    }
    
    /**
     * 오른쪽 하단에 미니 고도 그래프 그리기
     */
    private final void drawAltitudeGraph(android.graphics.Canvas canvas, java.util.List<com.onlysamhiking.app.data.model.TrackPoint> trackPoints, float width, float height, float padding) {
    }
    
    /**
     * 원본 EXIF GPS/날짜 데이터를 워터마크 파일에 복사
     */
    private final void copyExifData(androidx.exifinterface.media.ExifInterface sourceExif, java.lang.String destPath) {
    }
    
    private final java.lang.String saveWatermarkedBitmapAndGetPath(android.content.Context context, android.graphics.Bitmap bitmap, com.onlysamhiking.app.data.model.HikingRecord record) {
        return null;
    }
}