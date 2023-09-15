package com.example.runningtrackerapp.utilities

import android.Manifest

object Constants {
    const val REQUEST_CODE_LOCATION_PERMISSION = 123

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val backgroundLocationPermission =
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
}