package com.example.peptalkgenerator.alarm

import java.time.LocalDateTime

data class AlarmItem(
    val alarmTime: LocalDateTime,
    val message: String
)