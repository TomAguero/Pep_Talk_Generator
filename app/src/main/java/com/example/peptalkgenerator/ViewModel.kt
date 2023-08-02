package com.example.peptalkgenerator

import androidx.lifecycle.ViewModel
import com.example.peptalkgenerator.data.PhraseUIState
import com.example.peptalkgenerator.data.greetings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GreetingViewModel () : ViewModel() {

    private val _uiState = MutableStateFlow(PhraseUIState())
    val uiState: StateFlow<PhraseUIState> = _uiState.asStateFlow()

    private lateinit var currentGreeting: String

    private fun getRandomGreeting(): String {
        currentGreeting = greetings.random()
        return currentGreeting
    }

    fun getNewPepTalk(){
        _uiState.value = PhraseUIState(currentGreeting = getRandomGreeting())
    }
}