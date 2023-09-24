package com.example.runningtrackerapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runningtrackerapp.data.local.entities.Run
import com.example.runningtrackerapp.data.local.repositories.RunningRepository
import com.example.runningtrackerapp.utilities.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: RunningRepository) : ViewModel() {

    private var runsSortedByDate = repository.getAllRunsSortedByDate()
    private var runsSortedByDistance = repository.getAllRunsSortedByDistance()
    private var runsSortedByCalories = repository.getAllRunsSortedByCaloriesBurned()
    private var runsSortedByTimeInMillis = repository.getAllRunsSortedByTimeInMillis()
    private var runsSortedByAvgSpeed = repository.getAllRunsSortedByAvgSpeed()

    val runs = MediatorLiveData<List<Run>>()
    var sortType = SortType.DATE

    init {
        fun addSourceIfMatches(source: LiveData<List<Run>>, sortType: SortType) {
            runs.addSource(source) {
                // we must add source all data. After that we set the value
                if (this.sortType == sortType) {
                    runs.value = it
                }
            }
        }

        addSourceIfMatches(runsSortedByDate, SortType.DATE)
        addSourceIfMatches(runsSortedByAvgSpeed, SortType.AVG_SPEED)
        addSourceIfMatches(runsSortedByCalories, SortType.CALORIES_BURNED)
        addSourceIfMatches(runsSortedByDistance, SortType.DISTANCE)
        addSourceIfMatches(runsSortedByTimeInMillis, SortType.RUNNING_TIME)

    }

    fun sortRuns(sortType: SortType) = when (sortType) {
        SortType.DATE -> runsSortedByDate.value.let { runs.value = it }
        SortType.RUNNING_TIME -> runsSortedByTimeInMillis.value.let { runs.value = it }
        SortType.AVG_SPEED -> runsSortedByAvgSpeed.value.let { runs.value = it }
        SortType.DISTANCE -> runsSortedByDistance.value.let { runs.value = it }
        SortType.CALORIES_BURNED -> runsSortedByCalories.value.let { runs.value = it }

    }.also {
        this.sortType = sortType
    }

    fun insertRun(run: Run) = viewModelScope.launch {
        repository.insertRun(run)
    }


}