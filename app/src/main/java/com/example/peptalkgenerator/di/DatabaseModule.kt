package com.example.peptalkgenerator.di

import android.content.Context
import androidx.room.Room
import com.example.peptalkgenerator.data.PepTalkDao
import com.example.peptalkgenerator.data.PepTalksDatabase
import com.example.peptalkgenerator.data.PhraseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun providePepTalkDao(database: PepTalksDatabase): PepTalkDao {
        return database.pepTalkDao()
    }

    @Provides
    fun providePhraseDao(database: PepTalksDatabase): PhraseDao {
        return database.phraseDao()
    }

    private var Instance: PepTalksDatabase? = null

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext appContext: Context): PepTalksDatabase {
        return Instance ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                appContext,
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