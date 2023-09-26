package com.example.runningtrackerapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import com.example.runningtrackerapp.R
import com.example.runningtrackerapp.databinding.FragmentRunBinding
import com.example.runningtrackerapp.databinding.FragmentSetupBinding
import com.example.runningtrackerapp.utilities.Constants
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment() {
    private var _binding: FragmentSetupBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPref: SharedPreferences

    //we use set for primitive types (EX: Boolean, Float ...)
    @set:Inject
    var isFirstAppOpen = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetupBinding.inflate(layoutInflater)

        if (!isFirstAppOpen) {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.setupFragment, true)
                .build() // we set "inclusive" to true to exclude (pop) the given target from the back stack.

            findNavController().navigate(
                R.id.action_setupFragment_to_runFragment,
                savedInstanceState,
                navOptions
            )
            //The "savedInstanceState" parameter contains data saved from a previous state of the fragment or activity. This data can be restored when the fragment or activity is rebuilt.
        }

        val view = binding.root

        buttonHandle()
        return view
    }

    private fun buttonHandle() {
        binding.tvContinue.setOnClickListener {
            val success = writePersonalDaaToSharedPref()

            if (success) {
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)

            } else {
                Snackbar.make(requireView(), "Please enter all fields", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun writePersonalDaaToSharedPref(): Boolean {
        val name = binding.etName.text.toString()
        val weight = binding.etWeight.text.toString()
        val age = binding.etAge.text.toString()
        val height = binding.etHeight.text.toString()

        if (name.isEmpty() || weight.isEmpty() || age.isEmpty() || height.isEmpty()) {
            return false
        }

        sharedPref.edit()
            .putString(Constants.KEY_NAME, name)
            .putFloat(Constants.KEY_WEIGHT, weight.toFloat())
            .putInt(Constants.KEY_AGE, age.toInt())
            .putInt(Constants.KEY_HEIGHT, height.toInt())
            .putBoolean(Constants.KEY_FIRST_TIME_TOGGLE, false)
            .apply()

        val toolbarText = "Let's go, $name!"
        requireActivity().findViewById<TextView>(R.id.tvToolbarTitle).text = toolbarText

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}