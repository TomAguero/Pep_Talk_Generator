package com.example.peptalkgenerator.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.peptalkgenerator.PepTalkApplication
import com.example.peptalkgenerator.data.PepTalkRepository
import com.example.peptalkgenerator.data.Phrase
import javax.inject.Inject

data class PhraseEntryUIState(
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
fun Phrase.toPhraseEntryUIState(isEntryValid: Boolean = false): PhraseEntryUIState =
    PhraseEntryUIState(
        phraseDetails = this.toPhraseDetails(),
        isEntryValid = isEntryValid
    )

class PhraseEntryViewModel(

) : ViewModel() {

    @Inject
    lateinit var pepTalkRepository: PepTalkRepository

    //Holds current phrase UI state
    var phraseEntryUIState by mutableStateOf(PhraseEntryUIState())
        private set

    //validate input
    private fun validateInput(uiState: PhraseDetails = phraseEntryUIState.phraseDetails): Boolean {
        return with(uiState) {
            type.isNotBlank() && saying.isNotBlank()
        }
    }

    //update phraseUIState w/ values provided, also trigger input validation
    fun updatePhraseUIState(phraseDetails: PhraseDetails) {
        phraseEntryUIState =
            PhraseEntryUIState(
                phraseDetails = phraseDetails,
                isEntryValid = validateInput(phraseDetails)
            )
    }

    //finally save the phrase
    suspend fun savePhrase() {
        if (validateInput()) {
            pepTalkRepository.insertPhrase(phraseEntryUIState.phraseDetails.toPhrase())
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PepTalkApplication)

                PhraseEntryViewModel()
            }
        }
    }
}

