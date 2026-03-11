package com.onlysamhiking.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.onlysamhiking.app.data.model.HikingPhoto
import com.onlysamhiking.app.data.model.HikingRecord
import com.onlysamhiking.app.data.model.TrackPoint

@Database(
    entities = [HikingRecord::class, TrackPoint::class, HikingPhoto::class],
    version = 3,
    exportSchema = false
)
abstract class HikingDatabase : RoomDatabase() {
    abstract fun hikingRecordDao(): HikingRecordDao
    abstract fun trackPointDao(): TrackPointDao
    abstract fun hikingPhotoDao(): HikingPhotoDao

    companion object {
        @Volatile
        private var INSTANCE: HikingDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE hiking_records ADD COLUMN memo TEXT NOT NULL DEFAULT ''")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE hiking_records ADD COLUMN isUserImported INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getDatabase(context: Context): HikingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HikingDatabase::class.java,
                    "hiking_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
