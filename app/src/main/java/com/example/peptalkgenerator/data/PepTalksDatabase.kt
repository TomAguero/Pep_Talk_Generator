package com.example.peptalkgenerator.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

@Database(version =1, entities = [Phrase::class, PepTalk::class], exportSchema = false)
abstract class PepTalksDatabase : RoomDatabase () {
    abstract fun phraseDao (): PhraseDao

    abstract fun pepTalkDao (): PepTalkDao

    companion object {
        @Volatile
        private var Instance: PepTalksDatabase? = null

        fun getDatabase(context: Context): PepTalksDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, PepTalksDatabase::class.java, "pepTalks_database")
                    .fallbackToDestructiveMigration()
                    .createFromAsset("databases/pepTalks_database.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
