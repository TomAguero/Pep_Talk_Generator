package com.example.peptalkgenerator.workmanager

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.peptalkgenerator.PepTalkApplication
import com.example.peptalkgenerator.data.PepTalkRepository
import com.example.peptalkgenerator.data.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.TimeUnit

class NotificationViewModel(
    application: Application,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val pepTalkRepository: PepTalkRepository
) : ViewModel() {

    private val workManager = WorkManager.getInstance(application)

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

    val uiState: StateFlow<SettingScreenUIState> =
        userPreferencesRepository.reminderEnabledState.map { reminderEnabledState ->
            SettingScreenUIState(reminderEnabledState)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingScreenUIState(reminderEnabledState = true)
        )

    fun setReminderState(
        reminderEnabledState: Boolean
    ) {
        viewModelScope.launch {
            userPreferencesRepository.saveReminderState(reminderEnabledState)
        }
    }

    fun scheduleNotification() {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()

        // Set execution time to 8 AM, or whatever time it is for testing
        dueDate.set(Calendar.HOUR_OF_DAY, 8)

        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }
        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis

        val myWorkRequestBuilder = PeriodicWorkRequestBuilder<NotificationWorker>(
            24, TimeUnit.HOURS //Set this to 24 and TimeUnit.Hours after testing
        )
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .addTag("pepTalkNotification")

        myWorkRequestBuilder.setInputData(
            workDataOf(
                "PEP_TALK" to talkState
            )
        )

        workManager.enqueueUniquePeriodicWork(
            "pepTalkNotification",
            ExistingPeriodicWorkPolicy.KEEP,
            myWorkRequestBuilder.build()
        )
    }

    fun cancelNotification() {
        workManager.cancelAllWorkByTag("pepTalkNotification")
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PepTalkApplication)
                val pepTalkRepository = application.pepTalkRepository
                NotificationViewModel(
                    application = application,
                    application.userPreferencesRepository,
                    pepTalkRepository = pepTalkRepository
                )
            }
        }
    }
}

data class SettingScreenUIState(
    val reminderEnabledState: Boolean

)