package com.example.peptalkgenerator.workmanager

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.peptalkgenerator.PepTalkApplication
import com.example.peptalkgenerator.data.PepTalkRepository
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class NotificationViewModel(
    application: Application,
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

    internal fun scheduleNotification(
    ) {
        val myWorkRequestBuilder = PeriodicWorkRequestBuilder<NotificationWorker>(
            15, TimeUnit.MINUTES
        )
            .addTag("pepTalkNotification")

        myWorkRequestBuilder.setInputData(
            workDataOf(
                "PEP_TALK" to talkState
            )
        )

        workManager.enqueue(myWorkRequestBuilder.build())
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
                    pepTalkRepository = pepTalkRepository
                )
            }
        }
    }
}