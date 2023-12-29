package com.example.peptalkgenerator.alarm

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peptalkgenerator.data.PepTalkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val application: Application,
    private val pepTalkRepository: PepTalkRepository
) : ViewModel() {

    private val _talkState = mutableStateOf("")
    private val talkState: String by _talkState

    init {
        refreshTalkState()
    }

    private fun refreshTalkState() {
        viewModelScope.launch {
            _talkState.value = pepTalkRepository.generateNewTalk()
        }
    }

    private lateinit var alarmIntent: PendingIntent
    private var alarmMgr = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleAlarm() {
        alarmIntent = Intent(application, AlarmReceiver::class.java).apply {
            putExtra(talkState, 1002)
        }.let { intent ->
            PendingIntent.getBroadcast(
                application,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        // Set the alarm to start at approximately 8:00 a.m.
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 11)
        }

        alarmMgr.setInexactRepeating(
            AlarmManager.RTC,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_HALF_HOUR,
            alarmIntent
        )
        Log.d(
            "PepTalkAlarmViewModel",
            "Alarm Scheduled?"
        )
    }

    fun cancelAlarm() {
        alarmIntent = Intent(application, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(application, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }

        // If the alarm has been set, cancel it.
        alarmMgr.cancel(alarmIntent)
    }

}
