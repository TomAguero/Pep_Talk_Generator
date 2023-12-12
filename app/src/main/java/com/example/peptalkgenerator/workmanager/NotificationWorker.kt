package com.example.peptalkgenerator.workmanager

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.peptalkgenerator.MainActivity
import com.example.peptalkgenerator.PepTalkApplication
import com.example.peptalkgenerator.R

class NotificationWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val notificationId = 123

    @SuppressLint("MissingPermission")
    override suspend fun doWork(): Result {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val pepTalk = inputData.getString(pepTalk)

        val body = "$pepTalk"

        val builder = NotificationCompat.Builder(applicationContext, PepTalkApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.thumb_up_fill0_wght400_grad0_opsz24)
            .setContentTitle("New Daily Pep Talk")
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(notificationId, builder.build())
        }

        return Result.success()
    }

    companion object {
        const val pepTalk = "PEP_TALK"
    }

}