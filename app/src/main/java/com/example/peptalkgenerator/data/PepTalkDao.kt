package com.example.peptalkgenerator.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PepTalkDao {
    @Insert
    suspend fun insertPepTalk(pepTalk: PepTalk)

    @Update
    suspend fun updatePepTalk(pepTalk: PepTalk)

    @Delete
    suspend fun deletePepTalk(pepTalk: PepTalk)

    @Query ("SELECT * from PepTalks where id = :id")
    fun getPepTalk(id: Int): Flow<PepTalk>

    @Query ("SELECT * from PepTalks where favorite = 1")
    fun getFavoritePepTalks(): Flow<List<PepTalk>>

    @Query ("SELECT * from PepTalks where block = 1")
    fun getBlockedPepTalks(): Flow<List<PepTalk>>
}