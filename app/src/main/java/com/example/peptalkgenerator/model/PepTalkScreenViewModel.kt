package com.example.peptalkgenerator.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peptalkgenerator.data.PepTalkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PepTalkScreenViewModel @Inject constructor(
    private val pepTalkRepository: PepTalkRepository
) : ViewModel() {

    private val _talkState = mutableStateOf("")
    val talkState: String by _talkState

    var pepTalkUiState by mutableStateOf(PepTalkUiState())
        private set

    init {
        refreshTalkState()
    }

    fun refreshTalkState() {
        viewModelScope.launch {
            _talkState.value = pepTalkRepository.generateNewTalk()
        }
    }

    suspend fun favoritePepTalk() {
        pepTalkRepository.insertPepTalk(pepTalkUiState.pepTalkDetails.toPepTalk())
    }
}

data class PepTalkUiState(
    val pepTalkDetails: PepTalkDetails = PepTalkDetails()
)
