package com.example.peptalkgenerator.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.peptalkgenerator.PepTalkApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val app = context.applicationContext as PepTalkApplication
                val pepTalk = app.pepTalkRepository.generateNewTalk()
                PepTalkNotificationWorker.showNotification(context, pepTalk)

                // Reschedule for tomorrow
                val hour = NotificationPreferences.getHour(context)
                val minute = NotificationPreferences.getMinute(context)
                MorningNotificationScheduler.schedule(context, hour, minute)
            } finally {
                pendingResult.finish()
            }
        }
    }
}
