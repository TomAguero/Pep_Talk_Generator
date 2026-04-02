package app.peptalkgenerator

import android.app.Application
import app.peptalkgenerator.data.PepTalkRepository
import app.peptalkgenerator.data.PepTalksDatabase

import app.peptalkgenerator.notification.MorningNotificationScheduler
import app.peptalkgenerator.notification.NotificationPreferences

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
        if (NotificationPreferences.isEnabled(this)) {
            val hour = NotificationPreferences.getHour(this)
            val minute = NotificationPreferences.getMinute(this)
            MorningNotificationScheduler.schedule(this, hour, minute)
        }
    }
}
