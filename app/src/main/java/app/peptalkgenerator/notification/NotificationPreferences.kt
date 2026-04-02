package app.peptalkgenerator.notification

import android.content.Context

object NotificationPreferences {

    private const val PREFS_NAME = "notification_prefs"
    private const val KEY_ENABLED = "notifications_enabled"
    private const val KEY_HOUR = "notification_hour"
    private const val KEY_MINUTE = "notification_minute"

    fun isEnabled(context: Context): Boolean =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_ENABLED, true)

    fun getHour(context: Context): Int =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_HOUR, MorningNotificationScheduler.DEFAULT_HOUR)

    fun getMinute(context: Context): Int =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_MINUTE, MorningNotificationScheduler.DEFAULT_MINUTE)
}
