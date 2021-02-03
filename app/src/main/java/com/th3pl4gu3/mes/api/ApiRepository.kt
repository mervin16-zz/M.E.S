package com.th3pl4gu3.mes.api

/*
* Repository pattern for API requests
*/
class ApiRepository private constructor() {

    private val apiService =
        RetrofitManager.retrofit

    companion object {
        @Volatile
        private var INSTANCE: ApiRepository? = null

        fun getInstance(): ApiRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: ApiRepository()
                        .also { INSTANCE = it }
            }
    }

    suspend fun getServices() = apiService.getProperties()
}