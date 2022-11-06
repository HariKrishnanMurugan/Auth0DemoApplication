package com.example.auth0demoapplication.repository

import android.content.Context
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.example.auth0demoapplication.common.Auth0Application
import com.example.auth0demoapplication.R
import com.example.auth0demoapplication.common.callback.AuthenticationApiClientResultCallback
import com.example.auth0demoapplication.common.callback.ViewModelCallback
import com.example.auth0demoapplication.common.callback.WebAuthProviderResultCallback

/**
 * Repository class to handle data operations related to login, logout and fetch user's information
 */
object Repository {
    val instance = Auth0Application.getInstance()

    /**
     * To make login hit to authenticate the user
     *
     * @param viewModelCallback The view model callback to observe the results
     */
    fun login(context: Context, viewModelCallback: ViewModelCallback) {
        val loginScope = instance.getString(R.string.login_scope)
        instance.getAuth0Manager().login(context, loginScope, object : WebAuthProviderResultCallback {
            override fun <T> onSuccess(credentials: T) {
                // Saving the result credentials in preference
                instance.getPreferences().credentials = credentials as Credentials
                viewModelCallback.onSuccess(null)
            }

            override fun onFailure(error: AuthenticationException) {
                viewModelCallback.onFailure(error)
            }
        })
    }

    /**
     * To get the user profile
     *
     * @param viewModelCallback The view model callback to observe the results
     */
    fun getUserProfile(viewModelCallback: ViewModelCallback) {
        val token = instance.getPreferences().credentials?.accessToken
        token?.let {
            instance.getAuth0Manager().fetchUserProfile(it, object : AuthenticationApiClientResultCallback {
                override fun onSuccessUserProfile(userProfile: UserProfile) {
                    viewModelCallback.onSuccess(userProfile)
                }

                override fun onFailure(error: AuthenticationException) {
                    viewModelCallback.onFailure(error)
                }
            })
        }
    }

    /**
     * To logout the app
     *
     * @param viewModelCallback The view model callback to observe the results
     */
    fun logout(context: Context, viewModelCallback: ViewModelCallback) {
        instance.getAuth0Manager().logout(context, object : WebAuthProviderResultCallback {
            override fun <T> onSuccess(credentials: T) {
                instance.getPreferences().clearPreferenceData()
                viewModelCallback.onSuccess(null)
            }

            override fun onFailure(error: AuthenticationException) {
                viewModelCallback.onFailure(error)
            }
        })
    }
}