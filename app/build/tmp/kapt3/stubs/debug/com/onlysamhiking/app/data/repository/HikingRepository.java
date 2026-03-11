package com.onlysamhiking.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0015J\u0016\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u001c0\u001bJ\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00140\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001eJ\u0012\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u001c0\u001bJ\u0018\u0010 \u001a\u0004\u0018\u00010\u00102\u0006\u0010!\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010\"\u001a\u00020#2\u0006\u0010!\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u001c\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00100\u001c2\u0006\u0010!\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u001a\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u001c0\u001b2\u0006\u0010!\u001a\u00020\u0018J\u0018\u0010&\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010\'\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u001b2\u0006\u0010\u0017\u001a\u00020\u0018J\u001c\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u001c2\u0006\u0010!\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u001a\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\u001c0\u001b2\u0006\u0010!\u001a\u00020\u0018J\u0012\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u001c0\u001bJ\u0016\u0010,\u001a\u00020\u00182\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010-\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0015J\u0016\u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u00020)H\u0086@\u00a2\u0006\u0002\u00100J\u001c\u00101\u001a\u00020\u000e2\f\u00102\u001a\b\u0012\u0004\u0012\u00020)0\u001cH\u0086@\u00a2\u0006\u0002\u00103J\u0016\u00104\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0015R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00065"}, d2 = {"Lcom/onlysamhiking/app/data/repository/HikingRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "db", "Lcom/onlysamhiking/app/data/db/HikingDatabase;", "photoDao", "Lcom/onlysamhiking/app/data/db/HikingPhotoDao;", "recordDao", "Lcom/onlysamhiking/app/data/db/HikingRecordDao;", "trackPointDao", "Lcom/onlysamhiking/app/data/db/TrackPointDao;", "deletePhoto", "", "photo", "Lcom/onlysamhiking/app/data/model/HikingPhoto;", "(Lcom/onlysamhiking/app/data/model/HikingPhoto;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteRecord", "record", "Lcom/onlysamhiking/app/data/model/HikingRecord;", "(Lcom/onlysamhiking/app/data/model/HikingRecord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteRecordById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllRecords", "Landroidx/lifecycle/LiveData;", "", "getAllRecordsList", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAppRecords", "getFirstPhoto", "recordId", "getPhotoCount", "", "getPhotos", "getPhotosLive", "getRecordById", "getRecordByIdLive", "getTrackPoints", "Lcom/onlysamhiking/app/data/model/TrackPoint;", "getTrackPointsLive", "getUserImportedRecords", "insertPhoto", "insertRecord", "insertTrackPoint", "point", "(Lcom/onlysamhiking/app/data/model/TrackPoint;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertTrackPoints", "points", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateRecord", "app_debug"})
public final class HikingRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.onlysamhiking.app.data.db.HikingDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.onlysamhiking.app.data.db.HikingRecordDao recordDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.onlysamhiking.app.data.db.TrackPointDao trackPointDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.onlysamhiking.app.data.db.HikingPhotoDao photoDao = null;
    
    public HikingRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.onlysamhiking.app.data.model.HikingRecord>> getAllRecords() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.onlysamhiking.app.data.model.HikingRecord>> getAppRecords() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.onlysamhiking.app.data.model.HikingRecord>> getUserImportedRecords() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllRecordsList(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.onlysamhiking.app.data.model.HikingRecord>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRecordById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.onlysamhiking.app.data.model.HikingRecord> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.onlysamhiking.app.data.model.HikingRecord> getRecordByIdLive(long id) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertRecord(@org.jetbrains.annotations.NotNull()
    com.onlysamhiking.app.data.model.HikingRecord record, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateRecord(@org.jetbrains.annotations.NotNull()
    com.onlysamhiking.app.data.model.HikingRecord record, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteRecord(@org.jetbrains.annotations.NotNull()
    com.onlysamhiking.app.data.model.HikingRecord record, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteRecordById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertTrackPoint(@org.jetbrains.annotations.NotNull()
    com.onlysamhiking.app.data.model.TrackPoint point, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertTrackPoints(@org.jetbrains.annotations.NotNull()
    java.util.List<com.onlysamhiking.app.data.model.TrackPoint> points, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getTrackPoints(long recordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.onlysamhiking.app.data.model.TrackPoint>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.onlysamhiking.app.data.model.TrackPoint>> getTrackPointsLive(long recordId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertPhoto(@org.jetbrains.annotations.NotNull()
    com.onlysamhiking.app.data.model.HikingPhoto photo, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deletePhoto(@org.jetbrains.annotations.NotNull()
    com.onlysamhiking.app.data.model.HikingPhoto photo, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getPhotos(long recordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.onlysamhiking.app.data.model.HikingPhoto>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.onlysamhiking.app.data.model.HikingPhoto>> getPhotosLive(long recordId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getPhotoCount(long recordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getFirstPhoto(long recordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.onlysamhiking.app.data.model.HikingPhoto> $completion) {
        return null;
    }
}