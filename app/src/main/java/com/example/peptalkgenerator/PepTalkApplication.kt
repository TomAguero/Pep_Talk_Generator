package com.example.peptalkgenerator

import android.app.Application
import com.example.peptalkgenerator.data.AppContainer
import com.example.peptalkgenerator.data.AppDataContainer

class PepTalkApplication : Application () {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}