package com.managemywallet.security

import android.content.Context
import java.io.File

object DataWiper {

    fun wipeAllData(context: Context) {
        AndroidKeystoreManager.deleteAllKeys()

        context.deleteDatabase("wallet.db")
        context.deleteDatabase("wallet.db-journal")
        context.deleteDatabase("wallet.db-shm")
        context.deleteDatabase("wallet.db-wal")

        context.getSharedPreferences("secure_prefs", Context.MODE_PRIVATE).edit().clear().apply()
        context.getSharedPreferences("security_prefs", Context.MODE_PRIVATE).edit().clear().apply()

        val cacheDir = context.cacheDir
        cacheDir.listFiles()?.forEach { it.delete() }

        val externalCacheDir = context.externalCacheDir
        externalCacheDir?.listFiles()?.forEach { it.delete() }
    }

    fun secureDeleteFile(file: File): Boolean {
        if (file.exists()) {
            val random = java.util.Random()
            val buffer = ByteArray(1024)
            random.nextBytes(buffer)

            file.outputStream().use { output ->
                val passes = 3
                repeat(passes) {
                    output.write(buffer)
                }
            }

            return file.delete()
        }
        return false
    }
}
