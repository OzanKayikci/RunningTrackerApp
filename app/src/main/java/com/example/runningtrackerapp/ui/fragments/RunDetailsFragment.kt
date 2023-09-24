package com.example.runningtrackerapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.runningtrackerapp.databinding.FragmentRunDetailsBinding
import com.example.runningtrackerapp.utilities.TrackingUtility
import com.example.runningtrackerapp.viewmodels.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class RunDetailsFragment : Fragment() {

    private var _binding: FragmentRunDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRunDetailsBinding.inflate(inflater)
        val runId = arguments?.getString("runId")!!.toInt()
        viewModel.getRun(runId)
        setItems()
        return binding.root
    }

    private fun setItems() {

        viewModel.runDetail.observe(viewLifecycleOwner) {
            Log.d("run details", it.toString())

            Glide.with(this).load(it.img).into(binding.ivRunImage)


            val calendar = Calendar.getInstance().apply {
                timeInMillis = it.timestamp
            }

            val dateFormat = SimpleDateFormat("dd.MM.yy - EEEE", Locale.getDefault())
            binding.tvDate.text = dateFormat.format(calendar.time)

            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            binding.tvStartTime.text = timeFormat.format(calendar.time)

            val avgSpeed = "${it.avgSpeedInKMH}km/h"
            binding.tvAvgSpeed.text = avgSpeed

            val distanceInKm = "${it.distanceInMeters / 1000f}km"
            binding.tvDistance.text = distanceInKm

            binding.tvTime.text = TrackingUtility.getFormattedStopWatchTime(it.timeInMillis)

            val caloriesBurned = "${it.caloriesBurned} cal"
            binding.tvCalories.text = caloriesBurned

        }

    }
}