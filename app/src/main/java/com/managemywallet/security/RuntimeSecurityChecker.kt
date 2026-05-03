package com.managemywallet.security

import android.app.ActivityManager
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object RuntimeSecurityChecker {

    private val knownRootFiles = listOf(
        "/system/app/Superuser.apk",
        "/system/xbin/daemonsu",
        "/system/etc/init.d/99SuperSUDaemon",
        "/system/bin/.ext/.su",
        "/system/bin/su",
        "/system/xbin/su",
        "/sbin/su",
        "/su/bin/su",
        "/data/local/xbin/su",
        "/data/local/bin/su"
    )

    private val knownRootPackages = listOf(
        "com.noshufou.android.su",
        "com.thirdparty.superuser",
        "eu.chainfire.supersu",
        "com.koushikdutta.superuser",
        "com.zachspong.temprootremovejb"
    )

    fun isDeviceRooted(): Boolean {
        return checkRootFiles() || checkRootPackages() || checkBuildTags()
    }

    private fun checkRootFiles(): Boolean {
        return knownRootFiles.any { File(it).exists() }
    }

    private fun checkRootPackages(): Boolean {
        return knownRootPackages.any { try {
            javaClass.classLoader?.getResource(it) != null
        } catch (e: Exception) { false } }
    }

    private fun checkBuildTags(): Boolean {
        return Build.TAGS?.contains("test-keys") == true
    }

    fun isRunningOnEmulator(context: Context): Boolean {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) ||
                Build.FINGERPRINT.startsWith("generic") ||
                Build.FINGERPRINT.startsWith("unknown") ||
                Build.HARDWARE.contains("goldfish") ||
                Build.HARDWARE.contains("ranchu") ||
                Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK") ||
                Build.MODEL.lowercase().contains("emulator") ||
                (Build.BOOTLOADER ?: "").lowercase().contains("unknown") ||
                isEmulatorBySensors(context)
    }

    private fun isEmulatorBySensors(context: Context): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val pm = context.packageManager
        val hasGps = pm.hasSystemFeature(android.content.pm.PackageManager.FEATURE_LOCATION_GPS)
        val hasBluetooth = pm.hasSystemFeature(android.content.pm.PackageManager.FEATURE_BLUETOOTH)
        return !hasGps && !hasBluetooth
    }

    fun clearClipboard(context: Context) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboard.hasPrimaryClip()) {
            val clip = clipboard.primaryClip
            if (clip != null && clip.itemCount > 0) {
                val text = clip.getItemAt(0).text?.toString() ?: return
                if (text.matches(Regex(".*₹.*|.*Rs\\.?.*|.*INR.*|.*[0-9]{4}.*"))) {
                    clipboard.setPrimaryClip(android.content.ClipData.newPlainText("", ""))
                }
            }
        }
    }
}
