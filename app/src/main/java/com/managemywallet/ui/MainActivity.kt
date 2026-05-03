package com.managemywallet.ui

import android.Manifest
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.managemywallet.R
import com.managemywallet.WalletApplication
import com.managemywallet.security.BiometricAuthManager
import com.managemywallet.security.InactivityTimer
import com.managemywallet.security.RuntimeSecurityChecker
import com.managemywallet.security.ScreenSecurityHelper
import com.managemywallet.ui.dashboard.DashboardFragment
import com.managemywallet.ui.analytics.AnalyticsFragment
import com.managemywallet.ui.settings.SettingsFragment
import com.managemywallet.ui.transactions.TransactionListFragment

class MainActivity : AppCompatActivity() {

    private val SMS_PERMISSION_REQUEST_CODE = 100

    private var isUnlocked = false
    private var isAuthenticating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ScreenSecurityHelper.preventScreenshots(this)

        checkRuntimeSecurity()

        setContentView(R.layout.activity_main)

        InactivityTimer.initialize {
            runOnUiThread {
                isUnlocked = false
                showBiometricAuth()
            }
        }

        val prefs = (application as WalletApplication).encryptedPrefs
        val biometricEnabled = prefs.getBoolean("biometric_enabled", true)

        if (biometricEnabled && savedInstanceState == null) {
            isAuthenticating = true
            showBiometricAuth()
        } else {
            isUnlocked = true
            InactivityTimer.unlock()
            setupNavigation()
            requestSmsPermissions()
        }
    }

    private fun requestSmsPermissions() {
        val receiveSms = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
        val readSms = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
        val notifications = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)

        val permissionsToRequest = mutableListOf<String>()
        if (receiveSms != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.RECEIVE_SMS)
        }
        if (readSms != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.READ_SMS)
        }
        if (notifications != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                SMS_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            val allGranted = grantResults.all { it == android.content.pm.PackageManager.PERMISSION_GRANTED }
            if (!allGranted) {
                android.widget.Toast.makeText(this, "SMS permissions are required to read transaction messages", android.widget.Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isUnlocked && !isAuthenticating) {
            isAuthenticating = true
            showBiometricAuth()
        } else if (isUnlocked) {
            InactivityTimer.unlock()
        }
    }

    override fun onPause() {
        super.onPause()
        RuntimeSecurityChecker.clearClipboard(this)
    }

    override fun onStop() {
        super.onStop()
        InactivityTimer.stopTimer()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        InactivityTimer.resetTimer()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            InactivityTimer.resetTimer()
        }
        return super.dispatchTouchEvent(event)
    }

    private fun checkRuntimeSecurity() {
        val prefs = (application as WalletApplication).encryptedPrefs
        val isRooted = RuntimeSecurityChecker.isDeviceRooted()
        val isEmulator = RuntimeSecurityChecker.isRunningOnEmulator(this)

        if (isRooted && !prefs.getBoolean("root_warning_dismissed", false)) {
            prefs.edit().putBoolean("security_warning", true).apply()
        }
    }

    private fun showBiometricAuth() {
        if (BiometricAuthManager.isBiometricAvailable(this)) {
            BiometricAuthManager.showBiometricPrompt(this, object : BiometricAuthManager.AuthCallback {
                override fun onAuthenticationSucceeded() {
                    isUnlocked = true
                    isAuthenticating = false
                    InactivityTimer.unlock()
                    setupNavigation()
                }

                override fun onAuthenticationFailed() {
                    isAuthenticating = false
                }

                override fun onAuthenticationError(errorCode: Int, errString: String) {
                    isUnlocked = true
                    isAuthenticating = false
                    InactivityTimer.unlock()
                    setupNavigation()
                }
            })
        } else {
            isUnlocked = true
            isAuthenticating = false
            InactivityTimer.unlock()
            setupNavigation()
        }
    }

    private fun setupNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_dashboard -> DashboardFragment()
                R.id.nav_transactions -> TransactionListFragment()
                R.id.nav_analytics -> AnalyticsFragment()
                R.id.nav_settings -> SettingsFragment()
                else -> null
            }
            fragment?.let { loadFragment(it) }
            fragment != null
        }

        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            loadFragment(DashboardFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_enter,
                R.anim.fragment_exit,
                R.anim.fragment_pop_enter,
                R.anim.fragment_pop_exit
            )
            .replace(R.id.fragment_container, fragment)
            .commitAllowingStateLoss()
    }
}
