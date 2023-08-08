package com.example.peptalkgenerator.data

import android.content.Context

//App container for Dependency Injection

interface AppContainer {
    val pepTalkRepository: PepTalkRepository
}

//App container implementation that provides instance of Offline Repo

class AppDataContainer(private val context: Context) : AppContainer{
    //implementation for Item Repo
    override val pepTalkRepository: PepTalkRepository by lazy {
        OfflinePepTalkRepository(
            PepTalksDatabase.getDatabase(context).phraseDao(),
            PepTalksDatabase.getDatabase(context).pepTalkDao()
        )
    }
}