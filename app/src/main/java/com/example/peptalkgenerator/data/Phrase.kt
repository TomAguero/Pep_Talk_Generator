package com.example.peptalkgenerator.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrases")
data class Phrase(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String,
    val saying: String
)
