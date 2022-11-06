package com.example.auth0demoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.auth0demoapplication.common.Auth0Application
import com.example.auth0demoapplication.database.Auth0Data
import com.example.auth0demoapplication.databinding.ActivityMainBinding

/**
 * The main screen to login
 */
class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityBinding: ActivityMainBinding
    private val mainActivityViewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this)[MainActivityViewModel::class.java]
    }
    private val prefs = Auth0Application.getInstance().getPreferences()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }
        setInitialValues()
        setListeners()
        observeViewModels()
    }

    /**
     * To set the initial values
     */
    private fun setInitialValues() {
        if (Auth0Application.isLoggedIn) {
            mainActivityViewModel.fetchDataFromDb()
            updateUI()
        } else {
            mainActivityViewModel.fetchDataFromDb()
            updateUI()
        }
    }

    /**
     * To set the listeners
     */
    private fun setListeners() {
        with(mainActivityBinding) {
            btnLogin.setOnClickListener {
                mainActivityViewModel.login(this@MainActivity)
            }
            btnLogout.setOnClickListener {
                mainActivityViewModel.logout(this@MainActivity)
            }
        }
    }

    /**
     * To observe view model
     */
    private fun observeViewModels() {
        with(mainActivityViewModel) {
            loggedIn.observe(this@MainActivity) { isLoggedIn ->
                if (isLoggedIn) {
                    updateUI()
                    showToast(getString(R.string.successfully_login))
                } else {
                    updateUI()
                    showToast(getString(R.string.successfully_logout))
                }
            }
            profileData.observe(this@MainActivity) {
                updateUI(it)
            }
            errorMessage.observe(this@MainActivity) {
                showToast(it.getCode())
            }
        }
    }

    /**
     * To update the UI
     *
     * @param userProfile The user profile data
     */
    private fun updateUI(userProfile: Auth0Data? = null) {
        with(mainActivityBinding) {
            prefs.credentials?.let {
                groupLogin.gone()
                groupUserProfile.visible()
                if (userProfile != null) {
                    with(userProfile) {
                        etUserName.setText(userName)
                        etEmail.setText(emailId)
                    }
                }
            } ?: run {
                groupLogin.visible()
                groupUserProfile.gone()
            }
        }
    }

    /**
     * To show the toast message
     *
     * @param message Toast message
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    /**
     * To set the view visibility as [View.VISIBLE]
     */
    private fun View.visible() {
        this.visibility = View.VISIBLE
    }

    /**
     * To set the view visibility as [View.GONE]
     */
    private fun View.gone() {
        this.visibility = View.GONE
    }
}