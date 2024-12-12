package com.example.tustracker.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tustracker.repository.MoodRepository
import com.example.tustracker.viewModel.MoodViewModel

// A factory class for creating an instance of MoodViewModel.
class MoodViewModelFactory(private val repository: MoodRepository) : ViewModelProvider.Factory {

    // creates a new instance of MoodViewModel.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return MoodViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
