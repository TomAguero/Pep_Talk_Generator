package com.example.peptalkgenerator.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.peptalkgenerator.PepTalkApplication

class RescheduleAlarmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action != null) {
            if (action == Intent.ACTION_BOOT_COMPLETED) {
                (context.applicationContext as PepTalkApplication).alarmScheduler.rescheduleAlarms()
            }
        }
    }
}