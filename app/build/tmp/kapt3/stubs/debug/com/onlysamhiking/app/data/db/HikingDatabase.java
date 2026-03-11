package com.onlysamhiking.app.data.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\n"}, d2 = {"Lcom/onlysamhiking/app/data/db/HikingDatabase;", "Landroidx/room/RoomDatabase;", "()V", "hikingPhotoDao", "Lcom/onlysamhiking/app/data/db/HikingPhotoDao;", "hikingRecordDao", "Lcom/onlysamhiking/app/data/db/HikingRecordDao;", "trackPointDao", "Lcom/onlysamhiking/app/data/db/TrackPointDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.onlysamhiking.app.data.model.HikingRecord.class, com.onlysamhiking.app.data.model.TrackPoint.class, com.onlysamhiking.app.data.model.HikingPhoto.class}, version = 3, exportSchema = false)
public abstract class HikingDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.onlysamhiking.app.data.db.HikingDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.room.migration.Migration MIGRATION_1_2 = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.room.migration.Migration MIGRATION_2_3 = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.onlysamhiking.app.data.db.HikingDatabase.Companion Companion = null;
    
    public HikingDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.onlysamhiking.app.data.db.HikingRecordDao hikingRecordDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.onlysamhiking.app.data.db.TrackPointDao trackPointDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.onlysamhiking.app.data.db.HikingPhotoDao hikingPhotoDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/onlysamhiking/app/data/db/HikingDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/onlysamhiking/app/data/db/HikingDatabase;", "MIGRATION_1_2", "Landroidx/room/migration/Migration;", "MIGRATION_2_3", "getDatabase", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.onlysamhiking.app.data.db.HikingDatabase getDatabase(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}