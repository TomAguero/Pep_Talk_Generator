package com.example.peptalkgenerator

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
//import com.example.peptalkgenerator.data.AppContainer
//import com.example.peptalkgenerator.data.AppDataContainer
import com.example.peptalkgenerator.data.PepTalkRepository
import com.example.peptalkgenerator.data.PepTalksDatabase

class PepTalkApplication : Application() {
    private val pepTalksDB by lazy { PepTalksDatabase.getDatabase(this) }
    val pepTalkRepository by lazy {
        PepTalkRepository(
            pepTalksDB.phraseDao(),
            pepTalksDB.pepTalkDao()
        )
    }

    override fun onCreate() {
        super.onCreate()

        val name = getString(R.string.app_name)
        val descriptionText = getString(R.string.settings_reminder_desc)
        val importance = NotificationManager.IMPORTANCE_LOW

        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

    companion object {
        const val CHANNEL_ID = "PepTalk_ID"
    }
}