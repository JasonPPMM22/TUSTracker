package com.example.tustracker.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tustracker.classes.MoodClass
import kotlinx.coroutines.flow.Flow

// Data Access Object interface that defines the database operations for mood entries.
@Dao
interface DAO {

    // On insertion of a mood entry into the database. If there's a conflict, it is replaced
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mood: MoodClass)

    // Fetches all mood entries from the 'mood_entries' table.
    @Query("SELECT * FROM mood_entries")
    fun getAllMoods(): Flow<List<MoodClass>> // lists entries to mood table

    // Query to delete a specific mood entry by its unique 'id'.
    @Query("DELETE FROM mood_entries WHERE id = :id")
    suspend fun deleteMoodById(id: Int)
}
