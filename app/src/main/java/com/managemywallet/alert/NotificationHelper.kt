package com.managemywallet.alert

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.managemywallet.R
import com.managemywallet.data.entity.TransactionType
import com.managemywallet.ui.MainActivity

class NotificationHelper(private val context: Context) {

    private val channelId = "spending_alerts"
    private val uncategorizedChannelId = "uncategorized_transactions"
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
        createUncategorizedChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Spending Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alerts when spending thresholds are exceeded"
                enableVibration(true)
                enableLights(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createUncategorizedChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                uncategorizedChannelId,
                "Uncategorized Transactions",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Transactions that need to be categorized"
                enableVibration(true)
                enableLights(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showTransactionNotification(amount: Double, type: TransactionType, merchant: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val actionText = if (type == TransactionType.DEBIT) "Debited" else "Credited"
        val title = "New Transaction"
        val body = "$actionText ₹%.2f at %s".format(amount, merchant)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_wallet_logo)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0, 250, 250, 250))
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    fun showUncategorizedNotification(amount: Double, merchant: String, transactionId: Long) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("categorize_transaction_id", transactionId)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            transactionId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = "Categorize Transaction"
        val body = "₹%.2f at %s — tap to assign category".format(amount, merchant)

        val notification = NotificationCompat.Builder(context, uncategorizedChannelId)
            .setSmallIcon(R.drawable.ic_wallet_logo)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0, 500, 200, 500, 200, 500))
            .build()

        notificationManager.notify(transactionId.toInt() + 10000, notification)
    }

    fun showSpendingAlertNotification(message: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_wallet_logo)
            .setContentTitle("Spending Alert")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0, 500, 200, 500, 200, 500))
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
