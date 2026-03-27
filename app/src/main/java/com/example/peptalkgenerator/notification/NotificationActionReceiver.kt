package com.example.peptalkgenerator.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.peptalkgenerator.PepTalkApplication
import com.example.peptalkgenerator.data.PepTalk
import com.example.peptalkgenerator.util.createPepTalkShareUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val pepTalkText = intent.getStringExtra(EXTRA_PEP_TALK) ?: return
        when (intent.action) {
            ACTION_SHARE -> handleShare(context, pepTalkText)
            ACTION_FAVORITE -> handleFavorite(context, pepTalkText)
        }
    }

    private fun handleShare(context: Context, pepTalkText: String) {
        val imageUri = createPepTalkShareUri(context, pepTalkText)
        val shareIntent = Intent.createChooser(
            Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, imageUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            },
            null
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(shareIntent)
    }

    private fun handleFavorite(context: Context, pepTalkText: String) {
        val pendingResult = goAsync()
        val repository = (context.applicationContext as PepTalkApplication).pepTalkRepository
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.insertPepTalk(
                    PepTalk(pepTalk = pepTalkText, favorite = true, block = false)
                )
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(PepTalkNotificationWorker.NOTIFICATION_ID)
            } finally {
                pendingResult.finish()
            }
        }
    }

    companion object {
        const val ACTION_SHARE = "com.example.peptalkgenerator.notification.ACTION_SHARE"
        const val ACTION_FAVORITE = "com.example.peptalkgenerator.notification.ACTION_FAVORITE"
        const val EXTRA_PEP_TALK = "extra_pep_talk"
    }
}
