package com.example.auth0demoapplication.common

import android.content.Context
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.example.auth0demoapplication.BuildConfig
import com.example.auth0demoapplication.common.callback.AuthenticationApiClientResultCallback
import com.example.auth0demoapplication.common.callback.WebAuthProviderResultCallback

/**
 * The Auth0 manager class which we can access the web service
 */
class Auth0Manager {

    /**
     * To login for authenticate the Auth0 web
     *
     * @param scope    The login scope
     * @param callback The web auth0 provider result callback
     */
    fun login(context: Context, scope: String, callback: WebAuthProviderResultCallback) {
        WebAuthProvider.login(Auth0Application.getAuth0AccountInfo()).withScheme(BuildConfig.AUTH0_SCHEME).withScope(scope)
            .withAudience(BuildConfig.LOGIN_URL).start(context, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    callback.onFailure(error)
                }

                override fun onSuccess(result: Credentials) {
                    callback.onSuccess(result)
                }
            })
    }

    /**
     * To fetch the user profile info
     */
    fun fetchUserProfile(accessToken: String, callback: AuthenticationApiClientResultCallback) {
        AuthenticationAPIClient(Auth0Application.getAuth0AccountInfo()).userInfo(accessToken)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    callback.onFailure(error)
                }

                override fun onSuccess(result: UserProfile) {
                    callback.onSuccessUserProfile(result)
                }
            })
    }

    /**
     * To logout the auth0 demo application
     */
    fun logout(context: Context, callback: WebAuthProviderResultCallback) {
        WebAuthProvider.logout(Auth0Application.getAuth0AccountInfo()).withScheme(BuildConfig.AUTH0_SCHEME)
            .start(context, object : Callback<Void?, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    callback.onFailure(error)
                }

                override fun onSuccess(result: Void?) {
                    callback.onSuccess(result)
                }
            })
    }
}