package com.example.auth0demoapplication.common.callback

import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.result.Credentials

/**
 * The interface to handle Auth0 web provider results
 */
interface WebAuthProviderResultCallback {
    /**
     * Invoked when Auth0 provider gives success results
     *
     * @param credentials The credentials
     */
    fun <T> onSuccess(credentials: T)

    /**
     * Invoked when Auth0 provider gives failure results
     *
     * @param error The exception
     */
    fun onFailure(error: AuthenticationException)
}