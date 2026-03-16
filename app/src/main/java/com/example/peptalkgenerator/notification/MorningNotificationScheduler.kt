package com.example.peptalkgenerator.notification

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object MorningNotificationScheduler {

    private const val WORK_NAME = "morning_pep_talk_notification"
    const val DEFAULT_HOUR = 8
    const val DEFAULT_MINUTE = 0

    fun schedule(context: Context, hour: Int = DEFAULT_HOUR, minute: Int = DEFAULT_MINUTE) {
        val initialDelay = calculateInitialDelay(hour, minute)

        val workRequest = PeriodicWorkRequestBuilder<PepTalkNotificationWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun cancel(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }

    private fun calculateInitialDelay(hour: Int, minute: Int): Long {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        if (now.after(target)) {
            target.add(Calendar.DAY_OF_YEAR, 1)
        }
        return target.timeInMillis - now.timeInMillis
    }
}
