package com.th3pl4gu3.mes.api

/*
* Repository pattern for API requests
*/
class ApiRepository private constructor() {

    private val apiService =
        RetrofitManager.retrofit

    companion object {
        private const val ORDER_TYPE = "asc"

        @Volatile
        private var INSTANCE: ApiRepository? = null

        fun getInstance(): ApiRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: ApiRepository()
                        .also { INSTANCE = it }
            }
    }

    suspend fun getServices() = apiService.getServices(ORDER_TYPE)

    suspend fun getEmergencies() = apiService.getEmergencies(ORDER_TYPE)
}