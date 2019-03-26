package com.anitrack.ruby.anitrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController


/**
 * Temporary solution to dynamically change title of actionbar controlled by Navigation component
 * Should be removed as soon as the bug on Navigation will be fixed: (https://issuetracker.google.com/issues/80267266)
 *
 * Source: https://stackoverflow.com/questions/50599238/dynamic-actionbar-title-from-a-fragment-using-androidx-navigation
 */
interface TempToolbarTitleListener {
    fun updateTitle(title: String)
}

class MainActivity : AppCompatActivity(), TempToolbarTitleListener {

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

    override fun updateTitle(title: String) {
        supportActionBar?.title = title
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
