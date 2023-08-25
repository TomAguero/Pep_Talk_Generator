package com.example.peptalkgenerator.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PhraseDao {
    @Insert
    suspend fun insertPhrase(phrase: Phrase)

    @Update
    suspend fun updatePhrase(phrase: Phrase)

    @Delete
    suspend fun deletePhrase(phrase: Phrase)

    @Query("SELECT saying from phrases WHERE type = 'greeting' ORDER BY RANDOM() LIMIT 1")
    suspend fun getGreeting(): String?

    @Query("SELECT saying from phrases WHERE type = 'first' ORDER BY RANDOM() LIMIT 1")
    suspend fun getFirst(): String?

    @Query("SELECT saying from phrases WHERE type = 'second' ORDER BY RANDOM() LIMIT 1")
    suspend fun getSecond(): String?

    @Query("SELECT saying from phrases WHERE type = 'ending' ORDER BY RANDOM() LIMIT 1")
    suspend fun getEnding(): String?
}