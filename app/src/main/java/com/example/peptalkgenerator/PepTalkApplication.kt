package com.example.peptalkgenerator

import android.app.Application
//import com.example.peptalkgenerator.data.AppContainer
//import com.example.peptalkgenerator.data.AppDataContainer
import com.example.peptalkgenerator.data.PepTalkRepository
import com.example.peptalkgenerator.data.PepTalksDatabase
import com.example.peptalkgenerator.notification.MorningNotificationScheduler

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
        MorningNotificationScheduler.schedule(this)
    }
}