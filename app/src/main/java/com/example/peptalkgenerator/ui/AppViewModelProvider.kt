package com.example.peptalkgenerator.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.peptalkgenerator.PepTalkApplication
import com.example.peptalkgenerator.model.PepTalkEntryViewModel
import com.example.peptalkgenerator.model.PepTalkScreenViewModel
import com.example.peptalkgenerator.model.PhraseEntryViewModel

/**
 * Provides factory to create instance of ViewModel for entire app
 */

object AppViewModelProvider {
    val Factory = viewModelFactory {
        //Initializer for Phrase Entry ViewModel
        initializer {
            PhraseEntryViewModel(PepTalkApplication().container.pepTalkRepository)
        }

        //Initializer for PepTalk Entry ViewModel
        initializer {
            PepTalkEntryViewModel(PepTalkApplication().container.pepTalkRepository)
        }

        //Initialize the AppScreen View Model
        initializer {
            PepTalkScreenViewModel(
                //this.createSavedStateHandle(),
                pepTalkApplication().container.pepTalkRepository
            )
        }
    }
}

fun CreationExtras.pepTalkApplication(): PepTalkApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as PepTalkApplication)