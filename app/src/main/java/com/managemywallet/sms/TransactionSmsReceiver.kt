package com.managemywallet.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.managemywallet.WalletApplication
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
            Log.d("TransactionSmsReceiver", "Ignored SMS [not_transaction]: ${smsBody.take(100)}")
            return
        }

        scope.launch {
            try {
                val application = context.applicationContext as? WalletApplication
                if (application == null) {
                    Log.e("TransactionSmsReceiver", "Failed to get WalletApplication from context")
                    return@launch
                }

                val database = application.database
                val mappingDao = database.merchantCategoryMappingDao()

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

                // Check for duplicate before inserting
                val existing = if (!transaction.referenceId.isNullOrEmpty()) {
                    database.transactionDao().getByReferenceId(transaction.referenceId!!)
                } else {
                    database.transactionDao().getBySmsContent(transaction.smsContent ?: "")
                }

                if (existing != null) {
                    Log.d("TransactionSmsReceiver", "Skipping duplicate transaction: ${transaction.referenceId ?: transaction.smsContent?.take(50)}")
                    return@launch
                }

                val id = database.transactionDao().insert(transaction)
                Log.d("TransactionSmsReceiver", "Transaction saved with id: $id, category: ${transaction.category}")

                if (parsedTransaction.isCategoryLearned && userMappingsList.isNotEmpty()) {
                    val pattern = userMappingsList.find {
                        it.category == parsedTransaction.category &&
                        parsedTransaction.merchant.lowercase().contains(it.merchantPattern.lowercase())
                    }
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
                    database.transactionDao(),
                    database.alertRuleDao()
                )
                alertChecker.checkAlerts(transaction)

            } catch (e: Exception) {
                Log.e("TransactionSmsReceiver", "Error processing SMS", e)
            }
        }
    }
}
