package com.example.auth0demoapplication.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.auth0demoapplication.common.Auth0Application

/**
 * Room DB for this app
 */
@Database(entities = [Auth0Data::class], version = 1, exportSchema = true)
abstract class Auth0Database : RoomDatabase() {

    abstract fun auth0Dao(): Auth0Dao

    companion object {

        @Volatile
        private var INSTANCE: Auth0Database? = null

        fun getInstance(): Auth0Database {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(Auth0Application.getInstance(), Auth0Database::class.java, "Auth0 Demo").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}