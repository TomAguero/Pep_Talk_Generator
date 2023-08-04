package com.example.peptalkgenerator.data

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import com.example.peptalkgenerator.R

data class PhraseUIState(
    val currentGreeting: String = "Click",
    val currentAcknowledgement: String = "New Pep Talk",
    val currentPraise: String = "to generate a new",
    val currentSalutation: String = "pep talk.",
    val pepTalk: String = "$currentGreeting $currentAcknowledgement $currentPraise $currentSalutation"
)
