package com.example.auth0demoapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * The class representing the data to store in room db
 */
@Entity(tableName = "auth0_data")
data class Auth0Data(
    @field:ColumnInfo(name = "username") var userName: String?,
    @field:ColumnInfo(name = "emailId") var emailId: String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
)