package com.managemywallet.sms

import android.content.Context
import android.provider.Telephony
import android.util.Log
import com.managemywallet.WalletApplication
import com.managemywallet.data.entity.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

object SmsImporter {

    suspend fun importExistingSms(context: Context, repository: com.managemywallet.data.repository.TransactionRepository) {
        withContext(Dispatchers.IO) {
            try {
                val contentResolver = context.contentResolver
                val uri = Telephony.Sms.Inbox.CONTENT_URI
                val cursor = contentResolver.query(
                    uri,
                    arrayOf(Telephony.Sms.BODY, Telephony.Sms.DATE),
                    null,
                    null,
                    "${Telephony.Sms.DATE} DESC"
                )

                var count = 0
                cursor?.use { c ->
                    val bodyIndex = c.getColumnIndex(Telephony.Sms.BODY)
                    val dateIndex = c.getColumnIndex(Telephony.Sms.DATE)

                    while (c.moveToNext()) {
                        val smsBody = c.getString(bodyIndex) ?: continue
                        val timestamp = c.getLong(dateIndex)

                        if (!SmsParser.isTransactionSms(smsBody)) continue

                        val parsedTransaction = SmsParser.parseSms(
                            smsBody,
                            Date(timestamp),
                            emptyMap()
                        ) ?: continue

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

                        repository.insertTransaction(transaction)
                        count++
                    }
                }

                Log.d("SmsImporter", "Imported $count transactions")
                withContext(Dispatchers.Main) {
                    android.widget.Toast.makeText(
                        context,
                        "Imported $count transactions",
                        android.widget.Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("SmsImporter", "Error importing SMS", e)
                withContext(Dispatchers.Main) {
                    android.widget.Toast.makeText(
                        context,
                        "Error importing SMS: ${e.message}",
                        android.widget.Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
