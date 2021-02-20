package com.th3pl4gu3.mes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.th3pl4gu3.mes.api.Service

/*
 * CRUD for the Service Object
*/
@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(services: List<Service>)

    @Query("SELECT * FROM service_table")
    suspend fun getAll(): List<Service>

    @Query("SELECT * FROM service_table WHERE type = 'E'")
    suspend fun getEmergencies(): List<Service>
}