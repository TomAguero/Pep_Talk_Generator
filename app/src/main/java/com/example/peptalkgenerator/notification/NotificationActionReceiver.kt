package com.example.peptalkgenerator.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.peptalkgenerator.PepTalkApplication
import com.example.peptalkgenerator.data.PepTalk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val pepTalkText = intent.getStringExtra(EXTRA_PEP_TALK) ?: return
        when (intent.action) {
            ACTION_FAVORITE -> handleFavorite(context, pepTalkText)
        }
    }

    private fun handleFavorite(context: Context, pepTalkText: String) {
        val pendingResult = goAsync()
        val repository = (context.applicationContext as PepTalkApplication).pepTalkRepository
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.insertPepTalk(
                    PepTalk(pepTalk = pepTalkText, favorite = true, block = false)
                )
            } finally {
                // Cancel the notification regardless of whether the insert succeeded so the
                // user always gets visual feedback that the button tap was received.
                notificationManager.cancel(PepTalkNotificationWorker.NOTIFICATION_ID)
                pendingResult.finish()
            }
        }
    }

    companion object {
        const val ACTION_FAVORITE = "com.example.peptalkgenerator.notification.ACTION_FAVORITE"
        const val EXTRA_PEP_TALK = "extra_pep_talk"
    }
}
