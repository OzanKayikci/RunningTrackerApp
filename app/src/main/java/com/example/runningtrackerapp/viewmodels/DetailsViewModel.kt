package com.example.runningtrackerapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.runningtrackerapp.data.local.entities.Run
import com.example.runningtrackerapp.data.local.repositories.RunningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: RunningRepository,
) : ViewModel() {


    private var _runDetails:MutableLiveData<Run> = MutableLiveData()
    val runDetail get() = _runDetails

     fun getRun(id:Int) = _runDetails.postValue(repository.getRunWithId(id))
}