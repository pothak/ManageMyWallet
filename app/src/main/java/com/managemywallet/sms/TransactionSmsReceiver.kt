package com.managemywallet.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.managemywallet.WalletApplication
import com.managemywallet.data.entity.MerchantCategoryMapping
import com.managemywallet.data.entity.Transaction
import com.managemywallet.alert.NotificationHelper
import com.managemywallet.alert.SpendingAlertChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionSmsReceiver : BroadcastReceiver() {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return

        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent) ?: return

        val smsBody = messages.joinToString("") { it.messageBody }
        val timestamp = messages.firstOrNull()?.timestampMillis ?: System.currentTimeMillis()

        Log.d("TransactionSmsReceiver", "Received SMS: $smsBody")

        if (!SmsParser.isTransactionSms(smsBody)) {
            Log.d("TransactionSmsReceiver", "Not a transaction SMS, ignoring")
            return
        }

        scope.launch {
            try {
                val application = context.applicationContext as WalletApplication
                val mappingDao = application.database.merchantCategoryMappingDao()

                val userMappingsList = mappingDao.getAllMappings().value ?: emptyList()
                val userMappings = userMappingsList.associate { it.merchantPattern to it.category }

                val parsedTransaction = SmsParser.parseSms(
                    smsBody,
                    java.util.Date(timestamp),
                    userMappings
                ) ?: return@launch

                val transaction = Transaction(
                    amount = parsedTransaction.amount,
                    currency = "INR",
                    merchant = parsedTransaction.merchant,
                    category = parsedTransaction.category,
                    date = parsedTransaction.date,
                    type = parsedTransaction.type,
                    smsContent = parsedTransaction.rawSms,
                    bankName = parsedTransaction.bankName,
                    accountNumber = parsedTransaction.accountNumber,
                    referenceId = parsedTransaction.referenceId
                )

                val id = application.database.transactionDao().insert(transaction)
                Log.d("TransactionSmsReceiver", "Transaction saved with id: $id, category: ${transaction.category}")

                if (parsedTransaction.isCategoryLearned && userMappingsList.isNotEmpty()) {
                    val pattern = userMappingsList.find { it.category == parsedTransaction.category && parsedTransaction.merchant.lowercase().contains(it.merchantPattern.lowercase()) }
                    pattern?.let {
                        mappingDao.incrementMatchCount(it.id)
                    }
                }

                val notificationHelper = NotificationHelper(context)

                if (parsedTransaction.isUncategorized) {
                    notificationHelper.showUncategorizedNotification(
                        parsedTransaction.amount,
                        parsedTransaction.merchant,
                        id
                    )
                } else {
                    notificationHelper.showTransactionNotification(
                        parsedTransaction.amount,
                        parsedTransaction.type,
                        parsedTransaction.merchant
                    )
                }

                val alertChecker = SpendingAlertChecker(
                    context,
                    application.database.transactionDao(),
                    application.database.alertRuleDao()
                )
                alertChecker.checkAlerts(transaction)

            } catch (e: Exception) {
                Log.e("TransactionSmsReceiver", "Error processing SMS", e)
            }
        }
    }
}
