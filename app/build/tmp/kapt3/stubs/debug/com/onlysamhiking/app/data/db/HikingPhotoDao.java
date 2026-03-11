package com.onlysamhiking.app.data.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e2\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\u00102\u0006\u0010\b\u001a\u00020\tH\'J\u0016\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0012"}, d2 = {"Lcom/onlysamhiking/app/data/db/HikingPhotoDao;", "", "delete", "", "photo", "Lcom/onlysamhiking/app/data/model/HikingPhoto;", "(Lcom/onlysamhiking/app/data/model/HikingPhoto;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFirstPhoto", "recordId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPhotoCount", "", "getPhotosByRecordId", "", "getPhotosByRecordIdLive", "Landroidx/lifecycle/LiveData;", "insert", "app_debug"})
@androidx.room.Dao()
public abstract interface HikingPhotoDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.onlysamhiking.app.data.model.HikingPhoto photo, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.onlysamhiking.app.data.model.HikingPhoto photo, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM hiking_photos WHERE recordId = :recordId ORDER BY timestamp ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPhotosByRecordId(long recordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.onlysamhiking.app.data.model.HikingPhoto>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM hiking_photos WHERE recordId = :recordId ORDER BY timestamp ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.onlysamhiking.app.data.model.HikingPhoto>> getPhotosByRecordIdLive(long recordId);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM hiking_photos WHERE recordId = :recordId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPhotoCount(long recordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM hiking_photos WHERE recordId = :recordId ORDER BY timestamp ASC LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFirstPhoto(long recordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.onlysamhiking.app.data.model.HikingPhoto> $completion);
}