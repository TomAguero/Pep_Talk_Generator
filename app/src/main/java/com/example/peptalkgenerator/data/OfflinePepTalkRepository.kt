package com.example.peptalkgenerator.data
/*
import kotlinx.coroutines.flow.Flow

class OfflinePepTalkRepository(
    private val phraseDao: PhraseDao,
    private val pepTalkDao: PepTalkDao
) : PepTalkRepository {
    //override fun getPhraseStream(type: String): Flow<List<Phrase>> = phraseDao.getPhrase(type)

    override fun getGreeting(): Flow<String?> = phraseDao.getGreeting()

    override fun getFirst(): Flow<String?> = phraseDao.getFirst()

    override fun getSecond(): Flow<String?> = phraseDao.getSecond()

    override fun getEnding(): Flow<String?> = phraseDao.getEnding()

    override fun getTalk(): String {
        return super.getTalk()
    }

    override suspend fun deletePhrase(phrase: Phrase) = phraseDao.deletePhrase(phrase)

    override suspend fun insertPhrase(phrase: Phrase) = phraseDao.insertPhrase(phrase)

    override suspend fun updatePhrase(phrase: Phrase) = phraseDao.updatePhrase(phrase)

    override fun getFavoriteStream(): Flow<List<PepTalk>> = pepTalkDao.getFavoritePepTalks()

    override fun getBlockedStream(): Flow<List<PepTalk>> = pepTalkDao.getBlockedPepTalks()

    override suspend fun deletePepTalk(pepTalk: PepTalk) = pepTalkDao.deletePepTalk(pepTalk)

    override suspend fun updatePepTalk(pepTalk: PepTalk) = pepTalkDao.updatePepTalk(pepTalk)

    override suspend fun insertPepTalk(pepTalk: PepTalk) = pepTalkDao.insertPepTalk(pepTalk)
}

 */