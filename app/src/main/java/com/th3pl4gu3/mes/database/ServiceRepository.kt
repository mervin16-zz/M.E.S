package com.th3pl4gu3.mes.database

import android.app.Application
import com.th3pl4gu3.mes.api.ApiRepository
import com.th3pl4gu3.mes.api.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    private suspend fun insertAll(services: List<Service>) = serviceDao.insertAll(services)

    fun getAll() = serviceDao.getAll()

    fun getEmergencies() = serviceDao.getEmergencies()

    suspend fun hasData(): Boolean = serviceDao.count() > 0

    suspend fun refresh(): String? {
        var message: String? = null

        withContext(Dispatchers.IO) {
            val response = ApiRepository.getInstance().getServices()
            if (response.success) {
                insertAll(response.services)
            } else {
                message = response.message
            }
        }

        return message
    }

}