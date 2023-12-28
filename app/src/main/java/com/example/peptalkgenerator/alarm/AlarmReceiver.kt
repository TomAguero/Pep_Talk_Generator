package com.example.peptalkgenerator.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.peptalkgenerator.MainActivity
import com.example.peptalkgenerator.R

private const val NOTIFICATION_ID = 3
private const val NOTIFICATION_CHANNEL_ID = "daily_peptalk"
private const val NOTIFICATION_CHANNEL_NAME = "Daily PepTalk"

//Based off of https://www.kodeco.com/33899169-triggering-alarms-tutorial-for-android-getting-started?page=3#toc-anchor-012
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(
            "PepTalkAlarmBroadcastReceiver",
            "Daily PepTalk triggered"
        )

        val appIntent = Intent(context, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            appIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val body = ""

        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.thumb_up_fill0_wght400_grad0_opsz24)
            .setContentTitle("New Daily Pep Talk")
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        val notification = notificationBuilder.build()
        val notificationManager = context.getSystemService(NotificationManager::class.java)

        if (notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW
                )
            )
        }

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}

/* Original way
class AlarmReceiver : BroadcastReceiver() {
    private var notificationManager: NotificationManagerCompat? = null

    override fun onReceive(context: Context, intent: Intent) {

        val message = intent.getStringExtra("EXTRA_MESSAGE") ?: return
        val channelID = "pep_talk"

        context.let { ctx ->
            val notificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val builder = NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.thumb_up_fill0_wght400_grad0_opsz24)
                .setContentTitle(context.getString(R.string.channel_new_pep_talk))
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            notificationManager.notify(1, builder.build())
        }
    }
}
 */