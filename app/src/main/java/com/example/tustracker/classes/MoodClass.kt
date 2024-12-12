package com.example.tustracker.classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_entries")
data class MoodClass(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mood: Float, // Mood value from the slider (0.0 - 1.0) to reflect mood
    val timestamp: Long = System.currentTimeMillis() // To track when the mood was logged
) {
    constructor() : this(0, 0f)
}

