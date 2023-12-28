package com.example.peptalkgenerator.alarm

/*
class AlarmSchedulerImplementation(
    private val context: Context,
    private val pepTalkRepository: PepTalkRepository
) {

    private lateinit var alarmIntent: PendingIntent
    private var alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private val _talkState = mutableStateOf("")
    private val talkState: String by _talkState

    init {
        refreshTalkState()
    }

    private fun refreshTalkState() {
        _talkState.value = pepTalkRepository.generateNewTalk()
    }

    fun schedule(alarmItem: AlarmItem) {
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        // Set the alarm to start at approximately 8:00 a.m.
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
        }

        alarmMgr.setInexactRepeating(
            AlarmManager.RTC,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
    }

    fun cancel(alarmItem: AlarmItem) {
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }

        // If the alarm has been set, cancel it.
        alarmMgr.cancel(alarmIntent)
    }


}

 */