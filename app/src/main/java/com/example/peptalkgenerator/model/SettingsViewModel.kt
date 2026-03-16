package com.example.peptalkgenerator.model

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.peptalkgenerator.PepTalkApplication
import com.example.peptalkgenerator.notification.MorningNotificationScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsUiState(
    val notificationsEnabled: Boolean = true,
    val notificationHour: Int = MorningNotificationScheduler.DEFAULT_HOUR,
    val notificationMinute: Int = MorningNotificationScheduler.DEFAULT_MINUTE
)

private const val PREFS_NAME = "notification_prefs"
private const val KEY_ENABLED = "notifications_enabled"
private const val KEY_HOUR = "notification_hour"
private const val KEY_MINUTE = "notification_minute"

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _uiState = MutableStateFlow(
        SettingsUiState(
            notificationsEnabled = prefs.getBoolean(KEY_ENABLED, true),
            notificationHour = prefs.getInt(KEY_HOUR, MorningNotificationScheduler.DEFAULT_HOUR),
            notificationMinute = prefs.getInt(KEY_MINUTE, MorningNotificationScheduler.DEFAULT_MINUTE)
        )
    )
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun setNotificationsEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_ENABLED, enabled).apply()
        _uiState.update { it.copy(notificationsEnabled = enabled) }
        val context = getApplication<Application>()
        if (enabled) {
            MorningNotificationScheduler.schedule(context, _uiState.value.notificationHour, _uiState.value.notificationMinute)
        } else {
            MorningNotificationScheduler.cancel(context)
        }
    }

    fun setNotificationTime(hour: Int, minute: Int) {
        prefs.edit().putInt(KEY_HOUR, hour).putInt(KEY_MINUTE, minute).apply()
        _uiState.update { it.copy(notificationHour = hour, notificationMinute = minute) }
        if (_uiState.value.notificationsEnabled) {
            MorningNotificationScheduler.schedule(getApplication(), hour, minute)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PepTalkApplication)
                SettingsViewModel(application)
            }
        }
    }
}
