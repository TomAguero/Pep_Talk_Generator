package com.example.peptalkgenerator

import androidx.lifecycle.ViewModel
import com.example.peptalkgenerator.data.PhraseUIState
import com.example.peptalkgenerator.data.acknowledgements
import com.example.peptalkgenerator.data.greetings
import com.example.peptalkgenerator.data.praises
import com.example.peptalkgenerator.data.salutations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PepTalkViewModel () : ViewModel() {
    //Set UI Flow info
    private val _uiState = MutableStateFlow(PhraseUIState())
    val uiState: StateFlow<PhraseUIState> = _uiState.asStateFlow()

    private lateinit var currentGreeting: String

    private fun getRandomGreeting(): String {
        currentGreeting = greetings.random()
        return currentGreeting
    }

    //Set Acknowledgement info
    private lateinit var currentAcknowledgement: String

    private fun getRandomAcknowledgement(): String {
        currentAcknowledgement = acknowledgements.random()
        return currentAcknowledgement
    }

    //Set Praise info
    private lateinit var currentPraise: String

    private fun getRandomPraise(): String {
        currentPraise = praises.random()
        return currentPraise
    }

    //Set Salutation info
    private lateinit var currentSalutation: String

    private fun getRandomSalutation(): String {
        currentSalutation = salutations.random()
        return currentSalutation
    }

    fun getNewPepTalk(){
        _uiState.value = PhraseUIState(
            currentGreeting = getRandomGreeting(),
            currentAcknowledgement = getRandomAcknowledgement(),
            currentPraise = getRandomPraise(),
            currentSalutation = getRandomSalutation(),
            pepTalk = "$currentGreeting $currentAcknowledgement $currentPraise $currentSalutation"
        )
    }
}