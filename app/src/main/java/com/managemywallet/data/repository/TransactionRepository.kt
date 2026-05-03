package com.managemywallet.data.repository

import com.managemywallet.data.dao.TransactionDao
import com.managemywallet.data.entity.Transaction
import com.managemywallet.data.entity.TransactionType
import java.util.Date

class TransactionRepository(private val transactionDao: TransactionDao) {

    fun getAllTransactions() = transactionDao.getAllTransactions()

    fun getRecentTransactions(limit: Int = 50) = transactionDao.getRecentTransactions(limit)

    fun getTransactionsByCategory(category: String) = transactionDao.getTransactionsByCategory(category)

    fun getTransactionsByDateRange(startDate: Date, endDate: Date) = transactionDao.getTransactionsByDateRange(startDate, endDate)

    fun searchTransactions(query: String) = transactionDao.searchTransactions(query)

    suspend fun insertTransaction(transaction: Transaction): Long {
        return transactionDao.insert(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.update(transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.delete(transaction)
    }

    suspend fun getTransactionById(id: Long): Transaction? {
        return transactionDao.getTransactionById(id)
    }

    suspend fun getTotalSpent(startDate: Date, endDate: Date): Double {
        return transactionDao.getTotalAmountByTypeAndDateRange(TransactionType.DEBIT, startDate, endDate)
    }

    suspend fun getTotalIncome(startDate: Date, endDate: Date): Double {
        return transactionDao.getTotalAmountByTypeAndDateRange(TransactionType.CREDIT, startDate, endDate)
    }

    suspend fun getTotalByCategory(category: String, startDate: Date, endDate: Date): Double {
        return transactionDao.getTotalByCategoryAndDateRange(category, startDate, endDate)
    }

    fun getAllCategories() = transactionDao.getAllCategories()

    suspend fun getDebitTransactions() = transactionDao.getDebitTransactions()

    suspend fun getTransactionCount(): Int {
        return transactionDao.getTransactionCount()
    }

    suspend fun getTransactionByReferenceId(referenceId: String): Transaction? {
        return transactionDao.getByReferenceId(referenceId)
    }

    suspend fun getTransactionBySmsContent(smsContent: String): Transaction? {
        return transactionDao.getBySmsContent(smsContent)
    }
}
