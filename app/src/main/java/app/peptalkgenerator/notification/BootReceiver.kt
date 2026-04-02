package app.peptalkgenerator.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            if (NotificationPreferences.isEnabled(context)) {
                val hour = NotificationPreferences.getHour(context)
                val minute = NotificationPreferences.getMinute(context)
                MorningNotificationScheduler.schedule(context, hour, minute)
            }
        }
    }
}
