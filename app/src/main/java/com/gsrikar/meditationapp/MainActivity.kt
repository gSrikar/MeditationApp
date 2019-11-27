package com.gsrikar.meditationapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp

class MainActivity : AppCompatActivity() {

    private lateinit var configuration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        configuration = AppBarConfiguration(
            setOf(R.id.splashFragment, R.id.loginFragment, R.id.mainFragment)
        )

        setUpActionBar()
    }

    private fun setUpActionBar() {
        NavigationUI.setupActionBarWithNavController(
            this,
            findNavController(R.id.fragment),
            configuration
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp(configuration) || super.onSupportNavigateUp()
    }

}
