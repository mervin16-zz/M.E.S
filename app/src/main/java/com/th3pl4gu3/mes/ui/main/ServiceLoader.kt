package com.th3pl4gu3.mes.ui.main

import android.app.Application
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.api.ApiRepository
import com.th3pl4gu3.mes.api.Service
import com.th3pl4gu3.mes.database.ServiceRepository
import com.th3pl4gu3.mes.ui.utils.Global

/**
 * The ServiceLoader class makes the liaison with API data and Cache data.
 * It will automatically handle data fetching whether it is needed from cache
 * or if it needs a remote data fetching from API
 **/
class ServiceLoader private constructor(private val application: Application) {

    private var mMessage: String? = null

    val message: String?
        get() = mMessage

    companion object {
        @Volatile
        private var instance: ServiceLoader? = null

        fun getInstance(application: Application) =
            instance ?: synchronized(this) {
                instance ?: ServiceLoader(application).also { instance = it }
            }
    }

    suspend fun fetch(emergencies: Boolean): List<Service>? {
        // We first try to fetch services from cache
        val cachedServices = if (!emergencies) ServiceRepository.getInstance(application)
            .getAll() else ServiceRepository.getInstance(application).getEmergencies()

        // If cache is null or empty
        // We try to load from remote repo (API)
        if (cachedServices.isNullOrEmpty()) {

            // To fetch from remote
            // We check if we are connected to internet or not
            // From the variable passed into the loader
            if (Global.isNetworkConnected) {
                with(ApiRepository.getInstance().getServices()) {
                    return if (success) {
                        // Response was successful
                        // We cache the services
                        ServiceRepository.getInstance(application).insertAll(services)

                        // We serve the services back
                        return if (emergencies) services.filter { it.type == "E" } else services
                    } else {
                        // Response was not successful
                        mMessage = message
                        null
                    }
                }
            } else {
                // Sets the error message
                mMessage = application.getString(R.string.message_info_no_internet)
                return null
            }
        }

        //TODO("Create a worker to occasionally fetch updated data)
        // If the code reached this
        // It means that cache was available
        return cachedServices
    }
}