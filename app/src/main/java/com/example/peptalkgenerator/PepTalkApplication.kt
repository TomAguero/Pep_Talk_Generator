package com.example.peptalkgenerator

import android.app.Application
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
}