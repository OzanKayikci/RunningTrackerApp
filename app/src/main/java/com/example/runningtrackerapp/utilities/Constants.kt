package com.example.runningtrackerapp.utilities

import android.Manifest
import android.graphics.Color

object Constants {
    const val REQUEST_CODE_LOCATION_PERMISSION = 123
    const val REQUEST_CODE_NOTIFICATION_PERMISSION = 111
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,

        )

    const val backgroundLocationPermission =
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    const val postNotificationPermissions = Manifest.permission.POST_NOTIFICATIONS


    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"

    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1 // If it will be less then 1, there will be a bug

    const val LOCATION_UPDATE_INTERVAL = 2000L
    const val FASTEST_LOCATION_INTERVAL = 1000L

    const val POLYLINE_COLOR = Color.RED
    const val POLYLINE_WIDTH = 8f

    const val MAP_ZOOM = 18f

    const val TIMER_UPDATE_INTERVAL = 80L

    const val SHARED_PREFERENCES_NAME = "SharedPref"
    const val KEY_FIRST_TIME_TOGGLE = "KEY_FIRST_TIME_TOGGLE"
    const val KEY_NAME= "KEY_NAME"
    const val KEY_WEIGHT= "KEY_WEIGHT"
    const val KEY_AGE= "KEY_AGE"
    const val KEY_HEIGHT= "KEY_HEIGHT"
}