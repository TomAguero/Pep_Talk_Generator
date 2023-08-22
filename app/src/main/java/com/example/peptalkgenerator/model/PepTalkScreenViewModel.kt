package com.example.peptalkgenerator.model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.peptalkgenerator.PepTalkApplication
import com.example.peptalkgenerator.data.PepTalkRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface PepTalkUIState {
    data class PepTalk(val pepTalk: String) : PepTalkUIState
    object Error : PepTalkUIState
    object Loading : PepTalkUIState
}

class PepTalkScreenViewModel (
    private val pepTalkRepository: PepTalkRepository
) : ViewModel(){

    init {
        Log.d(TAG, "ViewModel Initialized: $this (hashCode: ${hashCode()})")
    }

    var pepTalkUIState: PepTalkUIState by mutableStateOf(PepTalkUIState.Loading)
        private set

    init {
        getPepTalk()
    }

    fun getPepTalk(){
        viewModelScope.launch {
            pepTalkUIState = PepTalkUIState.Loading
            pepTalkUIState = try {
                val pepTalk = pepTalkRepository.getTalk()
                PepTalkUIState.PepTalk(pepTalk)
            } catch (e: IOException){
                PepTalkUIState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PepTalkApplication)
                val pepTalkRepository = application.container.pepTalkRepository
                PepTalkScreenViewModel(pepTalkRepository = pepTalkRepository)
            }
        }
    }
}
