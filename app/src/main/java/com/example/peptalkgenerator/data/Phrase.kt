package com.example.peptalkgenerator.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
This is the class for the Phrase table
Stores the various parts that will become the pep talk
 */

@Entity(tableName = "phrases")
data class Phrase(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String,
    val saying: String
)
