package com.th3pl4gu3.mes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.databinding.ActivityMesBinding
import com.th3pl4gu3.mes.ui.utils.extensions.contentView
import com.th3pl4gu3.mes.ui.utils.extensions.mesToolBarConfiguration
import com.th3pl4gu3.mes.ui.utils.extensions.navController

class MesActivity : AppCompatActivity() {

    private val mBinding: ActivityMesBinding by contentView(R.layout.activity_mes)
    private lateinit var mAppBarConfiguration: AppBarConfiguration

    /*
    * Fragments listed here are
    * eligible for opening the navigation drawer
    * All other fragments not listed here will get the
    * back button instead of the hamburger menu icon
    * ONLY list fragments that can open the drawer menu here
    */
    private val mNavigationFragments = setOf(
            R.id.Fragment_Emergencies,
            R.id.Fragment_All_Services
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Custom toolbar configuration
        mesToolBarConfiguration(mBinding.ToolbarMain)

        // Setup the JetPack Navigation UI
        navigationUISetup()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(
                    item
            )

    override fun onSupportNavigateUp() = navController.navigateUp(mAppBarConfiguration)


    /*
    * Private functions that are
    * called within the MES Activity
    * itself.
    */
    private fun navigationUISetup() {
        //Fetch the Nav Controller
        with(navController) {
            //Setup the App Bar Configuration
            mAppBarConfiguration = AppBarConfiguration(mNavigationFragments, mBinding.DrawerMain)

            //Use Navigation UI to setup the app bar config and navigation view
            NavigationUI.setupActionBarWithNavController(
                    this@MesActivity,
                    this,
                    mAppBarConfiguration
            )
            NavigationUI.setupWithNavController(mBinding.NavigationView, this)

            //Add on change destination listener to navigation controller to handle fab visibility
            navigationDestinationChangeListener(this)
        }
    }

    private fun navigationDestinationChangeListener(navController: NavController) {
        navController.addOnDestinationChangedListener { _, nd, _ ->

            // Update the toolbar title
            mBinding.ToolbarMainTitle.text = nd.label

            // Update UI according to navigation destination
            when (nd.id) {
                R.id.Fragment_Emergencies,
                R.id.Fragment_All_Services -> {
                    mBinding.DrawerMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
                else -> {
                    mBinding.DrawerMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
            }
        }
    }
}