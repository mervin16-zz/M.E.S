package com.th3pl4gu3.mes.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.th3pl4gu3.mes.R

/**
 * Get the NavController in disregards
 * of the current user's activity
 **/
inline val AppCompatActivity.navController get() = findNavController(R.id.Navigation_Host)

/*
* MES Toolbar configuration
*/
fun AppCompatActivity.mesToolBarConfiguration(toolbar: MaterialToolbar) {
    /* Set the default action bar to our custom material toolbar */
    setSupportActionBar(toolbar)

    /*
    * Remove the default left title on the toolbar
    * We will provide our own title centered in the middle
    */
    supportActionBar?.setDisplayShowTitleEnabled(false)
}