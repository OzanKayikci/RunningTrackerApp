package com.example.runningtrackerapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.runningtrackerapp.R
import com.example.runningtrackerapp.databinding.FragmentRunBinding
import com.example.runningtrackerapp.databinding.FragmentStatisticsBinding
import com.example.runningtrackerapp.utilities.CustomMarkerView
import com.example.runningtrackerapp.utilities.TrackingUtility
import com.example.runningtrackerapp.viewmodels.MainViewModel
import com.example.runningtrackerapp.viewmodels.StatisticsViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class StatisticsFragment : Fragment() {
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticsBinding.inflate(layoutInflater)
        val view = binding.root

        subscribeToObservers()
        setupBarChart()
        return view
    }

    private fun setupBarChart() {
        binding.barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            axisLineColor = R.color.lightPrimary
            textColor = R.color.lightPrimary
            setDrawGridLines(false)

        }

        binding.barChart.axisLeft.apply {
            axisLineColor = R.color.lightPrimary
            textColor = R.color.lightPrimary
            setDrawGridLines(false)
        }

        binding.barChart.axisRight.apply {
            axisLineColor = R.color.lightPrimary
            textColor = R.color.lightPrimary
            setDrawGridLines(false)
        }

        binding.barChart.apply {
            description.text = "Avg Speed Over Time "
            legend.isEnabled = false
        }
    }

    private fun subscribeToObservers() {
        viewModel.totalTimeRun.observe(viewLifecycleOwner) {
            it?.let {
                val totalTimeRun = TrackingUtility.getFormattedStopWatchTime(it)
                binding.tvTotalTime.text = totalTimeRun
            }
        }

        viewModel.totalDistance.observe(viewLifecycleOwner) {
            it?.let {
                val km = it / 1000f

                val totalDistance = round(km * 10f) / 10f
                val totalDistanceString = "$totalDistance km"
                binding.tvTotalDistance.text = totalDistanceString
            }
        }

        viewModel.getTotalAvgSpeed.observe(viewLifecycleOwner) {
            it?.let {
                val avgSpeed = round(it * 10f) / 10f
                val avgSpeedString = "$avgSpeed km/h"

                binding.tvAverageSpeed.text = avgSpeedString

            }
        }

        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner) {
            it?.let {
                val totalCalories = "$it kcal"
                binding.tvTotalCalories.text = totalCalories
            }
        }

        viewModel.runsSortedByDate.observe(viewLifecycleOwner) {
            it?.let {
                val allAvgSpeed = it.indices.map { i ->
                    BarEntry(i.toFloat(), it[i].avgSpeedInKMH)
                }
                val barDataSet = BarDataSet(allAvgSpeed, "Avg Speed Over Time").apply {
                    valueTextColor = R.color.lightPrimary
                    color = ContextCompat.getColor(requireContext(), R.color.md_blue_200)
                    valueTextSize = 20f
                }

                binding.barChart.data = BarData(barDataSet)
                binding.barChart.marker =
                    CustomMarkerView(it.reversed(), requireContext(), R.layout.marker_view)
                binding.barChart.invalidate()

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}