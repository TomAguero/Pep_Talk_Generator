package com.example.peptalkgenerator.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PepTalks")
data class PepTalk(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val pepTalk: String,
    val favorite: Boolean?,
    val block: Boolean?
)
