package com.example.tustracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tustracker.classes.MoodClass
import com.example.tustracker.repository.MoodRepository
import kotlinx.coroutines.launch

// responsible for preparing and managing data for the UI.
class MoodViewModel(private val repository: MoodRepository) : ViewModel() {

    // LiveData that holds a list of all mood entries from repository
    val allMoods: LiveData<List<MoodClass>> = repository.allMoods

    // inserts a new mood entry into the database.
    fun insert(mood: MoodClass) = viewModelScope.launch {
        repository.insert(mood)
    }

    // deletes a mood entry from the database.
    fun delete(mood: MoodClass) = viewModelScope.launch {
        repository.delete(mood)
    }
}
