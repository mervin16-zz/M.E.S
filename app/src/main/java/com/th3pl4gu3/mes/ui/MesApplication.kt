package com.th3pl4gu3.mes.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.th3pl4gu3.mes.ui.utils.Global
import com.th3pl4gu3.mes.ui.utils.extensions.updateAppTheme

/*
* This is the Application class for Mes
* The class is a singleton so that we can access it anywhere
* E.g access it from Binding Adapters
*/
class MesApplication : Application() {

    companion object {
        @Volatile
        private var INSTANCE: MesApplication? = null

        fun getInstance() =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: MesApplication().also { INSTANCE = it }
            }
    }

    override fun onCreate() {
        super.onCreate()

        // Assigns 'this' to the singleton object
        INSTANCE = this

        // Updates the application's theme
        updateAppTheme()

        // Start a network callback to monitor internet connection
        startNetworkCallback()
    }

    private fun startNetworkCallback() {
        try {
            val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val builder = NetworkRequest.Builder()

            cm.registerNetworkCallback(
                builder.build(),
                object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)

                        // Sets a global variable to true
                        Global.isNetworkConnected = true
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)

                        // Sets a global variable to false
                        Global.isNetworkConnected = false
                    }
                })

            // By default set network to false
            // If network is available, it will be automatically set
            // in the callback
            Global.isNetworkConnected = false
        } catch (e: Exception) {
            Global.isNetworkConnected = false
        }
    }

}