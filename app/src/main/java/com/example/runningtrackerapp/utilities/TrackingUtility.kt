package com.example.runningtrackerapp.utilities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import com.example.runningtrackerapp.services.Polyline
import com.example.runningtrackerapp.utilities.Constants.backgroundLocationPermission
import com.example.runningtrackerapp.utilities.Constants.locationPermissions
import com.example.runningtrackerapp.utilities.Constants.postNotificationPermissions
import java.util.concurrent.TimeUnit

object TrackingUtility {

    fun hasLocationPermissions(context: Context): Boolean {
        val fineLocationGranted = PermissionChecker.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PermissionChecker.PERMISSION_GRANTED

        val coarseLocationGranted = PermissionChecker.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PermissionChecker.PERMISSION_GRANTED

//        val backgroundLocationGranted =
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                PermissionChecker.checkSelfPermission(
//                    context,
//                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
//                ) == PermissionChecker.PERMISSION_GRANTED
//            } else {
//                true // On versions prior to Android 10, background location is not required.
//            }

        return fineLocationGranted && coarseLocationGranted
    }

    fun hasNotificationPermission(context: Context): Boolean {
        return PermissionChecker.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PermissionChecker.PERMISSION_GRANTED
    }


    fun calculatePolylineLength(polyline: Polyline): Float {
        var distance = 0f
        for (i in 0..polyline.size - 2) {
            val pos1 = polyline[i]
            val pos2 = polyline[i + 1]


            val result = FloatArray(1)
            Location.distanceBetween(
                pos1.latitude,
                pos1.longitude,
                pos2.latitude,
                pos2.longitude,
                result
            )
            distance += result[0]
        }
        return distance
    }

    fun getFormattedStopWatchTime(ms: Long, includeMillis: Boolean = false): String {

        var milliseconds: Long = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)

        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)

        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        val timeString = "${if (hours < 10) "0" else ""}$hours:" +
                "${if (minutes < 10) "0" else ""}$minutes:" +
                "${if (seconds < 10) "0" else ""}$seconds"
        if (!includeMillis) {
            return timeString
        }

        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        milliseconds /= 10
        val timeStringWithMillis =
            timeString + ":${if (milliseconds < 10) "0" else ""}$milliseconds"
        return timeStringWithMillis
    }


//    fun hasLocationPermissions(context: Context): Boolean {
//        locationPermissions.plus(postNotificationPermissions)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            // For Android 11 and higher, include background location permission check
//            for (permission in locationPermissions.plus(backgroundLocationPermission)) {
//                if (ActivityCompat.checkSelfPermission(
//                        context,
//                        permission
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    return false
//                }
//            }
//        } else {
//            // For Android versions lower than 11, check only fine and coarse location permissions
//            for (permission in locationPermissions) {
//                if (ActivityCompat.checkSelfPermission(
//                        context,
//                        permission
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    return false
//                }
//            }
//        }
//        return true
//    }


}