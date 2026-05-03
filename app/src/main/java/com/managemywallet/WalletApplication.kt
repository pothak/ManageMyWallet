package com.managemywallet

import android.app.Application
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.managemywallet.security.AndroidKeystoreManager

class WalletApplication : Application() {

    lateinit var database: AppDatabase
        private set

    lateinit var encryptedPrefs: android.content.SharedPreferences
        private set

    override fun onCreate() {
        super.onCreate()

        AndroidKeystoreManager.getOrCreateDatabaseEncryptionKey()
        AndroidKeystoreManager.getOrCreateSharedSecretKey()

        database = AppDatabase.getInstance(this)

        encryptedPrefs = createEncryptedPrefs()
    }

    private fun createEncryptedPrefs(): android.content.SharedPreferences {
        val masterKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            this,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    companion object {
        fun getInstance(context: Context): WalletApplication {
            return context.applicationContext as WalletApplication
        }
    }
}
