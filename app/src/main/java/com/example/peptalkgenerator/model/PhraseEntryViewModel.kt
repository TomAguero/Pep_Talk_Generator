package com.example.peptalkgenerator.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.peptalkgenerator.data.PepTalkRepository
import com.example.peptalkgenerator.data.Phrase

data class PhraseUIState(
    val phraseDetails: PhraseDetails = PhraseDetails(),
    val isEntryValid: Boolean = false
)

data class PhraseDetails(
    val id: Int = 0,
    val type: String = "",
    val saying: String = ""
)

//Extension functions to convert PhraseDetails to Phrase
fun PhraseDetails.toPhrase(): Phrase = Phrase(
    id = id,
    type = type,
    saying = saying
)

//Another Extension func to go from Phrase to Phrase details
fun Phrase.toPhraseDetails(): PhraseDetails = PhraseDetails(
    id = id,
    type = type,
    saying = saying
)

//Then Extension func to convert Phrase to PhraseUI State
fun Phrase.toPhraseUIState(isEntryValid: Boolean = false): PhraseUIState = PhraseUIState(
    phraseDetails = this.toPhraseDetails(),
    isEntryValid = isEntryValid
)

class PhraseEntryViewModel (private val phraseRepository: PepTalkRepository) : ViewModel () {
    //Holds current phrase UI state
    var phraseUIState by mutableStateOf(PhraseUIState())
        private set

    //validate input
    private fun validateInput(uiState: PhraseDetails = phraseUIState.phraseDetails): Boolean{
        return with(uiState) {
            type.isNotBlank() && saying.isNotBlank()
        }
    }

    //update phraseUIState w/ values provided, also trigger input validation
    fun updatePhraseUIState(phraseDetails: PhraseDetails){
        phraseUIState =
            PhraseUIState(phraseDetails = phraseDetails, isEntryValid = validateInput(phraseDetails))
    }

    //finally save the phrase
    suspend fun savePhrase(){
        if(validateInput()){
            phraseRepository.insertPhrase(phraseUIState.phraseDetails.toPhrase())
        }
    }
}

