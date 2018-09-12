package com.anitrack.ruby.anitrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.anitrack.ruby.anitrack.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        /*if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }*/

        setupNavigation()
    }

    //To change titles in actionBar based on the label in each fragment
    private fun setupNavigation() {
        val navController = findNavController(this, R.id.my_nav_host_fragment)

        // Update action bar to reflect navigation
        setupActionBarWithNavController(this, navController)
    }

    //To allow back arrow in actionBar to go back
    override fun onSupportNavigateUp() =
            findNavController(this, R.id.my_nav_host_fragment).navigateUp()

}
