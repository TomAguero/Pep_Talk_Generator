package com.example.peptalkgenerator.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/*
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
 */

class PepTalkRepository(
    private val phraseDao: PhraseDao,
    private val pepTalkDao: PepTalkDao
){
    //region Phrases
    //get phrase
    val greeting: Flow<String?> = phraseDao.getGreeting()

    val first: Flow<String?> = phraseDao.getFirst()

    val second: Flow<String?> = phraseDao.getSecond()

    val ending: Flow<String?> = phraseDao.getEnding()

    val firstHalf = greeting.combine(first) { greeting, first ->
        "$greeting $first"
    }

    val secondHalf = second.combine(ending) { second, ending ->
        "$second $ending"
    }

    val currentTalk = firstHalf.combine(secondHalf) {firstHalf, secondHalf->
        "$firstHalf $secondHalf"
    }

    //delete phrase
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deletePhrase(phrase: Phrase){
        phraseDao.deletePhrase(phrase)
    }

    //update phrase
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updatePhrase(phrase: Phrase){
        phraseDao.updatePhrase(phrase)
    }

    //insert phrase
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPhrase(phrase: Phrase){
        phraseDao.updatePhrase(phrase)
    }
    //endregion

    //region Pep talks
    // get favorites
    val favorites: Flow<List<PepTalk>> = pepTalkDao.getFavoritePepTalks()

    //get blocks
    val blocked: Flow<List<PepTalk>> = pepTalkDao.getBlockedPepTalks()

    //insert pepTalk
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPepTalk(pepTalk: PepTalk){
        pepTalkDao.insertPepTalk(pepTalk)
    }

    //update pepTalk
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updatePepTalk(pepTalk: PepTalk){
        pepTalkDao.updatePepTalk(pepTalk)
    }

    //delete pepTalk
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deletePepTalk(pepTalk: PepTalk){
        pepTalkDao.deletePepTalk(pepTalk)
    }

    //endregion
}