package com.managemywallet.alert

import android.content.Context
import com.managemywallet.data.dao.AlertRuleDao
import com.managemywallet.data.dao.TransactionDao
import com.managemywallet.data.entity.AlertRule
import com.managemywallet.data.entity.AlertType
import com.managemywallet.data.entity.TimePeriod
import com.managemywallet.data.entity.Transaction
import java.util.Calendar

class SpendingAlertChecker(
    private val context: Context,
    private val transactionDao: TransactionDao,
    private val alertRuleDao: AlertRuleDao
) {

    suspend fun checkAlerts(transaction: Transaction) {
        val enabledRules = alertRuleDao.getEnabledAlertRules().value ?: return

        for (rule in enabledRules) {
            val threshold = rule.thresholdAmount ?: continue
            val exceeded = evaluateRule(rule, transaction, threshold)

            if (exceeded) {
                val message = buildAlertMessage(rule, transaction, threshold)
                NotificationHelper(context).showSpendingAlertNotification(message)
            }
        }
    }

    private suspend fun evaluateRule(rule: AlertRule, transaction: Transaction, threshold: Double): Boolean {
        if (rule.category != null && rule.category != transaction.category) return false

        return when (rule.type) {
            AlertType.SINGLE_TRANSACTION_LIMIT -> {
                transaction.type == com.managemywallet.data.entity.TransactionType.DEBIT &&
                        transaction.amount > threshold
            }
            AlertType.DAILY_SPENDING_LIMIT -> {
                val (start, end) = getDateRange(TimePeriod.DAILY)
                val total = transactionDao.getTotalAmountByTypeAndDateRange(
                    com.managemywallet.data.entity.TransactionType.DEBIT, start, end
                )
                total > threshold
            }
            AlertType.WEEKLY_SPENDING_LIMIT -> {
                val (start, end) = getDateRange(TimePeriod.WEEKLY)
                val total = transactionDao.getTotalAmountByTypeAndDateRange(
                    com.managemywallet.data.entity.TransactionType.DEBIT, start, end
                )
                total > threshold
            }
            AlertType.MONTHLY_SPENDING_LIMIT -> {
                val (start, end) = getDateRange(TimePeriod.MONTHLY)
                val total = transactionDao.getTotalAmountByTypeAndDateRange(
                    com.managemywallet.data.entity.TransactionType.DEBIT, start, end
                )
                total > threshold
            }
            AlertType.CATEGORY_LIMIT -> {
                val (start, end) = getDateRange(rule.timePeriod)
                val total = transactionDao.getTotalByCategoryAndDateRange(
                    transaction.category, start, end
                )
                total > threshold
            }
            AlertType.UNUSUAL_ACTIVITY -> false
        }
    }

    private fun buildAlertMessage(rule: AlertRule, transaction: Transaction, threshold: Double): String {
        return when (rule.type) {
            AlertType.SINGLE_TRANSACTION_LIMIT ->
                "Transaction of ₹%.2f at %s exceeds your single transaction limit of ₹%.2f".format(
                    transaction.amount, transaction.merchant, threshold
                )
            AlertType.DAILY_SPENDING_LIMIT ->
                "Daily spending has exceeded your limit of ₹%.2f".format(threshold)
            AlertType.WEEKLY_SPENDING_LIMIT ->
                "Weekly spending has exceeded your limit of ₹%.2f".format(threshold)
            AlertType.MONTHLY_SPENDING_LIMIT ->
                "Monthly spending has exceeded your limit of ₹%.2f".format(threshold)
            AlertType.CATEGORY_LIMIT ->
                "${transaction.category} spending has exceeded your limit of ₹%.2f".format(threshold)
            AlertType.UNUSUAL_ACTIVITY ->
                "Unusual spending activity detected"
        }
    }

    private fun getDateRange(period: TimePeriod): Pair<java.util.Date, java.util.Date> {
        val calendar = Calendar.getInstance()
        val end = calendar.time

        when (period) {
            TimePeriod.DAILY -> {
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
            TimePeriod.WEEKLY -> {
                calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
            TimePeriod.MONTHLY -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
        }

        val start = calendar.time
        return Pair(start, end)
    }
}
