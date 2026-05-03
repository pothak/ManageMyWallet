package com.managemywallet.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object AndroidKeystoreManager {

    private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
    private const val KEY_ALIAS_DB_ENCRYPTION = "db_encryption_key"
    private const val KEY_ALIAS_SHARED_SECRET = "shared_secret_key"

    private const val AES_MODE = "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_GCM}/${KeyProperties.ENCRYPTION_PADDING_NONE}"
    private const val GCM_TAG_LENGTH = 128
    private const val GCM_IV_SIZE = 12

    private val keyStore: KeyStore by lazy {
        KeyStore.getInstance(KEYSTORE_PROVIDER).apply { load(null) }
    }

    fun getOrCreateDatabaseEncryptionKey(): SecretKey {
        var key = keyStore.getKey(KEY_ALIAS_DB_ENCRYPTION, null) as? SecretKey
        if (key == null) {
            key = generateKey(KEY_ALIAS_DB_ENCRYPTION, true)
        }
        return key
    }

    fun getOrCreateSharedSecretKey(): SecretKey {
        var key = keyStore.getKey(KEY_ALIAS_SHARED_SECRET, null) as? SecretKey
        if (key == null) {
            key = generateKey(KEY_ALIAS_SHARED_SECRET, true)
        }
        return key
    }

    fun deleteAllKeys() {
        keyStore.deleteEntry(KEY_ALIAS_DB_ENCRYPTION)
        keyStore.deleteEntry(KEY_ALIAS_SHARED_SECRET)
    }

    fun keyExists(alias: String): Boolean {
        return keyStore.containsAlias(alias)
    }

    fun createCipherForEncryption(keyAlias: String): Pair<Cipher, ByteArray> {
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(keyAlias))
        return Pair(cipher, cipher.iv)
    }

    fun createCipherForDecryption(keyAlias: String, iv: ByteArray): Cipher {
        val cipher = Cipher.getInstance(AES_MODE)
        val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(keyAlias), spec)
        return cipher
    }

    private fun getSecretKey(alias: String): SecretKey {
        return keyStore.getKey(alias, null) as? SecretKey
            ?: throw IllegalStateException("Key not found: $alias. Generate it first.")
    }

    private fun generateKey(alias: String, userAuthenticationRequired: Boolean): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            KEYSTORE_PROVIDER
        )

        val builder = KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .setUserAuthenticationRequired(userAuthenticationRequired)
            .setUserAuthenticationValidityDurationSeconds(-1) // Require auth for each operation

        keyGenerator.init(builder.build())
        return keyGenerator.generateKey()
    }

    fun getDatabasePassphrase(): String {
        val key = getOrCreateDatabaseEncryptionKey()
        return key.encoded.joinToString("") { byte -> "%02x".format(byte) }
    }
}
