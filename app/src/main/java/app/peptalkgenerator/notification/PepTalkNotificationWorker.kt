package app.peptalkgenerator.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.peptalkgenerator.MainActivity
import app.peptalkgenerator.PepTalkApplication
import app.peptalkgenerator.R
import app.peptalkgenerator.util.createPepTalkShareUri

class PepTalkNotificationWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val repository = (context.applicationContext as PepTalkApplication).pepTalkRepository
        val pepTalk = repository.generateNewTalk()
        showNotification(context, pepTalk)
        return Result.success()
    }

    companion object {
        const val CHANNEL_ID = "morning_pep_talk_channel"
        const val NOTIFICATION_ID = 1001

        fun showNotification(context: Context, pepTalk: String) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "Morning Pep Talk",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Your daily morning motivational pep talk"
                }
                notificationManager.createNotificationChannel(channel)
            }

            val openIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(MainActivity.EXTRA_PEP_TALK, pepTalk)
            }
            val openPendingIntent = PendingIntent.getActivity(
                context, 0, openIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val imageUri = createPepTalkShareUri(context, pepTalk)
            val shareChooserIntent = Intent.createChooser(
                Intent(Intent.ACTION_SEND).apply {
                    type = "image/png"
                    putExtra(Intent.EXTRA_STREAM, imageUri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                },
                null
            )
            val sharePendingIntent = PendingIntent.getActivity(
                context, 1, shareChooserIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val favoriteIntent = Intent(context, NotificationActionReceiver::class.java).apply {
                action = NotificationActionReceiver.ACTION_FAVORITE
                putExtra(NotificationActionReceiver.EXTRA_PEP_TALK, pepTalk)
            }
            val favoritePendingIntent = PendingIntent.getBroadcast(
                context, 2, favoriteIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Your Morning Pep Talk")
                .setContentText(pepTalk)
                .setStyle(NotificationCompat.BigTextStyle().bigText(pepTalk))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(openPendingIntent)
                .setAutoCancel(true)
                .addAction(0, "Share", sharePendingIntent)
                .addAction(0, "Favorite", favoritePendingIntent)
                .build()

            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
}
