package com.example.runningtrackerapp.utilities

import android.content.Context
import android.widget.TextView
import com.example.runningtrackerapp.R
import com.example.runningtrackerapp.data.local.entities.Run
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CustomMarkerView(
    val runs: List<Run>,
    c: Context,
    layoutId: Int,

    ) : MarkerView(c, layoutId) {

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())

    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        if (e == null) {
            return
        }

        val currentRunId = e.x.toInt()
        val run = runs[currentRunId]


        val calendar = Calendar.getInstance().apply {
            timeInMillis = run.timestamp
        }

        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        this.findViewById<TextView>(R.id.tvDate).text = dateFormat.format(calendar.time)

//        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
//        startingTime.text = timeFormat.format(calendar.time)

        val avgSpeed = "${run.avgSpeedInKMH}km/h"
        this.rootView.findViewById<TextView>(R.id.tvAvgSpeed).text = avgSpeed

        val distanceInKm = "${run.distanceInMeters / 1000f}km"
        this.rootView.findViewById<TextView>(R.id.tvDistance).text = distanceInKm

        this.rootView.findViewById<TextView>(R.id.tvDuration).text =
            TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

        val caloriesBurned = "${run.caloriesBurned} kcal"
        this.findViewById<TextView>(R.id.tvCaloriesBurned).text = caloriesBurned
    }

}