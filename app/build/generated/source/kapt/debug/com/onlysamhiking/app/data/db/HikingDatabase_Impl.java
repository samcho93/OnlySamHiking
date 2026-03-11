package com.onlysamhiking.app.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class HikingDatabase_Impl extends HikingDatabase {
  private volatile HikingRecordDao _hikingRecordDao;

  private volatile TrackPointDao _trackPointDao;

  private volatile HikingPhotoDao _hikingPhotoDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `hiking_records` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `mountainName` TEXT NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER NOT NULL, `distance` REAL NOT NULL, `maxAltitude` REAL NOT NULL, `minAltitude` REAL NOT NULL, `elevationGain` REAL NOT NULL, `elevationLoss` REAL NOT NULL, `avgSpeed` REAL NOT NULL, `maxSpeed` REAL NOT NULL, `calories` INTEGER NOT NULL, `startLat` REAL NOT NULL, `startLng` REAL NOT NULL, `endLat` REAL NOT NULL, `endLng` REAL NOT NULL, `memo` TEXT NOT NULL, `isUserImported` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `track_points` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `recordId` INTEGER NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `altitude` REAL NOT NULL, `speed` REAL NOT NULL, `accuracy` REAL NOT NULL, `timestamp` INTEGER NOT NULL, FOREIGN KEY(`recordId`) REFERENCES `hiking_records`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_track_points_recordId` ON `track_points` (`recordId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `hiking_photos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `recordId` INTEGER NOT NULL, `filePath` TEXT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `altitude` REAL NOT NULL, `timestamp` INTEGER NOT NULL, FOREIGN KEY(`recordId`) REFERENCES `hiking_records`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_hiking_photos_recordId` ON `hiking_photos` (`recordId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5ff04b22954979150513a2aca9aac1f9')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `hiking_records`");
        db.execSQL("DROP TABLE IF EXISTS `track_points`");
        db.execSQL("DROP TABLE IF EXISTS `hiking_photos`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsHikingRecords = new HashMap<String, TableInfo.Column>(18);
        _columnsHikingRecords.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("mountainName", new TableInfo.Column("mountainName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("startTime", new TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("endTime", new TableInfo.Column("endTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("distance", new TableInfo.Column("distance", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("maxAltitude", new TableInfo.Column("maxAltitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("minAltitude", new TableInfo.Column("minAltitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("elevationGain", new TableInfo.Column("elevationGain", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("elevationLoss", new TableInfo.Column("elevationLoss", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("avgSpeed", new TableInfo.Column("avgSpeed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("maxSpeed", new TableInfo.Column("maxSpeed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("calories", new TableInfo.Column("calories", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("startLat", new TableInfo.Column("startLat", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("startLng", new TableInfo.Column("startLng", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("endLat", new TableInfo.Column("endLat", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("endLng", new TableInfo.Column("endLng", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("memo", new TableInfo.Column("memo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingRecords.put("isUserImported", new TableInfo.Column("isUserImported", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHikingRecords = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHikingRecords = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHikingRecords = new TableInfo("hiking_records", _columnsHikingRecords, _foreignKeysHikingRecords, _indicesHikingRecords);
        final TableInfo _existingHikingRecords = TableInfo.read(db, "hiking_records");
        if (!_infoHikingRecords.equals(_existingHikingRecords)) {
          return new RoomOpenHelper.ValidationResult(false, "hiking_records(com.onlysamhiking.app.data.model.HikingRecord).\n"
                  + " Expected:\n" + _infoHikingRecords + "\n"
                  + " Found:\n" + _existingHikingRecords);
        }
        final HashMap<String, TableInfo.Column> _columnsTrackPoints = new HashMap<String, TableInfo.Column>(8);
        _columnsTrackPoints.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackPoints.put("recordId", new TableInfo.Column("recordId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackPoints.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackPoints.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackPoints.put("altitude", new TableInfo.Column("altitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackPoints.put("speed", new TableInfo.Column("speed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackPoints.put("accuracy", new TableInfo.Column("accuracy", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackPoints.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrackPoints = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTrackPoints.add(new TableInfo.ForeignKey("hiking_records", "CASCADE", "NO ACTION", Arrays.asList("recordId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTrackPoints = new HashSet<TableInfo.Index>(1);
        _indicesTrackPoints.add(new TableInfo.Index("index_track_points_recordId", false, Arrays.asList("recordId"), Arrays.asList("ASC")));
        final TableInfo _infoTrackPoints = new TableInfo("track_points", _columnsTrackPoints, _foreignKeysTrackPoints, _indicesTrackPoints);
        final TableInfo _existingTrackPoints = TableInfo.read(db, "track_points");
        if (!_infoTrackPoints.equals(_existingTrackPoints)) {
          return new RoomOpenHelper.ValidationResult(false, "track_points(com.onlysamhiking.app.data.model.TrackPoint).\n"
                  + " Expected:\n" + _infoTrackPoints + "\n"
                  + " Found:\n" + _existingTrackPoints);
        }
        final HashMap<String, TableInfo.Column> _columnsHikingPhotos = new HashMap<String, TableInfo.Column>(7);
        _columnsHikingPhotos.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingPhotos.put("recordId", new TableInfo.Column("recordId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingPhotos.put("filePath", new TableInfo.Column("filePath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingPhotos.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingPhotos.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingPhotos.put("altitude", new TableInfo.Column("altitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHikingPhotos.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHikingPhotos = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysHikingPhotos.add(new TableInfo.ForeignKey("hiking_records", "CASCADE", "NO ACTION", Arrays.asList("recordId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesHikingPhotos = new HashSet<TableInfo.Index>(1);
        _indicesHikingPhotos.add(new TableInfo.Index("index_hiking_photos_recordId", false, Arrays.asList("recordId"), Arrays.asList("ASC")));
        final TableInfo _infoHikingPhotos = new TableInfo("hiking_photos", _columnsHikingPhotos, _foreignKeysHikingPhotos, _indicesHikingPhotos);
        final TableInfo _existingHikingPhotos = TableInfo.read(db, "hiking_photos");
        if (!_infoHikingPhotos.equals(_existingHikingPhotos)) {
          return new RoomOpenHelper.ValidationResult(false, "hiking_photos(com.onlysamhiking.app.data.model.HikingPhoto).\n"
                  + " Expected:\n" + _infoHikingPhotos + "\n"
                  + " Found:\n" + _existingHikingPhotos);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "5ff04b22954979150513a2aca9aac1f9", "545db3b3cebaa7550447547e95b9b66b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "hiking_records","track_points","hiking_photos");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `hiking_records`");
      _db.execSQL("DELETE FROM `track_points`");
      _db.execSQL("DELETE FROM `hiking_photos`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(HikingRecordDao.class, HikingRecordDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TrackPointDao.class, TrackPointDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(HikingPhotoDao.class, HikingPhotoDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public HikingRecordDao hikingRecordDao() {
    if (_hikingRecordDao != null) {
      return _hikingRecordDao;
    } else {
      synchronized(this) {
        if(_hikingRecordDao == null) {
          _hikingRecordDao = new HikingRecordDao_Impl(this);
        }
        return _hikingRecordDao;
      }
    }
  }

  @Override
  public TrackPointDao trackPointDao() {
    if (_trackPointDao != null) {
      return _trackPointDao;
    } else {
      synchronized(this) {
        if(_trackPointDao == null) {
          _trackPointDao = new TrackPointDao_Impl(this);
        }
        return _trackPointDao;
      }
    }
  }

  @Override
  public HikingPhotoDao hikingPhotoDao() {
    if (_hikingPhotoDao != null) {
      return _hikingPhotoDao;
    } else {
      synchronized(this) {
        if(_hikingPhotoDao == null) {
          _hikingPhotoDao = new HikingPhotoDao_Impl(this);
        }
        return _hikingPhotoDao;
      }
    }
  }
}
