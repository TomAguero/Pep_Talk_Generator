package com.example.peptalkgenerator.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PhraseDao {
    @Insert
    suspend fun insertPhrase(phrase: Phrase)

    @Update
    suspend fun updatePhrase(phrase: Phrase)

    @Delete
    suspend fun deletePhrase(phrase: Phrase)

    @Query("SELECT * from phrases WHERE type = :type")
    fun getPhrase(type: String): Flow<List<Phrase>>
}