package com.example.peptalkgenerator.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PepTalkRepository @Inject constructor(
    private val phraseDao: PhraseDao,
    private val pepTalkDao: PepTalkDao
) {
    //region Phrases
    /*
    //Commenting out to see if this requeryTrigger is needed or not. I got it off a StackoverFlow post.
    private val requeryTrigger = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
     */

    //Function to pull the different parts of the Pep Talk together
    suspend fun generateNewTalk(): String {
        val greeting = phraseDao.getGreeting()
        val first = phraseDao.getFirst()
        val second = phraseDao.getSecond()
        val ending = phraseDao.getEnding()
        return "$greeting $first $second $ending"
    }

    //delete phrase
    //Not currently implemented, will be added in once we add in functionality for custom phrases.
    @WorkerThread
    suspend fun deletePhrase(phrase: Phrase) {
        phraseDao.deletePhrase(phrase)
    }

    //update phrase
    //9/27/23 - not implemented yet, will be added in once we add in functionality for custom phrases.
    @WorkerThread
    suspend fun updatePhrase(phrase: Phrase) {
        phraseDao.updatePhrase(phrase)
    }

    //insert phrase
    //9/27/23 - not implemented yet, will be added in once we add in functionality for custom phrases.
    @WorkerThread
    suspend fun insertPhrase(phrase: Phrase) {
        phraseDao.updatePhrase(phrase)
    }
    //endregion

    //region pep talks
    //Get list of Favorites from DB
    fun getFavorites(): Flow<List<PepTalk>> = pepTalkDao.getFavoritePepTalks()

    //Get specific pep talks
    fun getPepTalk(id: Int): Flow<PepTalk> = pepTalkDao.getPepTalk(id = id)

    //get Blocked pep talks
    // 9/27/23 - not implemented yet, need to create Block page and other bits
    val blocked: Flow<List<PepTalk>> = pepTalkDao.getBlockedPepTalks()

    //insert pepTalk
    //Used for Favoriting, will also be used for Blocking
    @WorkerThread
    suspend fun insertPepTalk(pepTalk: PepTalk) {
        pepTalkDao.insertPepTalk(pepTalk)
    }

    //update pepTalk
    //9/27/23 - Not implemented yet, actually not sure what the plan for this was.
    @WorkerThread
    suspend fun updatePepTalk(pepTalk: PepTalk) {
        pepTalkDao.updatePepTalk(pepTalk)
    }

    //delete pepTalk
    //Used for deleting Favorites, will also be used for deleting Blocked ones eventually
    @WorkerThread
    suspend fun deletePepTalk(pepTalk: PepTalk) {
        pepTalkDao.deletePepTalk(pepTalk)
    }

    //endregion
}