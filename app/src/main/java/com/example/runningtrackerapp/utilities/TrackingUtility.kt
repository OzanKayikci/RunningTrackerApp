package com.example.runningtrackerapp.utilities

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.example.runningtrackerapp.utilities.Constants.backgroundLocationPermission
import com.example.runningtrackerapp.utilities.Constants.locationPermissions

object TrackingUtility {
    fun hasLocationPermissions(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // For Android 11 and higher, include background location permission check
            for (permission in locationPermissions.plus(backgroundLocationPermission)) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        } else {
            // For Android versions lower than 11, check only fine and coarse location permissions
            for (permission in locationPermissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

}