package com.example.runningtrackerapp.di.module

import android.app.Application
import com.example.runningtrackerapp.data.local.dao.RunDAO
import com.example.runningtrackerapp.data.local.db.RunningDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getRunningDb(context: Application): RunningDatabase {
        return RunningDatabase.getRunningDB(context)
    }

    @Provides
    @Singleton
    fun getRunDao(runningDb: RunningDatabase): RunDAO {
        return runningDb.getRunDao()
    }
}