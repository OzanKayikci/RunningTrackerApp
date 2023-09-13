package com.example.runningtrackerapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.runningtrackerapp.data.local.Converters
import com.example.runningtrackerapp.data.local.dao.RunDAO
import com.example.runningtrackerapp.data.local.entities.Run

@Database(
    entities = [Run::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RunningDatabase : RoomDatabase() {

    abstract fun getRunDao(): RunDAO

    companion object {
        private var dbINSTANCE: RunningDatabase? = null
        fun getRunningDB(context: Context): RunningDatabase {
            if (dbINSTANCE == null) {
                dbINSTANCE = Room.databaseBuilder<RunningDatabase>(
                    context.applicationContext,
                    RunningDatabase::class.java,
                    "running_database"
                ).allowMainThreadQueries().build()
            }
            return dbINSTANCE!!
        }
    }
}