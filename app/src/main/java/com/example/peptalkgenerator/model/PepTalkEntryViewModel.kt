package com.example.peptalkgenerator.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.peptalkgenerator.data.PepTalk
import com.example.peptalkgenerator.data.PepTalkRepository

data class PepTalkEntryUIState(
    val pepTalkDetails: PepTalkEntryDetails = PepTalkEntryDetails(),
    val isEntryValid: Boolean = false
)

data class PepTalkEntryDetails(
    val id: Int = 0,
    val pepTalk: String = "",
    val favorite: Boolean? = false,
    val block: Boolean? = false
)

//Extension functions to convert PepTalkDetails to PepTalk
fun PepTalkEntryDetails.toPepTalk(): PepTalk = PepTalk(
    id = id,
    pepTalk = pepTalk,
    favorite = favorite,
    block = block
)

//Another Extension func to go from PepTalk to PepTalk details
fun PepTalk.toPepTalkEntryDetails(): PepTalkEntryDetails = PepTalkEntryDetails(
    id = id,
    pepTalk = pepTalk,
    favorite = favorite,
    block = block
)

//Then Extension func to convert PepTalk to PepTalkUI State
fun PepTalk.toPepTalkUIState(isEntryValid: Boolean = false): PepTalkEntryUIState = PepTalkEntryUIState(
    pepTalkDetails = this.toPepTalkEntryDetails(),
    isEntryValid = isEntryValid
)

class PepTalkEntryViewModel (private val pepTalkRepository: PepTalkRepository) : ViewModel () {
    //Holds current PepTalk UI state
    var pepTalkUIState by mutableStateOf(PepTalkEntryUIState())
        private set

    //validate input
    private fun validateInput(uiState: PepTalkEntryDetails = pepTalkUIState.pepTalkDetails): Boolean{
        return with(uiState) {
            pepTalk.isNotBlank() && (favorite == true || block == true)
        }
    }

    //update PepTalkUIState w/ values provided, also trigger input validation
    fun updatePhraseUIState(phraseDetails: PepTalkEntryDetails){
        pepTalkUIState =
            PepTalkEntryUIState(pepTalkDetails = phraseDetails, isEntryValid = validateInput(phraseDetails))
    }

    //finally save the PepTalk
    suspend fun savePepTalk(){
        if(validateInput()){
            pepTalkRepository.insertPepTalk(pepTalkUIState.pepTalkDetails.toPepTalk())
        }
    }
}

