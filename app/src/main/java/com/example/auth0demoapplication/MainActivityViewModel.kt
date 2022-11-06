package com.example.auth0demoapplication

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.result.UserProfile
import com.example.auth0demoapplication.common.Auth0Application
import com.example.auth0demoapplication.common.callback.ViewModelCallback
import com.example.auth0demoapplication.database.Auth0Data
import com.example.auth0demoapplication.database.Auth0Database
import com.example.auth0demoapplication.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The main screen view model
 */
class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    val loggedIn by lazy { MutableLiveData<Boolean>() }
    val errorMessage by lazy { MutableLiveData<AuthenticationException>() }
    val profileData by lazy { MutableLiveData<Auth0Data>() }

    /**
     * To fetch and show data from saved db
     */
    fun fetchDataFromDb() {
        val auth0Dao = Auth0Database.getInstance().auth0Dao()
        CoroutineScope(Dispatchers.IO).launch {
            // Values from room db
            profileData.postValue(auth0Dao.getData())
        }
    }

    /**
     * To initiate the login to authenticate process
     */
    fun login(context: Context) {
        Repository.login(context, object : ViewModelCallback {
            override fun <T> onSuccess(data: T) {
                Auth0Application.isLoggedIn = true
                getUserProfile()
                loggedIn.value = true
            }

            override fun onFailure(error: AuthenticationException) {
                errorMessage.value = error
            }
        })
    }

    /**
     * To get the user profile information
     */
    private fun getUserProfile() {
        Repository.getUserProfile(object : ViewModelCallback {
            override fun <T> onSuccess(data: T) {
                val userProfile = data as UserProfile
                val dataToSave = Auth0Data(userName = userProfile.name, emailId = userProfile.email)
                Thread {
                    val auth0Dao = Auth0Database.getInstance().auth0Dao()
                    auth0Dao.saveData(dataToSave)
                }.start()
                profileData.value = dataToSave
            }

            override fun onFailure(error: AuthenticationException) {
                errorMessage.value = error
            }
        })
    }

    /**
     * To logout the app
     */
    fun logout(context: Context) {
        Repository.logout(context, object : ViewModelCallback {
            override fun <T> onSuccess(data: T) {
                Thread {
                    Auth0Database.getInstance().auth0Dao().deleteAllData()
                }.start()
                Auth0Application.isLoggedIn = false
                loggedIn.value = false
            }

            override fun onFailure(error: AuthenticationException) {
                errorMessage.value = error
            }
        })
    }
}