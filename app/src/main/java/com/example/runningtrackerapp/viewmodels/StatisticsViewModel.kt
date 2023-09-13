package com.example.runningtrackerapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.runningtrackerapp.data.local.repositories.RunningRepository
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(val repository: RunningRepository) : ViewModel() {
}