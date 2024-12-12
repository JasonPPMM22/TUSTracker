package com.example.tustracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.tustracker.DAO.DAO
import com.example.tustracker.classes.MoodClass

// handles data operations for the mood entries.
class MoodRepository(private val moodDao: DAO) {

    // 'allMoods' is a LiveData object that holds a list of all mood entries.
    val allMoods: LiveData<List<MoodClass>> = moodDao.getAllMoods().asLiveData()

    // A suspend function that adds a new mood entry into the database.
    suspend fun insert(mood: MoodClass) {
        moodDao.insert(mood)
    }

    // A suspend function that deletes a mood entry from the database by its ID.
    suspend fun delete(mood: MoodClass) {
        moodDao.deleteMoodById(mood.id)
    }
}
