package com.example.peptalkgenerator.alarm

interface AlarmScheduler {
    fun schedule()
    fun cancel()
    fun rescheduleAlarms()
}