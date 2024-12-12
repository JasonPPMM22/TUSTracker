package com.example.tustracker.DAO

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tustracker.classes.MoodClass

// Defines the Room database for the mood tracker
@Database(entities = [MoodClass::class], version = 1, exportSchema = false)
abstract class MoodDatabase : RoomDatabase() {

    // Allows access to the methods defined in the DAO interface.
    abstract fun todoDao(): DAO

    // ensures that only one instance of the database is created.
    companion object {
        @Volatile
        private var INSTANCE: MoodDatabase? = null

        // This function retrieves the instance of the database.
        fun getDatabase(context: Context): MoodDatabase {
            return INSTANCE ?: synchronized(this) {
                // If the database instance is not there, create a new one using databaseBuilder.
                val instance = Room.databaseBuilder(
                    context.applicationContext, MoodDatabase::class.java, "mood_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
