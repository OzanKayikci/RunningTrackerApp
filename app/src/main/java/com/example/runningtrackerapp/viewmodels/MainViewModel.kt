package com.example.runningtrackerapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runningtrackerapp.data.local.entities.Run
import com.example.runningtrackerapp.data.local.repositories.RunningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: RunningRepository) : ViewModel() {

    private var _runSortedByDate = repository.getAllRunsSortedByDate()
    val runSortedByDate get() = _runSortedByDate


    fun insertRun(run: Run) = viewModelScope.launch {
        repository.insertRun(run)
    }


}