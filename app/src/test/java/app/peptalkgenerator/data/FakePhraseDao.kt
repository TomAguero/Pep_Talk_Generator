package app.peptalkgenerator.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakePhraseDao : PhraseDao {

    private val phrases = mutableListOf<Phrase>()
    private val phrasesFlow = MutableStateFlow<List<Phrase>>(emptyList())

    override suspend fun insertPhrase(phrase: Phrase) {
        val newPhrase = if (phrase.id == 0) phrase.copy(id = (phrases.maxOfOrNull { it.id } ?: 0) + 1) else phrase
        phrases.add(newPhrase)
        emitUpdate()
    }

    override suspend fun updatePhrase(phrase: Phrase) {
        val index = phrases.indexOfFirst { it.id == phrase.id }
        if (index >= 0) {
            phrases[index] = phrase
            emitUpdate()
        }
    }

    override suspend fun deletePhrase(phrase: Phrase) {
        phrases.removeAll { it.id == phrase.id }
        emitUpdate()
    }

    override fun getAllPhrases(): Flow<List<Phrase>> =
        phrasesFlow.map { it.sortedWith(compareBy({ it.type }, { it.saying })) }

    override suspend fun getGreeting(): String? =
        phrases.filter { it.type == "greeting" }.randomOrNull()?.saying

    override suspend fun getFirst(): String? =
        phrases.filter { it.type == "first" }.randomOrNull()?.saying

    override suspend fun getSecond(): String? =
        phrases.filter { it.type == "second" }.randomOrNull()?.saying

    override suspend fun getEnding(): String? =
        phrases.filter { it.type == "ending" }.randomOrNull()?.saying

    private fun emitUpdate() {
        phrasesFlow.value = phrases.toList()
    }
}
