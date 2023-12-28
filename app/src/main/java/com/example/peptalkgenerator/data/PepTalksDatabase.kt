package com.example.peptalkgenerator.data

import androidx.room.Database
import androidx.room.RoomDatabase

/*
Create the Room DB
 */

@Database(version = 1, entities = [Phrase::class, PepTalk::class], exportSchema = false)
abstract class PepTalksDatabase : RoomDatabase() {
    abstract fun phraseDao(): PhraseDao

    abstract fun pepTalkDao(): PepTalkDao

}

