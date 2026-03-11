package com.onlysamhiking.app;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u000b\u001a\u00020\nH\u0016R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\r"}, d2 = {"Lcom/onlysamhiking/app/OnlySamHikingApp;", "Landroid/app/Application;", "()V", "database", "Lcom/onlysamhiking/app/data/db/HikingDatabase;", "getDatabase", "()Lcom/onlysamhiking/app/data/db/HikingDatabase;", "database$delegate", "Lkotlin/Lazy;", "createNotificationChannel", "", "onCreate", "Companion", "app_debug"})
public final class OnlySamHikingApp extends android.app.Application {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy database$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_TRACKING = "hiking_tracking";
    @org.jetbrains.annotations.NotNull()
    public static final com.onlysamhiking.app.OnlySamHikingApp.Companion Companion = null;
    
    public OnlySamHikingApp() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.onlysamhiking.app.data.db.HikingDatabase getDatabase() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    private final void createNotificationChannel() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/onlysamhiking/app/OnlySamHikingApp$Companion;", "", "()V", "CHANNEL_TRACKING", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}