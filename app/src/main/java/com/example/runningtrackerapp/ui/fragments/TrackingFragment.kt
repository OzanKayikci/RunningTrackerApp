package com.example.runningtrackerapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.runningtrackerapp.databinding.FragmentRunBinding
import com.example.runningtrackerapp.databinding.FragmentTrackingBinding
import com.example.runningtrackerapp.viewmodels.MainViewModel
import com.google.android.gms.maps.GoogleMap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment : Fragment() {
    private var _binding: FragmentTrackingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    private var map: GoogleMap? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackingBinding.inflate(layoutInflater)

        val view = binding.root

        binding.mapView?.onCreate(savedInstanceState)
        binding.mapView?.getMapAsync {
            map = it
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        binding.mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView?.onLowMemory()
    }

    //help us to cache that map
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView?.onDestroy()
        _binding = null
    }
}