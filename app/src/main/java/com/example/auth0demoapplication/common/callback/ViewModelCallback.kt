package com.example.auth0demoapplication.common.callback

import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.result.Credentials

/**
 * The interface listen to result
 */
interface ViewModelCallback {
    /**
     * Invoked when Auth0 provider gives success results
     *
     * @param data The data retrieved from the service
     * @param <T>  The type of data
     */
    fun <T> onSuccess(data: T)

    /**
     * Invoked when Auth0 provider gives failure results
     *
     * @param error The exception
     */
    fun onFailure(error: AuthenticationException)
}