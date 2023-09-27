package com.example.peptalkgenerator.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
This is the class for the PepTalk table
Stores Favorited/Blocked pep talks
 */

@Entity(tableName = "PepTalks")
data class PepTalk(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val pepTalk: String,
    val favorite: Boolean?,
    val block: Boolean?
)
