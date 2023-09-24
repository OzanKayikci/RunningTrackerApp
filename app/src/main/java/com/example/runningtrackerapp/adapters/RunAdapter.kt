package com.example.runningtrackerapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.runningtrackerapp.R
import com.example.runningtrackerapp.data.local.entities.Run
import com.example.runningtrackerapp.utilities.TrackingUtility
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class RunAdapter @Inject constructor(private val navController: NavController) :
    RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val dateView = itemView.findViewById<TextView>(R.id.tvDate)
        private val speedView = itemView.findViewById<TextView>(R.id.tvAvgSpeed)
        private val distanceView = itemView.findViewById<TextView>(R.id.tvDistance)
        private val timeView = itemView.findViewById<TextView>(R.id.tvTime)
        private val caloriesView = itemView.findViewById<TextView>(R.id.tvCalories)
        private val startingTime = itemView.findViewById<TextView>(R.id.tvStartTime)
        fun bind(run: Run) {

            val calendar = Calendar.getInstance().apply {
                timeInMillis = run.timestamp
            }

            val dateFormat = SimpleDateFormat("dd.MM.yy - EEEE", Locale.getDefault())
            dateView.text = dateFormat.format(calendar.time)

            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            startingTime.text = timeFormat.format(calendar.time)

            val avgSpeed = "${run.avgSpeedInKMH}km/h"
            speedView.text = avgSpeed

            val distanceInKm = "${run.distanceInMeters / 1000f}km"
            distanceView.text = distanceInKm

            timeView.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

            val caloriesBurned = "${run.caloriesBurned} cal"
            caloriesView.text = caloriesBurned
        }
    }

    val diffCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Run>) = differ.submitList(list)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_run,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {

        val run = differ.currentList[position]
        holder.bind(run)

        holder.itemView.setOnClickListener {

            val bundle = bundleOf("runId" to run.id.toString())
            navController.navigate(R.id.action_runFragment_to_runDetailsFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}