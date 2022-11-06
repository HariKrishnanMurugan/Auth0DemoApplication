package com.example.auth0demoapplication.common.callback

import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.result.UserProfile

/**
 * The interface listen when api client result
 */
interface AuthenticationApiClientResultCallback {
    /**
     * Invoked when get the user profile info
     *
     * @param userProfile The user profile info
     */
    fun onSuccessUserProfile(userProfile: UserProfile)

    /**
     * Invoked when Auth0 provider gives failure results
     *
     * @param error The exception
     */
    fun onFailure(error: AuthenticationException)
}