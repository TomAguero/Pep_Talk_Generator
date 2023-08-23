package com.example.peptalkgenerator.data

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version =1, entities = [Phrase::class, PepTalk::class], exportSchema = false)
abstract class PepTalksDatabase : RoomDatabase () {
    abstract fun phraseDao (): PhraseDao

    abstract fun pepTalkDao (): PepTalkDao

    companion object {
        @Volatile
        private var Instance: PepTalksDatabase? = null

        fun getDatabase(context: Context): PepTalksDatabase {
            return Instance ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PepTalksDatabase::class.java,
                    "pepTalks_db"
                )
                    .createFromAsset("database/pepTalks.db")
                    .build()
                Instance = instance
                //return instance
                instance
            }
        }
    }
}

