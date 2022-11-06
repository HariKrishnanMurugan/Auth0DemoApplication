package com.example.auth0demoapplication.common

import android.app.Application
import com.auth0.android.Auth0
import com.example.auth0demoapplication.BuildConfig

/**
 * The application class to be used across modules
 */
class Auth0Application : Application() {

    companion object {
        private lateinit var instance: Auth0Application
        private lateinit var auth0AccountInfo: Auth0
        private lateinit var auth0Manager: Auth0Manager
        private lateinit var preferences: Auth0Preferences

        /**
         * To get the application instance
         *
         * @return The auth0 Demo Application instance
         */
        fun getInstance(): Auth0Application = instance

        /**
         * To get the auth0 account information
         *
         * @return The auth0 account info
         */
        fun getAuth0AccountInfo(): Auth0 = auth0AccountInfo

        /**
         *  Whether need to know the web is already logged in or not
         */
        var isLoggedIn: Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        auth0AccountInfo = Auth0(BuildConfig.AUTH0_CLIENT_ID, BuildConfig.AUTH0_DOMAIN)
        auth0Manager = Auth0Manager()
        preferences = Auth0Preferences(this)
    }

    /**
     * To get the auth0 manager
     *
     * @return The Auth0 manager instance
     */
    fun getAuth0Manager(): Auth0Manager = auth0Manager

    /**
     * To get the preference instance
     *
     * @return The preference instance
     */
    fun getPreferences(): Auth0Preferences = preferences
}