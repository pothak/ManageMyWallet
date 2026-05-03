package com.managemywallet.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.managemywallet.data.entity.Transaction
import com.managemywallet.data.entity.TransactionType

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction): Long

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Long): Transaction?

    @Query("SELECT * FROM transactions ORDER BY date DESC LIMIT :limit")
    fun getRecentTransactions(limit: Int = 50): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE category = :category ORDER BY date DESC")
    fun getTransactionsByCategory(category: String): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getTransactionsByDateRange(startDate: java.util.Date, endDate: java.util.Date): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE merchant LIKE '%' || :query || '%' OR sms_content LIKE '%' || :query || '%' ORDER BY date DESC")
    fun searchTransactions(query: String): LiveData<List<Transaction>>

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE transaction_type = :type AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalAmountByTypeAndDateRange(
        type: TransactionType,
        startDate: java.util.Date,
        endDate: java.util.Date
    ): Double

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE category = :category AND transaction_type = 'DEBIT' AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalByCategoryAndDateRange(
        category: String,
        startDate: java.util.Date,
        endDate: java.util.Date
    ): Double

    @Query("SELECT DISTINCT category FROM transactions ORDER BY category")
    fun getAllCategories(): LiveData<List<String>>

    @Query("SELECT * FROM transactions WHERE transaction_type = 'DEBIT'")
    suspend fun getDebitTransactions(): List<Transaction>

    @Query("SELECT COUNT(*) FROM transactions")
    suspend fun getTransactionCount(): Int

    @Query("DELETE FROM transactions")
    suspend fun deleteAll()

    @Query("SELECT * FROM transactions WHERE reference_id = :referenceId LIMIT 1")
    suspend fun getByReferenceId(referenceId: String): Transaction?

    @Query("SELECT * FROM transactions WHERE sms_content = :smsContent LIMIT 1")
    suspend fun getBySmsContent(smsContent: String): Transaction?
}
