package com.example.peptalkgenerator.alarm

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class AlarmApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val channelID = "pep_talk_ID"
        val channelName = "pep_talk"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            channelID,
            channelName,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}