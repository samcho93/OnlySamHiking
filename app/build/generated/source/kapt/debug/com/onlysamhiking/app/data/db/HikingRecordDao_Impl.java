package com.onlysamhiking.app.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.onlysamhiking.app.data.model.HikingRecord;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class HikingRecordDao_Impl implements HikingRecordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HikingRecord> __insertionAdapterOfHikingRecord;

  private final EntityDeletionOrUpdateAdapter<HikingRecord> __deletionAdapterOfHikingRecord;

  private final EntityDeletionOrUpdateAdapter<HikingRecord> __updateAdapterOfHikingRecord;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public HikingRecordDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHikingRecord = new EntityInsertionAdapter<HikingRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `hiking_records` (`id`,`mountainName`,`startTime`,`endTime`,`distance`,`maxAltitude`,`minAltitude`,`elevationGain`,`elevationLoss`,`avgSpeed`,`maxSpeed`,`calories`,`startLat`,`startLng`,`endLat`,`endLng`,`memo`,`isUserImported`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HikingRecord entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getMountainName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getMountainName());
        }
        statement.bindLong(3, entity.getStartTime());
        statement.bindLong(4, entity.getEndTime());
        statement.bindDouble(5, entity.getDistance());
        statement.bindDouble(6, entity.getMaxAltitude());
        statement.bindDouble(7, entity.getMinAltitude());
        statement.bindDouble(8, entity.getElevationGain());
        statement.bindDouble(9, entity.getElevationLoss());
        statement.bindDouble(10, entity.getAvgSpeed());
        statement.bindDouble(11, entity.getMaxSpeed());
        statement.bindLong(12, entity.getCalories());
        statement.bindDouble(13, entity.getStartLat());
        statement.bindDouble(14, entity.getStartLng());
        statement.bindDouble(15, entity.getEndLat());
        statement.bindDouble(16, entity.getEndLng());
        if (entity.getMemo() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getMemo());
        }
        final int _tmp = entity.isUserImported() ? 1 : 0;
        statement.bindLong(18, _tmp);
      }
    };
    this.__deletionAdapterOfHikingRecord = new EntityDeletionOrUpdateAdapter<HikingRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `hiking_records` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HikingRecord entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfHikingRecord = new EntityDeletionOrUpdateAdapter<HikingRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `hiking_records` SET `id` = ?,`mountainName` = ?,`startTime` = ?,`endTime` = ?,`distance` = ?,`maxAltitude` = ?,`minAltitude` = ?,`elevationGain` = ?,`elevationLoss` = ?,`avgSpeed` = ?,`maxSpeed` = ?,`calories` = ?,`startLat` = ?,`startLng` = ?,`endLat` = ?,`endLng` = ?,`memo` = ?,`isUserImported` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HikingRecord entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getMountainName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getMountainName());
        }
        statement.bindLong(3, entity.getStartTime());
        statement.bindLong(4, entity.getEndTime());
        statement.bindDouble(5, entity.getDistance());
        statement.bindDouble(6, entity.getMaxAltitude());
        statement.bindDouble(7, entity.getMinAltitude());
        statement.bindDouble(8, entity.getElevationGain());
        statement.bindDouble(9, entity.getElevationLoss());
        statement.bindDouble(10, entity.getAvgSpeed());
        statement.bindDouble(11, entity.getMaxSpeed());
        statement.bindLong(12, entity.getCalories());
        statement.bindDouble(13, entity.getStartLat());
        statement.bindDouble(14, entity.getStartLng());
        statement.bindDouble(15, entity.getEndLat());
        statement.bindDouble(16, entity.getEndLng());
        if (entity.getMemo() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getMemo());
        }
        final int _tmp = entity.isUserImported() ? 1 : 0;
        statement.bindLong(18, _tmp);
        statement.bindLong(19, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM hiking_records WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final HikingRecord record, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfHikingRecord.insertAndReturnId(record);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final HikingRecord record, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfHikingRecord.handle(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final HikingRecord record, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfHikingRecord.handle(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<HikingRecord>> getAllRecords() {
    final String _sql = "SELECT * FROM hiking_records ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"hiking_records"}, false, new Callable<List<HikingRecord>>() {
      @Override
      @Nullable
      public List<HikingRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMountainName = CursorUtil.getColumnIndexOrThrow(_cursor, "mountainName");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
          final int _cursorIndexOfMaxAltitude = CursorUtil.getColumnIndexOrThrow(_cursor, "maxAltitude");
          final int _cursorIndexOfMinAltitude = CursorUtil.getColumnIndexOrThrow(_cursor, "minAltitude");
          final int _cursorIndexOfElevationGain = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationGain");
          final int _cursorIndexOfElevationLoss = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationLoss");
          final int _cursorIndexOfAvgSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "avgSpeed");
          final int _cursorIndexOfMaxSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "maxSpeed");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfStartLat = CursorUtil.getColumnIndexOrThrow(_cursor, "startLat");
          final int _cursorIndexOfStartLng = CursorUtil.getColumnIndexOrThrow(_cursor, "startLng");
          final int _cursorIndexOfEndLat = CursorUtil.getColumnIndexOrThrow(_cursor, "endLat");
          final int _cursorIndexOfEndLng = CursorUtil.getColumnIndexOrThrow(_cursor, "endLng");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final int _cursorIndexOfIsUserImported = CursorUtil.getColumnIndexOrThrow(_cursor, "isUserImported");
          final List<HikingRecord> _result = new ArrayList<HikingRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HikingRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpMountainName;
            if (_cursor.isNull(_cursorIndexOfMountainName)) {
              _tmpMountainName = null;
            } else {
              _tmpMountainName = _cursor.getString(_cursorIndexOfMountainName);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            final double _tmpDistance;
            _tmpDistance = _cursor.getDouble(_cursorIndexOfDistance);
            final double _tmpMaxAltitude;
            _tmpMaxAltitude = _cursor.getDouble(_cursorIndexOfMaxAltitude);
            final double _tmpMinAltitude;
            _tmpMinAltitude = _cursor.getDouble(_cursorIndexOfMinAltitude);
            final double _tmpElevationGain;
            _tmpElevationGain = _cursor.getDouble(_cursorIndexOfElevationGain);
            final double _tmpElevationLoss;
            _tmpElevationLoss = _cursor.getDouble(_cursorIndexOfElevationLoss);
            final double _tmpAvgSpeed;
            _tmpAvgSpeed = _cursor.getDouble(_cursorIndexOfAvgSpeed);
            final double _tmpMaxSpeed;
            _tmpMaxSpeed = _cursor.getDouble(_cursorIndexOfMaxSpeed);
            final int _tmpCalories;
            _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            final double _tmpStartLat;
            _tmpStartLat = _cursor.getDouble(_cursorIndexOfStartLat);
            final double _tmpStartLng;
            _tmpStartLng = _cursor.getDouble(_cursorIndexOfStartLng);
            final double _tmpEndLat;
            _tmpEndLat = _cursor.getDouble(_cursorIndexOfEndLat);
            final double _tmpEndLng;
            _tmpEndLng = _cursor.getDouble(_cursorIndexOfEndLng);
            final String _tmpMemo;
            if (_cursor.isNull(_cursorIndexOfMemo)) {
              _tmpMemo = null;
            } else {
              _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            }
            final boolean _tmpIsUserImported;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUserImported);
            _tmpIsUserImported = _tmp != 0;
            _item = new HikingRecord(_tmpId,_tmpMountainName,_tmpStartTime,_tmpEndTime,_tmpDistance,_tmpMaxAltitude,_tmpMinAltitude,_tmpElevationGain,_tmpElevationLoss,_tmpAvgSpeed,_tmpMaxSpeed,_tmpCalories,_tmpStartLat,_tmpStartLng,_tmpEndLat,_tmpEndLng,_tmpMemo,_tmpIsUserImported);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAllRecordsList(final Continuation<? super List<HikingRecord>> $completion) {
    final String _sql = "SELECT * FROM hiking_records ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<HikingRecord>>() {
      @Override
      @NonNull
      public List<HikingRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMountainName = CursorUtil.getColumnIndexOrThrow(_cursor, "mountainName");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
          final int _cursorIndexOfMaxAltitude = CursorUtil.getColumnIndexOrThrow(_cursor, "maxAltitude");
          final int _cursorIndexOfMinAltitude = CursorUtil.getColumnIndexOrThrow(_cursor, "minAltitude");
          final int _cursorIndexOfElevationGain = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationGain");
          final int _cursorIndexOfElevationLoss = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationLoss");
          final int _cursorIndexOfAvgSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "avgSpeed");
          final int _cursorIndexOfMaxSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "maxSpeed");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfStartLat = CursorUtil.getColumnIndexOrThrow(_cursor, "startLat");
          final int _cursorIndexOfStartLng = CursorUtil.getColumnIndexOrThrow(_cursor, "startLng");
          final int _cursorIndexOfEndLat = CursorUtil.getColumnIndexOrThrow(_cursor, "endLat");
          final int _cursorIndexOfEndLng = CursorUtil.getColumnIndexOrThrow(_cursor, "endLng");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final int _cursorIndexOfIsUserImported = CursorUtil.getColumnIndexOrThrow(_cursor, "isUserImported");
          final List<HikingRecord> _result = new ArrayList<HikingRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HikingRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpMountainName;
            if (_cursor.isNull(_cursorIndexOfMountainName)) {
              _tmpMountainName = null;
            } else {
              _tmpMountainName = _cursor.getString(_cursorIndexOfMountainName);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            final double _tmpDistance;
            _tmpDistance = _cursor.getDouble(_cursorIndexOfDistance);
            final double _tmpMaxAltitude;
            _tmpMaxAltitude = _cursor.getDouble(_cursorIndexOfMaxAltitude);
            final double _tmpMinAltitude;
            _tmpMinAltitude = _cursor.getDouble(_cursorIndexOfMinAltitude);
            final double _tmpElevationGain;
            _tmpElevationGain = _cursor.getDouble(_cursorIndexOfElevationGain);
            final double _tmpElevationLoss;
            _tmpElevationLoss = _cursor.getDouble(_cursorIndexOfElevationLoss);
            final double _tmpAvgSpeed;
            _tmpAvgSpeed = _cursor.getDouble(_cursorIndexOfAvgSpeed);
            final double _tmpMaxSpeed;
            _tmpMaxSpeed = _cursor.getDouble(_cursorIndexOfMaxSpeed);
            final int _tmpCalories;
            _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            final double _tmpStartLat;
            _tmpStartLat = _cursor.getDouble(_cursorIndexOfStartLat);
            final double _tmpStartLng;
            _tmpStartLng = _cursor.getDouble(_cursorIndexOfStartLng);
            final double _tmpEndLat;
            _tmpEndLat = _cursor.getDouble(_cursorIndexOfEndLat);
            final double _tmpEndLng;
            _tmpEndLng = _cursor.getDouble(_cursorIndexOfEndLng);
            final String _tmpMemo;
            if (_cursor.isNull(_cursorIndexOfMemo)) {
              _tmpMemo = null;
            } else {
              _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            }
            final boolean _tmpIsUserImported;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUserImported);
            _tmpIsUserImported = _tmp != 0;
            _item = new HikingRecord(_tmpId,_tmpMountainName,_tmpStartTime,_tmpEndTime,_tmpDistance,_tmpMaxAltitude,_tmpMinAltitude,_tmpElevationGain,_tmpElevationLoss,_tmpAvgSpeed,_tmpMaxSpeed,_tmpCalories,_tmpStartLat,_tmpStartLng,_tmpEndLat,_tmpEndLng,_tmpMemo,_tmpIsUserImported);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getRecordById(final long id, final Continuation<? super HikingRecord> $completion) {
    final String _sql = "SELECT * FROM hiking_records WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<HikingRecord>() {
      @Override
      @Nullable
      public HikingRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMountainName = CursorUtil.getColumnIndexOrThrow(_cursor, "mountainName");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
          final int _cursorIndexOfMaxAltitude = CursorUtil.getColumnIndexOrThrow(_cursor, "maxAltitude");
          final int _cursorIndexOfMinAltitude = CursorUtil.getColumnIndexOrThrow(_cursor, "minAltitude");
          final int _cursorIndexOfElevationGain = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationGain");
          final int _cursorIndexOfElevationLoss = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationLoss");
          final int _cursorIndexOfAvgSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "avgSpeed");
          final int _cursorIndexOfMaxSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "maxSpeed");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfStartLat = CursorUtil.getColumnIndexOrThrow(_cursor, "startLat");
          final int _cursorIndexOfStartLng = CursorUtil.getColumnIndexOrThrow(_cursor, "startLng");
          final int _cursorIndexOfEndLat = CursorUtil.getColumnIndexOrThrow(_cursor, "endLat");
          final int _cursorIndexOfEndLng = CursorUtil.getColumnIndexOrThrow(_cursor, "endLng");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final int _cursorIndexOfIsUserImported = CursorUtil.getColumnIndexOrThrow(_cursor, "isUserImported");
          final HikingRecord _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpMountainName;
            if (_cursor.isNull(_cursorIndexOfMountainName)) {
              _tmpMountainName = null;
            } else {
              _tmpMountainName = _cursor.getString(_cursorIndexOfMountainName);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            final double _tmpDistance;
            _tmpDistance = _cursor.getDouble(_cursorIndexOfDistance);
            final double _tmpMaxAltitude;
            _tmpMaxAltitude = _cursor.getDouble(_cursorIndexOfMaxAltitude);
            final double _tmpMinAltitude;
            _tmpMinAltitude = _cursor.getDouble(_cursorIndexOfMinAltitude);
            final double _tmpElevationGain;
            _tmpElevationGain = _cursor.getDouble(_cursorIndexOfElevationGain);
            final double _tmpElevationLoss;
            _tmpElevationLoss = _cursor.getDouble(_cursorIndexOfElevationLoss);
            final double _tmpAvgSpeed;
            _tmpAvgSpeed = _cursor.getDouble(_cursorIndexOfAvgSpeed);
            final double _tmpMaxSpeed;
            _tmpMaxSpeed = _cursor.getDouble(_cursorIndexOfMaxSpeed);
            final int _tmpCalories;
            _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            final double _tmpStartLat;
            _tmpStartLat = _cursor.getDouble(_cursorIndexOfStartLat);
            final double _tmpStartLng;
            _tmpStartLng = _cursor.getDouble(_cursorIndexOfStartLng);
            final double _tmpEndLat;
            _tmpEndLat = _cursor.getDouble(_cursorIndexOfEndLat);
            final double _tmpEndLng;
            _tmpEndLng = _cursor.getDouble(_cursorIndexOfEndLng);
            final String _tmpMemo;
            if (_cursor.isNull(_cursorIndexOfMemo)) {
              _tmpMemo = null;
            } else {
              _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            }
            final boolean _tmpIsUserImported;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUserImported);
            _tmpIsUserImported = _tmp != 0;
            _result = new HikingRecord(_tmpId,_tmpMountainName,_tmpStartTime,_tmpEndTime,_tmpDistance,_tmpMaxAltitude,_tmpMinAltitude,_tmpElevationGain,_tmpElevationLoss,_tmpAvgSpeed,_tmpMaxSpeed,_tmpCalories,_tmpStartLat,_tmpStartLng,_tmpEndLat,_tmpEndLng,_tmpMemo,_tmpIsUserImported);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<HikingRecord> getRecordByIdLive(final long id) {
    final String _sql = "SELECT * FROM hiking_records WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"hiking_records"}, false, new Callable<HikingRecord>() {
      @Override
      @Nullable
      public HikingRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMountainName = CursorUtil.getColumnIndexOrThrow(_cursor, "mountainName");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
          final int _cursorIndexOfMaxAltitude = CursorUtil.getColumnIndexOrThrow(_cursor, "maxAltitude");
          final int _cursorIndexOfMinAltitude = CursorUtil.getColumnIndexOrThrow(_cursor, "minAltitude");
          final int _cursorIndexOfElevationGain = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationGain");
          final int _cursorIndexOfElevationLoss = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationLoss");
          final int _cursorIndexOfAvgSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "avgSpeed");
          final int _cursorIndexOfMaxSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "maxSpeed");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfStartLat = CursorUtil.getColumnIndexOrThrow(_cursor, "startLat");
          final int _cursorIndexOfStartLng = CursorUtil.getColumnIndexOrThrow(_cursor, "startLng");
          final int _cursorIndexOfEndLat = CursorUtil.getColumnIndexOrThrow(_cursor, "endLat");
          final int _cursorIndexOfEndLng = CursorUtil.getColumnIndexOrThrow(_cursor, "endLng");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final int _cursorIndexOfIsUserImported = CursorUtil.getColumnIndexOrThrow(_cursor, "isUserImported");
          final HikingRecord _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpMountainName;
            if (_cursor.isNull(_cursorIndexOfMountainName)) {
              _tmpMountainName = null;
            } else {
              _tmpMountainName = _cursor.getString(_cursorIndexOfMountainName);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            final double _tmpDistance;
            _tmpDistance = _cursor.getDouble(_cursorIndexOfDistance);
            final double _tmpMaxAltitude;
            _tmpMaxAltitude = _cursor.getDouble(_cursorIndexOfMaxAltitude);
            final double _tmpMinAltitude;
            _tmpMinAltitude = _cursor.getDouble(_cursorIndexOfMinAltitude);
            final double _tmpElevationGain;
            _tmpElevationGain = _cursor.getDouble(_cursorIndexOfElevationGain);
            final double _tmpElevationLoss;
            _tmpElevationLoss = _cursor.getDouble(_cursorIndexOfElevationLoss);
            final double _tmpAvgSpeed;
            _tmpAvgSpeed = _cursor.getDouble(_cursorIndexOfAvgSpeed);
            final double _tmpMaxSpeed;
            _tmpMaxSpeed = _cursor.getDouble(_cursorIndexOfMaxSpeed);
            final int _tmpCalories;
            _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            final double _tmpStartLat;
            _tmpStartLat = _cursor.getDouble(_cursorIndexOfStartLat);
            final double _tmpStartLng;
            _tmpStartLng = _cursor.getDouble(_cursorIndexOfStartLng);
            final double _tmpEndLat;
            _tmpEndLat = _cursor.getDouble(_cursorIndexOfEndLat);
            final double _tmpEndLng;
            _tmpEndLng = _cursor.getDouble(_cursorIndexOfEndLng);
            final String _tmpMemo;
            if (_cursor.isNull(_cursorIndexOfMemo)) {
              _tmpMemo = null;
            } else {
              _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            }
            final boolean _tmpIsUserImported;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUserImported);
            _tmpIsUserImported = _tmp != 0;
            _result = new HikingRecord(_tmpId,_tmpMountainName,_tmpStartTime,_tmpEndTime,_tmpDistance,_tmpMaxAltitude,_tmpMinAltitude,_tmpElevationGain,_tmpElevationLoss,_tmpAvgSpeed,_tmpMaxSpeed,_tmpCalories,_tmpStartLat,_tmpStartLng,_tmpEndLat,_tmpEndLng,_tmpMemo,_tmpIsUserImported);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<HikingRecord>> getAppRecords() {
    final String _sql = "SELECT * FROM hiking_records WHERE isUserImported = 0 ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"hiking_records"}, false, new Callable<List<HikingRecord>>() {
      @Override
      @Nullable
      public List<HikingRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMountainName = CursorUtil.getColumnIndexOrThrow(_cursor, "mountainName");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
          final int _cursorIndexOfMaxAltitude = CursorUtil.getColumnIndexOrThrow(_cursor, "maxAltitude");
          final int _cursorIndexOfMinAltitude = CursorUtil.getColumnIndexOrThrow(_cursor, "minAltitude");
          final int _cursorIndexOfElevationGain = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationGain");
          final int _cursorIndexOfElevationLoss = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationLoss");
          final int _cursorIndexOfAvgSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "avgSpeed");
          final int _cursorIndexOfMaxSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "maxSpeed");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfStartLat = CursorUtil.getColumnIndexOrThrow(_cursor, "startLat");
          final int _cursorIndexOfStartLng = CursorUtil.getColumnIndexOrThrow(_cursor, "startLng");
          final int _cursorIndexOfEndLat = CursorUtil.getColumnIndexOrThrow(_cursor, "endLat");
          final int _cursorIndexOfEndLng = CursorUtil.getColumnIndexOrThrow(_cursor, "endLng");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final int _cursorIndexOfIsUserImported = CursorUtil.getColumnIndexOrThrow(_cursor, "isUserImported");
          final List<HikingRecord> _result = new ArrayList<HikingRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HikingRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpMountainName;
            if (_cursor.isNull(_cursorIndexOfMountainName)) {
              _tmpMountainName = null;
            } else {
              _tmpMountainName = _cursor.getString(_cursorIndexOfMountainName);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            final double _tmpDistance;
            _tmpDistance = _cursor.getDouble(_cursorIndexOfDistance);
            final double _tmpMaxAltitude;
            _tmpMaxAltitude = _cursor.getDouble(_cursorIndexOfMaxAltitude);
            final double _tmpMinAltitude;
            _tmpMinAltitude = _cursor.getDouble(_cursorIndexOfMinAltitude);
            final double _tmpElevationGain;
            _tmpElevationGain = _cursor.getDouble(_cursorIndexOfElevationGain);
            final double _tmpElevationLoss;
            _tmpElevationLoss = _cursor.getDouble(_cursorIndexOfElevationLoss);
            final double _tmpAvgSpeed;
            _tmpAvgSpeed = _cursor.getDouble(_cursorIndexOfAvgSpeed);
            final double _tmpMaxSpeed;
            _tmpMaxSpeed = _cursor.getDouble(_cursorIndexOfMaxSpeed);
            final int _tmpCalories;
            _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            final double _tmpStartLat;
            _tmpStartLat = _cursor.getDouble(_cursorIndexOfStartLat);
            final double _tmpStartLng;
            _tmpStartLng = _cursor.getDouble(_cursorIndexOfStartLng);
            final double _tmpEndLat;
            _tmpEndLat = _cursor.getDouble(_cursorIndexOfEndLat);
            final double _tmpEndLng;
            _tmpEndLng = _cursor.getDouble(_cursorIndexOfEndLng);
            final String _tmpMemo;
            if (_cursor.isNull(_cursorIndexOfMemo)) {
              _tmpMemo = null;
            } else {
              _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            }
            final boolean _tmpIsUserImported;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUserImported);
            _tmpIsUserImported = _tmp != 0;
            _item = new HikingRecord(_tmpId,_tmpMountainName,_tmpStartTime,_tmpEndTime,_tmpDistance,_tmpMaxAltitude,_tmpMinAltitude,_tmpElevationGain,_tmpElevationLoss,_tmpAvgSpeed,_tmpMaxSpeed,_tmpCalories,_tmpStartLat,_tmpStartLng,_tmpEndLat,_tmpEndLng,_tmpMemo,_tmpIsUserImported);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<HikingRecord>> getUserImportedRecords() {
    final String _sql = "SELECT * FROM hiking_records WHERE isUserImported = 1 ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"hiking_records"}, false, new Callable<List<HikingRecord>>() {
      @Override
      @Nullable
      public List<HikingRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMountainName = CursorUtil.getColumnIndexOrThrow(_cursor, "mountainName");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
          final int _cursorIndexOfMaxAltitude = CursorUtil.getColumnIndexOrThrow(_cursor, "maxAltitude");
          final int _cursorIndexOfMinAltitude = CursorUtil.getColumnIndexOrThrow(_cursor, "minAltitude");
          final int _cursorIndexOfElevationGain = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationGain");
          final int _cursorIndexOfElevationLoss = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationLoss");
          final int _cursorIndexOfAvgSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "avgSpeed");
          final int _cursorIndexOfMaxSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "maxSpeed");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfStartLat = CursorUtil.getColumnIndexOrThrow(_cursor, "startLat");
          final int _cursorIndexOfStartLng = CursorUtil.getColumnIndexOrThrow(_cursor, "startLng");
          final int _cursorIndexOfEndLat = CursorUtil.getColumnIndexOrThrow(_cursor, "endLat");
          final int _cursorIndexOfEndLng = CursorUtil.getColumnIndexOrThrow(_cursor, "endLng");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final int _cursorIndexOfIsUserImported = CursorUtil.getColumnIndexOrThrow(_cursor, "isUserImported");
          final List<HikingRecord> _result = new ArrayList<HikingRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HikingRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpMountainName;
            if (_cursor.isNull(_cursorIndexOfMountainName)) {
              _tmpMountainName = null;
            } else {
              _tmpMountainName = _cursor.getString(_cursorIndexOfMountainName);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            final double _tmpDistance;
            _tmpDistance = _cursor.getDouble(_cursorIndexOfDistance);
            final double _tmpMaxAltitude;
            _tmpMaxAltitude = _cursor.getDouble(_cursorIndexOfMaxAltitude);
            final double _tmpMinAltitude;
            _tmpMinAltitude = _cursor.getDouble(_cursorIndexOfMinAltitude);
            final double _tmpElevationGain;
            _tmpElevationGain = _cursor.getDouble(_cursorIndexOfElevationGain);
            final double _tmpElevationLoss;
            _tmpElevationLoss = _cursor.getDouble(_cursorIndexOfElevationLoss);
            final double _tmpAvgSpeed;
            _tmpAvgSpeed = _cursor.getDouble(_cursorIndexOfAvgSpeed);
            final double _tmpMaxSpeed;
            _tmpMaxSpeed = _cursor.getDouble(_cursorIndexOfMaxSpeed);
            final int _tmpCalories;
            _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            final double _tmpStartLat;
            _tmpStartLat = _cursor.getDouble(_cursorIndexOfStartLat);
            final double _tmpStartLng;
            _tmpStartLng = _cursor.getDouble(_cursorIndexOfStartLng);
            final double _tmpEndLat;
            _tmpEndLat = _cursor.getDouble(_cursorIndexOfEndLat);
            final double _tmpEndLng;
            _tmpEndLng = _cursor.getDouble(_cursorIndexOfEndLng);
            final String _tmpMemo;
            if (_cursor.isNull(_cursorIndexOfMemo)) {
              _tmpMemo = null;
            } else {
              _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            }
            final boolean _tmpIsUserImported;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUserImported);
            _tmpIsUserImported = _tmp != 0;
            _item = new HikingRecord(_tmpId,_tmpMountainName,_tmpStartTime,_tmpEndTime,_tmpDistance,_tmpMaxAltitude,_tmpMinAltitude,_tmpElevationGain,_tmpElevationLoss,_tmpAvgSpeed,_tmpMaxSpeed,_tmpCalories,_tmpStartLat,_tmpStartLng,_tmpEndLat,_tmpEndLng,_tmpMemo,_tmpIsUserImported);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
