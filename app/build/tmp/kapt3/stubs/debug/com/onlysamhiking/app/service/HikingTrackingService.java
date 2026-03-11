package com.onlysamhiking.app.service;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00a6\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u000f\u0018\u0000 U2\u00020\u00012\u00020\u0002:\u0002UVB\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010:\u001a\u00020%2\u0006\u0010;\u001a\u00020\u001aH\u0002J\u0010\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\u0006H\u0002J\u0006\u0010?\u001a\u00020$J\u0012\u0010@\u001a\u00020A2\b\u0010B\u001a\u0004\u0018\u00010CH\u0016J\b\u0010D\u001a\u00020%H\u0016J\b\u0010E\u001a\u00020%H\u0016J\u0010\u0010F\u001a\u00020%2\u0006\u0010G\u001a\u00020HH\u0016J\"\u0010I\u001a\u00020H2\b\u0010B\u001a\u0004\u0018\u00010C2\u0006\u0010J\u001a\u00020H2\u0006\u0010K\u001a\u00020HH\u0016J\u0006\u0010L\u001a\u00020%J\u0010\u0010M\u001a\u00020%2\u0006\u0010;\u001a\u00020\u001aH\u0002J\u0006\u0010N\u001a\u00020%J\u0010\u0010O\u001a\u00020%2\u0006\u0010P\u001a\u00020+H\u0002J\b\u0010Q\u001a\u00020%H\u0002J\u0006\u0010R\u001a\u00020%J\u0006\u0010S\u001a\u00020%J\u0010\u0010T\u001a\u00020%2\u0006\u0010>\u001a\u00020\u0006H\u0002R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00060\bR\u00020\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0016\u001a\u00020\u00152\u0006\u0010\t\u001a\u00020\u0015@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001e\u0010\u0018\u001a\u00020\u00152\u0006\u0010\t\u001a\u00020\u0015@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R.\u0010\"\u001a\u0016\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%\u0018\u00010#X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\'\"\u0004\b(\u0010)R.\u0010*\u001a\u0016\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020%\u0018\u00010#X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\'\"\u0004\b-\u0010)R\u000e\u0010.\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u00104\u001a\b\u0012\u0004\u0012\u00020605X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u00107\u001a\u0004\u0018\u000108X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006W"}, d2 = {"Lcom/onlysamhiking/app/service/HikingTrackingService;", "Landroid/app/Service;", "Landroid/speech/tts/TextToSpeech$OnInitListener;", "()V", "alertedPeaks", "", "", "binder", "Lcom/onlysamhiking/app/service/HikingTrackingService$TrackingBinder;", "<set-?>", "", "currentRecordId", "getCurrentRecordId", "()J", "elevationGain", "", "elevationLoss", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "hikingRepository", "Lcom/onlysamhiking/app/data/repository/HikingRepository;", "", "isPaused", "()Z", "isRecording", "lastLocation", "Landroid/location/Location;", "locationCallback", "Lcom/google/android/gms/location/LocationCallback;", "maxAltitude", "maxSpeed", "minAltitude", "mountainRepository", "Lcom/onlysamhiking/app/data/repository/MountainRepository;", "onLocationUpdate", "Lkotlin/Function2;", "Lcom/onlysamhiking/app/service/TrackingStats;", "", "getOnLocationUpdate", "()Lkotlin/jvm/functions/Function2;", "setOnLocationUpdate", "(Lkotlin/jvm/functions/Function2;)V", "onPeakNearby", "Lcom/onlysamhiking/app/data/model/Mountain;", "getOnPeakNearby", "setOnPeakNearby", "pauseStartTime", "pausedDuration", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "startTime", "totalDistance", "trackPoints", "", "Lcom/onlysamhiking/app/data/model/TrackPoint;", "tts", "Landroid/speech/tts/TextToSpeech;", "ttsReady", "checkNearbyPeaks", "location", "createNotification", "Landroid/app/Notification;", "content", "getTrackingStats", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onInit", "status", "", "onStartCommand", "flags", "startId", "pauseRecording", "processLocation", "resumeRecording", "speakPeakAlert", "mountain", "startLocationUpdates", "startRecording", "stopRecording", "updateNotification", "Companion", "TrackingBinder", "app_debug"})
public final class HikingTrackingService extends android.app.Service implements android.speech.tts.TextToSpeech.OnInitListener {
    @org.jetbrains.annotations.NotNull()
    private final com.onlysamhiking.app.service.HikingTrackingService.TrackingBinder binder = null;
    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient;
    private com.onlysamhiking.app.data.repository.HikingRepository hikingRepository;
    private com.onlysamhiking.app.data.repository.MountainRepository mountainRepository;
    @org.jetbrains.annotations.Nullable()
    private android.speech.tts.TextToSpeech tts;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    private boolean isRecording = false;
    private boolean isPaused = false;
    private long currentRecordId = -1L;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.onlysamhiking.app.data.model.TrackPoint> trackPoints = null;
    @org.jetbrains.annotations.Nullable()
    private android.location.Location lastLocation;
    private double totalDistance = 0.0;
    private double maxAltitude = 4.9E-324;
    private double minAltitude = 1.7976931348623157E308;
    private double elevationGain = 0.0;
    private double elevationLoss = 0.0;
    private double maxSpeed = 0.0;
    private long startTime = 0L;
    private long pausedDuration = 0L;
    private long pauseStartTime = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.String> alertedPeaks = null;
    private boolean ttsReady = false;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function2<? super android.location.Location, ? super com.onlysamhiking.app.service.TrackingStats, kotlin.Unit> onLocationUpdate;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function2<? super com.onlysamhiking.app.data.model.Mountain, ? super java.lang.Double, kotlin.Unit> onPeakNearby;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.location.LocationCallback locationCallback = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_START = "com.onlysamhiking.ACTION_START";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_PAUSE = "com.onlysamhiking.ACTION_PAUSE";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_RESUME = "com.onlysamhiking.ACTION_RESUME";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_STOP = "com.onlysamhiking.ACTION_STOP";
    public static final int NOTIFICATION_ID = 1001;
    @org.jetbrains.annotations.NotNull()
    public static final com.onlysamhiking.app.service.HikingTrackingService.Companion Companion = null;
    
    public HikingTrackingService() {
        super();
    }
    
    public final boolean isRecording() {
        return false;
    }
    
    public final boolean isPaused() {
        return false;
    }
    
    public final long getCurrentRecordId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final kotlin.jvm.functions.Function2<android.location.Location, com.onlysamhiking.app.service.TrackingStats, kotlin.Unit> getOnLocationUpdate() {
        return null;
    }
    
    public final void setOnLocationUpdate(@org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function2<? super android.location.Location, ? super com.onlysamhiking.app.service.TrackingStats, kotlin.Unit> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final kotlin.jvm.functions.Function2<com.onlysamhiking.app.data.model.Mountain, java.lang.Double, kotlin.Unit> getOnPeakNearby() {
        return null;
    }
    
    public final void setOnPeakNearby(@org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function2<? super com.onlysamhiking.app.data.model.Mountain, ? super java.lang.Double, kotlin.Unit> p0) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public void onInit(int status) {
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    public final void startRecording() {
    }
    
    public final void pauseRecording() {
    }
    
    public final void resumeRecording() {
    }
    
    public final void stopRecording() {
    }
    
    @kotlin.Suppress(names = {"MissingPermission"})
    private final void startLocationUpdates() {
    }
    
    private final void processLocation(android.location.Location location) {
    }
    
    private final void checkNearbyPeaks(android.location.Location location) {
    }
    
    private final void speakPeakAlert(com.onlysamhiking.app.data.model.Mountain mountain) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.onlysamhiking.app.service.TrackingStats getTrackingStats() {
        return null;
    }
    
    private final android.app.Notification createNotification(java.lang.String content) {
        return null;
    }
    
    private final void updateNotification(java.lang.String content) {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/onlysamhiking/app/service/HikingTrackingService$Companion;", "", "()V", "ACTION_PAUSE", "", "ACTION_RESUME", "ACTION_START", "ACTION_STOP", "NOTIFICATION_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/onlysamhiking/app/service/HikingTrackingService$TrackingBinder;", "Landroid/os/Binder;", "(Lcom/onlysamhiking/app/service/HikingTrackingService;)V", "getService", "Lcom/onlysamhiking/app/service/HikingTrackingService;", "app_debug"})
    public final class TrackingBinder extends android.os.Binder {
        
        public TrackingBinder() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.onlysamhiking.app.service.HikingTrackingService getService() {
            return null;
        }
    }
}