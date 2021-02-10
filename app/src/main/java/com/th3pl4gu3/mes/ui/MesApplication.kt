package com.th3pl4gu3.mes.ui

import android.app.Application
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

        /* Assigns this to the singleton object */
        INSTANCE = this

        /* Updates the app theme*/
        updateAppTheme()
    }

}