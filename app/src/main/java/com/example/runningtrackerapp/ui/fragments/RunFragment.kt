package com.example.runningtrackerapp.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.runningtrackerapp.R
import com.example.runningtrackerapp.databinding.FragmentRunBinding
import com.example.runningtrackerapp.utilities.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.example.runningtrackerapp.utilities.Constants.REQUEST_CODE_NOTIFICATION_PERMISSION
import com.example.runningtrackerapp.utilities.Constants.backgroundLocationPermission
import com.example.runningtrackerapp.utilities.Constants.locationPermissions
import com.example.runningtrackerapp.utilities.Constants.postNotificationPermissions
import com.example.runningtrackerapp.utilities.TrackingUtility.hasLocationPermissions
import com.example.runningtrackerapp.utilities.TrackingUtility.hasNotificationPermission
import com.example.runningtrackerapp.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar

import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RunFragment : Fragment() {
    private var _binding: FragmentRunBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRunBinding.inflate(layoutInflater)
        val view = binding.root
        showPermissionDialog()
        buttonHandle()
        Timber.wtf("RunFragment OnCreate running")
        return view
    }

    private fun buttonHandle() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val grantedPermissions = permissions.filter { it.value }
        val deniedPermissions = permissions.filter { !it.value }

        if (grantedPermissions.isNotEmpty()) {
            // Permission granted
        } else if (deniedPermissions.isNotEmpty()) {
            Snackbar.make(
                requireView(),
                "Location and Notification permissions are required for this feature. Please enable it in the app settings.",
                Snackbar.LENGTH_LONG
            ).setAction("Settings") {
                navigateToAppSettings()
            }.show()
        }
    }

    private fun requestPermissions() {

        if (!hasNotificationPermission(requireContext()) || !hasLocationPermissions(requireContext())) {
            val permissionsToRequest = mutableListOf<String>()

            if (!hasNotificationPermission(requireContext())) {
                permissionsToRequest.add(postNotificationPermissions)
            }

            if (!hasLocationPermissions(requireContext())) {
                permissionsToRequest.addAll(locationPermissions)
            }

            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }


    private fun showPermissionDialog() {
        if (hasLocationPermissions(requireContext()) && hasNotificationPermission(requireContext())) {
            return
        }
        AlertDialog.Builder(requireContext())
            .setMessage("Location and Notification permissions are required for this feature. Please provide permission to access your location.")
            .setPositiveButton("OK") { dialogInterface, _ ->
                dialogInterface.dismiss()
                requestPermissions()
            }
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
                navigateToAppSettings()

            }
            .create()
            .show()

    }

    private fun navigateToAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        startActivity(intent)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}