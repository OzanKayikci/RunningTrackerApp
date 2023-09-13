package com.example.runningtrackerapp.data.local.entities

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "running_table")
data class Run(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val img: Bitmap,
    val timestamp: Long,//when our run was
    val avgSpeedInKMH: Float,
    val distanceInMeters: Int,
    val timeInMillis: Long, // how long our run was
    val caloriesBurned: Int
)