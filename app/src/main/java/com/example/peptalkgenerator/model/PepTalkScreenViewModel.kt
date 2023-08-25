package com.example.peptalkgenerator.model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.util.query
import com.example.peptalkgenerator.PepTalkApplication
import com.example.peptalkgenerator.data.PepTalk
import com.example.peptalkgenerator.data.PepTalkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.IllegalArgumentException


sealed interface PepTalkUIState {
    data class PepTalk(val pepTalk: String) : PepTalkUIState
    object Error : PepTalkUIState
    object Loading : PepTalkUIState
}


data class PhraseUIState(val pepTalk: Flow<String>)

class PepTalkScreenViewModel (
    private val pepTalkRepository: PepTalkRepository
) : ViewModel() {

    //region This is working

    val currentTalk = pepTalkRepository.getNewTalk()

    fun refreshTalk(){
        viewModelScope.launch {
            Log.d(TAG, "ViewModel - Trying refreshTalk()")
            pepTalkRepository.refreshTalk()
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

    //endregion of working bit
}
