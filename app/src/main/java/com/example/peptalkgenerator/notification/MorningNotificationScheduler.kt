package com.example.peptalkgenerator.notification

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object MorningNotificationScheduler {

    private const val WORK_NAME = "morning_pep_talk_notification"
    const val NOTIFICATION_HOUR = 8
    const val NOTIFICATION_MINUTE = 0

    fun schedule(context: Context) {
        val delay = calculateDelayToNextTarget()

        val workRequest = OneTimeWorkRequestBuilder<PepTalkNotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun cancel(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }

    fun calculateDelayToNextTarget(): Long {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, NOTIFICATION_HOUR)
            set(Calendar.MINUTE, NOTIFICATION_MINUTE)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        if (!now.before(target)) {
            target.add(Calendar.DAY_OF_YEAR, 1)
        }
        return target.timeInMillis - now.timeInMillis
    }
}
