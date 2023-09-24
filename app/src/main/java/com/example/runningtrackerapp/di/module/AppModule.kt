package com.example.runningtrackerapp.di.module

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.runningtrackerapp.data.local.dao.RunDAO
import com.example.runningtrackerapp.data.local.db.RunningDatabase
import com.example.runningtrackerapp.utilities.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext app: Context) = app.getSharedPreferences(
        Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE
    )

    @Singleton
    @Provides
    fun provideName(sharedPref: SharedPreferences) =
        sharedPref.getString(Constants.KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPref: SharedPreferences) =
        sharedPref.getFloat(Constants.KEY_WEIGHT, 80f)

    @Singleton
    @Provides
    fun provideHeight(sharedPref: SharedPreferences) =
        sharedPref.getInt(Constants.KEY_HEIGHT, 178)

    @Singleton
    @Provides
    fun provideAge(sharedPref: SharedPreferences) =
        sharedPref.getInt(Constants.KEY_AGE, 23)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) =
        sharedPref.getBoolean(Constants.KEY_FIRST_TIME_TOGGLE, true)

}