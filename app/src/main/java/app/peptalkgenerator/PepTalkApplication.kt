package app.peptalkgenerator

import android.app.Application
import app.peptalkgenerator.data.PepTalkRepository
import app.peptalkgenerator.data.PepTalksDatabase

import app.peptalkgenerator.notification.MorningNotificationScheduler

class PepTalkApplication : Application() {
    private val pepTalksDB by lazy { PepTalksDatabase.getDatabase(this) }
    val pepTalkRepository by lazy {
        PepTalkRepository(
            pepTalksDB.phraseDao(),
            pepTalksDB.pepTalkDao()
        )
    }

    /** Holds the pep talk text from a tapped morning notification, consumed once by the ViewModel. */
    var pendingNotificationTalk: String? = null

    override fun onCreate() {
        super.onCreate()
        val prefs = getSharedPreferences("notification_prefs", MODE_PRIVATE)
        val enabled = prefs.getBoolean("notifications_enabled", true)
        if (enabled) {
            val hour = prefs.getInt("notification_hour", MorningNotificationScheduler.DEFAULT_HOUR)
            val minute = prefs.getInt("notification_minute", MorningNotificationScheduler.DEFAULT_MINUTE)
            MorningNotificationScheduler.schedule(this, hour, minute)
        }
    }
}
