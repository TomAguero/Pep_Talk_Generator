package app.peptalkgenerator.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/*
DAO for the Phrases
 */

@Dao
interface PhraseDao {
    @Insert
    suspend fun insertPhrase(phrase: Phrase)

    @Update
    suspend fun updatePhrase(phrase: Phrase)

    @Delete
    suspend fun deletePhrase(phrase: Phrase)

    @Query("SELECT * FROM phrases ORDER BY type, saying")
    fun getAllPhrases(): Flow<List<Phrase>>

    @Query("SELECT saying from phrases WHERE type = 'greeting' ORDER BY RANDOM() LIMIT 1")
    suspend fun getGreeting(): String?

    @Query("SELECT saying from phrases WHERE type = 'first' ORDER BY RANDOM() LIMIT 1")
    suspend fun getFirst(): String?

    @Query("SELECT saying from phrases WHERE type = 'second' ORDER BY RANDOM() LIMIT 1")
    suspend fun getSecond(): String?

    @Query("SELECT saying from phrases WHERE type = 'ending' ORDER BY RANDOM() LIMIT 1")
    suspend fun getEnding(): String?

    @Query("SELECT saying FROM phrases WHERE type = 'greeting' AND saying NOT IN (:blocked) ORDER BY RANDOM() LIMIT 1")
    suspend fun getGreetingExcluding(blocked: List<String>): String?

    @Query("SELECT saying FROM phrases WHERE type = 'first' AND saying NOT IN (:blocked) ORDER BY RANDOM() LIMIT 1")
    suspend fun getFirstExcluding(blocked: List<String>): String?

    @Query("SELECT saying FROM phrases WHERE type = 'second' AND saying NOT IN (:blocked) ORDER BY RANDOM() LIMIT 1")
    suspend fun getSecondExcluding(blocked: List<String>): String?

    @Query("SELECT saying FROM phrases WHERE type = 'ending' AND saying NOT IN (:blocked) ORDER BY RANDOM() LIMIT 1")
    suspend fun getEndingExcluding(blocked: List<String>): String?
}