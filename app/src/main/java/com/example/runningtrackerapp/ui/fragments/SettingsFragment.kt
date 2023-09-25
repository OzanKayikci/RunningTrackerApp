package com.example.runningtrackerapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.runningtrackerapp.R
import com.example.runningtrackerapp.databinding.FragmentRunBinding
import com.example.runningtrackerapp.databinding.FragmentSettingsBinding
import com.example.runningtrackerapp.utilities.Constants
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(layoutInflater)
        val view = binding.root

        loadFieldsFromSharedPref()
        buttonActions()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun buttonActions() {
        binding.btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPref()

            if (success) {
                Snackbar.make(requireView(), "Saved changes", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(requireView(), "Please fill out all fields", Snackbar.LENGTH_LONG)
                    .show()

            }
        }
    }

    private fun loadFieldsFromSharedPref() {
        val name = sharedPreferences.getString(Constants.KEY_NAME, "")
        val weight = sharedPreferences.getFloat(Constants.KEY_WEIGHT, 80f)
        val age = sharedPreferences.getInt(Constants.KEY_AGE, 24)
        val height = sharedPreferences.getInt(Constants.KEY_HEIGHT, 178)

        binding.etName.setText(name)
        binding.etWeight.setText(weight.toString())
        binding.etAge.setText(age.toString())
        binding.etHeight.setText(height.toString())

    }

    private fun applyChangesToSharedPref(): Boolean {
        val nameText = binding.etName.text.toString()
        val weightText = binding.etWeight.text.toString()
        val ageText = binding.etAge.text.toString()
        val heightText = binding.etHeight.text.toString()

        if (nameText.isEmpty() || weightText.isEmpty() || ageText.isEmpty() || heightText.isEmpty()) {
            return false
        }

        sharedPreferences.edit()
            .putString(Constants.KEY_NAME, nameText)
            .putFloat(Constants.KEY_WEIGHT, weightText.toFloat())
            .putInt(Constants.KEY_AGE, ageText.toInt())
            .putInt(Constants.KEY_HEIGHT, heightText.toInt())
            .apply()

        val toolBarText = "Let's go $nameText"
        requireActivity().findViewById<TextView>(R.id.tvToolbarTitle).text = toolBarText

        return true
    }
}