package com.example.auth0demoapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface Auth0Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveData(data: Auth0Data)

    @Query("SELECT * from auth0_data")
    suspend fun getData(): Auth0Data

    @Query("DELETE from auth0_data")
    fun deleteAllData()
}