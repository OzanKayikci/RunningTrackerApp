package com.example.runningtrackerapp.data.local.repositories

import com.example.runningtrackerapp.data.local.dao.RunDAO
import com.example.runningtrackerapp.data.local.entities.Run
import javax.inject.Inject

class RunningRepository @Inject constructor(
    val runDao: RunDAO
) {


    suspend fun insertRun(run: Run) = runDao.insertRun(run)
    suspend fun deleteRun(run: Run) = runDao.deleteRun(run)

    fun getRunWithId(id: Int) = runDao.getRunWithId(id)
    fun getAllRunsSortedByDate() = runDao.getAllRunsSortedByDate()
    fun getAllRunsSortedByDistance() = runDao.getAllRunsSortedByDistance()
    fun getAllRunsSortedByAvgSpeed() = runDao.getAllRunsSortedByAvgSpeed()
    fun getAllRunsSortedByTimeInMillis() = runDao.getAllRunsSortedByTimeInMillis()
    fun getAllRunsSortedByCaloriesBurned() = runDao.getAllRunsSortedByCaloriesBurned()

    fun getTotalAvgSpeed() = runDao.getTotalAvgSpeed()
    fun getTotalDistance() = runDao.getTotalDistance()
    fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()
    fun getTotalTimesInMillis() = runDao.getTotalMillis()

}