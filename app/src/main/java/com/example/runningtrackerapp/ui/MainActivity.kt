package com.example.runningtrackerapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.runningtrackerapp.R
import com.example.runningtrackerapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        setSupportActionBar(binding.toolbar)
        binding.bottomNavigationView.setupWithNavController(navHostFragment.findNavController())

        navHostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.settingsFragment, R.id.statisticsFragment, R.id.statisticsFragment,R.id.runFragment ->
                    binding.bottomCardNavigationView.visibility = View.VISIBLE

                else -> binding.bottomCardNavigationView.visibility = View.GONE
            }
        }
    }
}