package com.example.peptalkgenerator.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar

class AlarmSchedulerImplementation(
    private val context: Context
) {

    private lateinit var alarmIntent: PendingIntent
    private var alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun schedule(alarmItem: AlarmItem) {
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        // Set the alarm to start at approximately 8:00 a.m.
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
        }

        alarmMgr.setInexactRepeating(
            AlarmManager.RTC,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
    }

    fun cancel(alarmItem: AlarmItem) {
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }

        // If the alarm has been set, cancel it.
        alarmMgr.cancel(alarmIntent)
    }

}