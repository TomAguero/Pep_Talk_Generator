package app.peptalkgenerator.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class PepTalkRepository(
    private val phraseDao: PhraseDao,
    private val pepTalkDao: PepTalkDao
) {
    //region Phrases
    //Functional to pull the different parts of the Pep Talk together
    suspend fun generateNewTalk(): String {
        val greeting = phraseDao.getGreeting() ?: ""
        val first = phraseDao.getFirst() ?: ""
        val second = phraseDao.getSecond() ?: ""
        val ending = phraseDao.getEnding() ?: ""
        return "$greeting $first $second $ending".trim()
    }

    fun getAllPhrases(): Flow<List<Phrase>> = phraseDao.getAllPhrases()

    @WorkerThread
    suspend fun deletePhrase(phrase: Phrase) {
        phraseDao.deletePhrase(phrase)
    }

    @WorkerThread
    suspend fun updatePhrase(phrase: Phrase) {
        phraseDao.updatePhrase(phrase)
    }

    @WorkerThread
    suspend fun insertPhrase(phrase: Phrase) {
        phraseDao.insertPhrase(phrase)
    }
    //endregion

    //region pep talks
    //Get list of Favorites from DB
    fun getFavorites(): Flow<List<PepTalk>> = pepTalkDao.getFavoritePepTalks()

    //Get specific pep talks
    fun getPepTalk(id: Int): Flow<PepTalk> = pepTalkDao.getPepTalk(id = id)

    //insert pepTalk
    //Used for Favoriting, will also be used for Blocking
    @WorkerThread
    suspend fun insertPepTalk(pepTalk: PepTalk) {
        pepTalkDao.insertPepTalk(pepTalk)
    }

    //delete pepTalk
    //Used for deleting Favorites, will also be used for deleting Blocked ones eventually
    @WorkerThread
    suspend fun deletePepTalk(pepTalk: PepTalk) {
        pepTalkDao.deletePepTalk(pepTalk)
    }

    //endregion
}