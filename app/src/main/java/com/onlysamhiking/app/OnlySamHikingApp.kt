package com.onlysamhiking.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.onlysamhiking.app.data.db.HikingDatabase

class OnlySamHikingApp : Application() {

    val database by lazy { HikingDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_TRACKING,
            getString(com.onlysamhiking.app.R.string.notification_channel_tracking),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = getString(com.onlysamhiking.app.R.string.notification_channel_description)
            setShowBadge(false)
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_TRACKING = "hiking_tracking"
    }
}
