package com.managemywallet.security

import android.os.Handler
import android.os.Looper

object InactivityTimer {

    private const val DEFAULT_TIMEOUT_MS = 2 * 60 * 1000L

    private val handler = Handler(Looper.getMainLooper())
    private var timeoutMs = DEFAULT_TIMEOUT_MS
    private var onLockCallback: (() -> Unit)? = null
    private var isLocked = false

    private val timeoutRunnable = Runnable {
        triggerLock()
    }

    fun initialize(onLock: () -> Unit) {
        onLockCallback = onLock
        resetTimer()
    }

    fun resetTimer() {
        handler.removeCallbacks(timeoutRunnable)
        handler.postDelayed(timeoutRunnable, timeoutMs)
    }

    fun stopTimer() {
        handler.removeCallbacks(timeoutRunnable)
    }

    fun setTimeout(seconds: Int) {
        timeoutMs = seconds * 1000L
        resetTimer()
    }

    private fun triggerLock() {
        if (!isLocked) {
            isLocked = true
            onLockCallback?.invoke()
        }
    }

    fun unlock() {
        isLocked = false
        resetTimer()
    }

    fun isLocked(): Boolean = isLocked
}
