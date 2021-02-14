package com.th3pl4gu3.mes.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/*
* Retrofit API service to fetch service details from API
* Current service API configured: [MES by Th3pl4gu3]
*/
private const val BASE_URL = "https://mes.th3pl4gu3.com/"
private const val VERSION = "v0.2"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofitService = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("/${VERSION}/en/services")
    suspend fun getProperties(): Response
}

object RetrofitManager {

    val retrofit: ApiService by lazy {
        retrofitService
            .create(ApiService::class.java)
    }

}


