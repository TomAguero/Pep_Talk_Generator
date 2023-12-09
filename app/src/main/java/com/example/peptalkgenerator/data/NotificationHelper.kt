package com.example.peptalkgenerator.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.annotation.WorkerThread
import androidx.compose.runtime.Composable
import androidx.core.app.NotificationCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peptalkgenerator.MainActivity
import com.example.peptalkgenerator.R
import com.example.peptalkgenerator.model.PepTalkScreenViewModel

//Handles all operations related to notifications
/*
class NotificationHelper(private val context: Context) {

    companion object {
        /**
         * The notification channel for messages. This is used for showing Bubbles.
         */
        private const val CHANNEL_NEW_MESSAGES = "new_pep_talk"
        private const val NEW_PEP_TALK = 1
    }

    private val notificationManager: NotificationManager =
        context.getSystemService() ?: throw IllegalStateException()

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        val name = context.getString(R.string.channel_new_pep_talk)
        val descriptionText = context.getString(R.string.channel_new_pep_talk)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_NEW_MESSAGES, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system.
        val notificationManager: NotificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @WorkerThread
    @Composable
    fun ShowNotification() {
        val pepTalkViewModel: PepTalkScreenViewModel =
            viewModel(factory = PepTalkScreenViewModel.Factory)
        val pepTalk = pepTalkViewModel.talkState


        val builder = NotificationCompat.Builder(context, CHANNEL_NEW_MESSAGES)
            .setSmallIcon(R.drawable.thumb_up_fill0_wght400_grad0_opsz24)
            .setContentTitle(context.getString(R.string.channel_new_pep_talk))
            .setContentText(pepTalk)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    NEW_PEP_TALK,
                    Intent(context, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            .setAutoCancel(true)
        notificationManager.notify(pepTalk.toInt(), builder.build())
    }
}
*/
