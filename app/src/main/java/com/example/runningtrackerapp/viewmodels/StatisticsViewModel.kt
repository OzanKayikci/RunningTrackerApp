package com.example.runningtrackerapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.runningtrackerapp.data.local.repositories.RunningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(val repository: RunningRepository) : ViewModel() {

    val totalTimeRun = repository.getTotalTimesInMillis()
    val totalDistance = repository.getTotalDistance()
    val totalCaloriesBurned = repository.getTotalCaloriesBurned()
    val getTotalAvgSpeed = repository.getTotalAvgSpeed()

    val runsSortedByDate = repository.getAllRunsSortedByDate()


}