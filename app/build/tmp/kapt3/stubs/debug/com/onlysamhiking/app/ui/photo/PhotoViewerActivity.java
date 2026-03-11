package com.onlysamhiking.app.ui.photo;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0006H\u0002J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0012\u0010\u0015\u001a\u00020\u000f2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\b\u0010\u0018\u001a\u00020\u000fH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/onlysamhiking/app/ui/photo/PhotoViewerActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/onlysamhiking/app/databinding/ActivityPhotoViewerBinding;", "currentPhotoPath", "", "record", "Lcom/onlysamhiking/app/data/model/HikingRecord;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "trackPoints", "", "Lcom/onlysamhiking/app/data/model/TrackPoint;", "addWatermark", "", "loadPhoto", "path", "loadRecord", "recordId", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "Companion", "app_debug"})
public final class PhotoViewerActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.onlysamhiking.app.databinding.ActivityPhotoViewerBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String currentPhotoPath;
    @org.jetbrains.annotations.Nullable()
    private com.onlysamhiking.app.data.model.HikingRecord record;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.onlysamhiking.app.data.model.TrackPoint> trackPoints;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_PHOTO_PATH = "photo_path";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_RECORD_ID = "record_id";
    @org.jetbrains.annotations.NotNull()
    public static final com.onlysamhiking.app.ui.photo.PhotoViewerActivity.Companion Companion = null;
    
    public PhotoViewerActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadPhoto(java.lang.String path) {
    }
    
    private final void loadRecord(long recordId) {
    }
    
    private final void addWatermark() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/onlysamhiking/app/ui/photo/PhotoViewerActivity$Companion;", "", "()V", "EXTRA_PHOTO_PATH", "", "EXTRA_RECORD_ID", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}