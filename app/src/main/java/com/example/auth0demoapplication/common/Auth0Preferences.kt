package com.example.auth0demoapplication.common

import android.content.Context
import android.content.SharedPreferences
import com.auth0.android.result.Credentials
import com.example.auth0demoapplication.BuildConfig
import com.google.gson.Gson

/**
 * This Class represents to hold the data in shared preference
 */
class Auth0Preferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(BuildConfig.PREFERENCE, Context.MODE_PRIVATE)
    private val edit: SharedPreferences.Editor = preferences.edit()

    companion object {
        const val KEY_CREDENTIALS = "credentials"
    }

    /**
     * Property contains the Auth0 result credentials
     */
    var credentials: Credentials?
        get() {
            val json: String? = preferences.getString(KEY_CREDENTIALS, null)
            return Gson().fromJson(json, Credentials::class.java)
        }
        set(credentials) {
            val json = Gson().toJson(credentials)
            edit.putString(KEY_CREDENTIALS, json).apply()
        }

    /**
     * To clear the preference data
     */
    fun clearPreferenceData() {
        edit.clear().apply()
    }
}