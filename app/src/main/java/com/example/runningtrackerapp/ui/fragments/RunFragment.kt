package com.example.runningtrackerapp.ui.fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runningtrackerapp.R
import com.example.runningtrackerapp.adapters.RunAdapter
import com.example.runningtrackerapp.databinding.FragmentRunBinding
import com.example.runningtrackerapp.utilities.Constants.locationPermissions
import com.example.runningtrackerapp.utilities.Constants.postNotificationPermissions
import com.example.runningtrackerapp.utilities.SortType
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

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[MainViewModel::class.java]
    }

    private lateinit var runAdapter: RunAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRunBinding.inflate(layoutInflater)
        val view = binding.root

        setFilters()
        setupRecycleView()
        observerFunctions()
        showPermissionDialog()
        buttonHandle()
        return view
    }

    private fun setFilters() {
        when (viewModel.sortType) {
            SortType.DATE -> binding.spFilter.setSelection(0)
            SortType.RUNNING_TIME -> binding.spFilter.setSelection(1)
            SortType.DISTANCE -> binding.spFilter.setSelection(2)
            SortType.AVG_SPEED -> binding.spFilter.setSelection(3)
            SortType.CALORIES_BURNED -> binding.spFilter.setSelection(4)
        }
    }

    private fun observerFunctions() {


        viewModel.runs.observe(viewLifecycleOwner) {
            if (it != null) {
                Timber.d("values", it)
                runAdapter.submitList(it)

            }
        }
    }

    private fun buttonHandle() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
        binding.spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> viewModel.sortRuns(SortType.DATE)
                    1 -> viewModel.sortRuns(SortType.RUNNING_TIME)
                    2 -> viewModel.sortRuns(SortType.DISTANCE)
                    3 -> viewModel.sortRuns(SortType.AVG_SPEED)
                    4 -> viewModel.sortRuns(SortType.CALORIES_BURNED)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun setupRecycleView() = binding.rvRuns.apply {
        runAdapter = RunAdapter(findNavController())
        adapter = runAdapter

        layoutManager = LinearLayoutManager(requireContext())
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