package com.example.peptalkgenerator.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.peptalkgenerator.PepTalkApplication
import com.example.peptalkgenerator.data.PepTalkRepository
import kotlinx.coroutines.launch

class PepTalkScreenViewModel (
    private val pepTalkRepository: PepTalkRepository
) : ViewModel() {

    private val _talkState = mutableStateOf<String>("")
    val talkState: String by _talkState

    init {
        refreshTalkState()
    }

    fun refreshTalkState() {
        viewModelScope.launch {
            _talkState.value = pepTalkRepository.generateNewTalk()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PepTalkApplication)
                val pepTalkRepository = application.pepTalkRepository
                PepTalkScreenViewModel(pepTalkRepository = pepTalkRepository)
            }
        }
    }

}
