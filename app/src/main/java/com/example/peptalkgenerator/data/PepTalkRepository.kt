package com.example.peptalkgenerator.data

import kotlinx.coroutines.flow.Flow

interface PepTalkRepository {
    //region Phrases
    //get phrase
    fun getGreeting(): Flow<String?>

    fun getFirst(): Flow<String?>

    fun getSecond(): Flow<String?>

    fun getEnding(): Flow<String?>

    fun getTalk(): String = "${getGreeting()} ${getFirst()} ${getSecond()} ${getEnding()}"


    //delete phrase
    suspend fun deletePhrase(phrase: Phrase)

    //update phrase
    suspend fun updatePhrase(phrase: Phrase)

    //insert phrase
    suspend fun insertPhrase(phrase: Phrase)
    //endregion

    //region Pep talks
    // get favorites
    fun getFavoriteStream():Flow<List<PepTalk>>

    //get blocks
    fun getBlockedStream():Flow<List<PepTalk>>

    //insert pepTalk
    suspend fun insertPepTalk(pepTalk: PepTalk)

    //update pepTalk
    suspend fun updatePepTalk(pepTalk: PepTalk)

    //delete pepTalk
    suspend fun deletePepTalk(pepTalk: PepTalk)

    //endregion
}