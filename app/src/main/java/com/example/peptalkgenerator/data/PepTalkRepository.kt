package com.example.peptalkgenerator.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class PepTalkRepository(
    private val phraseDao: PhraseDao,
    private val pepTalkDao: PepTalkDao
){
    //region Phrases
    private val requeryTrigger = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    suspend fun generateNewTalk(): String {
        val greeting = phraseDao.getGreeting()
        val first = phraseDao.getFirst()
        val second = phraseDao.getSecond()
        val ending = phraseDao.getEnding()
        return "$greeting $first $second $ending"
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