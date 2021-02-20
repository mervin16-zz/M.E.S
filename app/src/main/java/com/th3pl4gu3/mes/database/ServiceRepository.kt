package com.th3pl4gu3.mes.database

import android.app.Application
import com.th3pl4gu3.mes.api.Service

/*
* Repository pattern for Service CRUD
*/
class ServiceRepository private constructor(application: Application) {

    private val mDatabase =
        MesDatabase.getDatabase(
            application
        )
    private val serviceDao = mDatabase.serviceDao()

    companion object {
        @Volatile
        private var instance: ServiceRepository? = null

        fun getInstance(application: Application) =
            instance ?: synchronized(this) {
                instance ?: ServiceRepository(application).also { instance = it }
            }
    }

    suspend fun getAll() = serviceDao.getAll()

    suspend fun getEmergencies() = serviceDao.getEmergencies()

    suspend fun insertAll(services: List<Service>) = serviceDao.insertAll(services)
}