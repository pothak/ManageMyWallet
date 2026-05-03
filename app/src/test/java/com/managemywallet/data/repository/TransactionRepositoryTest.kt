package com.managemywallet.data.repository

import com.managemywallet.data.dao.TransactionDao
import com.managemywallet.data.entity.Transaction
import com.managemywallet.data.entity.TransactionType
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class TransactionRepositoryTest {

    @Mock
    private lateinit var transactionDao: TransactionDao

    @Test
    fun `repository creates successfully`() {
        val repository = TransactionRepository(transactionDao)
        assertNotNull(repository)
    }

    @Test
    fun `repository delegates to dao for all transactions`() {
        val repository = TransactionRepository(transactionDao)
        repository.getAllTransactions()
        verify(transactionDao).getAllTransactions()
    }

    @Test
    fun `repository delegates to dao for recent transactions`() {
        val repository = TransactionRepository(transactionDao)
        repository.getRecentTransactions(10)
        verify(transactionDao).getRecentTransactions(10)
    }

    @Test
    fun `repository delegates to dao for category transactions`() {
        val repository = TransactionRepository(transactionDao)
        repository.getTransactionsByCategory("Food")
        verify(transactionDao).getTransactionsByCategory("Food")
    }

    @Test
    fun `repository delegates to dao for date range transactions`() {
        val start = Date()
        val end = Date()
        val repository = TransactionRepository(transactionDao)
        repository.getTransactionsByDateRange(start, end)
        verify(transactionDao).getTransactionsByDateRange(start, end)
    }

    @Test
    fun `repository inserts transaction via dao`() {
        runBlocking {
            val transaction = Transaction(
                amount = 500.0,
                merchant = "Test",
                date = Date()
            )
            `when`(transactionDao.insert(transaction)).thenReturn(1L)

            val repository = TransactionRepository(transactionDao)
            val id = repository.insertTransaction(transaction)

            assertEquals(1L, id)
            verify(transactionDao).insert(transaction)
        }
    }

    @Test
    fun `repository updates transaction via dao`() {
        runBlocking {
            val transaction = Transaction(
                id = 1,
                amount = 500.0,
                merchant = "Test",
                date = Date()
            )

            val repository = TransactionRepository(transactionDao)
            repository.updateTransaction(transaction)

            verify(transactionDao).update(transaction)
        }
    }

    @Test
    fun `repository deletes transaction via dao`() {
        runBlocking {
            val transaction = Transaction(
                id = 1,
                amount = 500.0,
                merchant = "Test",
                date = Date()
            )

            val repository = TransactionRepository(transactionDao)
            repository.deleteTransaction(transaction)

            verify(transactionDao).delete(transaction)
        }
    }

    @Test
    fun `repository gets transaction by id`() {
        runBlocking {
            val transaction = Transaction(
                id = 1,
                amount = 500.0,
                merchant = "Test",
                date = Date()
            )
            `when`(transactionDao.getTransactionById(1)).thenReturn(transaction)

            val repository = TransactionRepository(transactionDao)
            val result = repository.getTransactionById(1)

            assertEquals(transaction, result)
            verify(transactionDao).getTransactionById(1)
        }
    }

    @Test
    fun `repository calculates total spent`() {
        runBlocking {
            val start = Date()
            val end = Date()
            `when`(transactionDao.getTotalAmountByTypeAndDateRange(TransactionType.DEBIT, start, end)).thenReturn(5000.0)

            val repository = TransactionRepository(transactionDao)
            val total = repository.getTotalSpent(start, end)

            assertEquals(5000.0, total, 0.01)
            verify(transactionDao).getTotalAmountByTypeAndDateRange(TransactionType.DEBIT, start, end)
        }
    }

    @Test
    fun `repository calculates total income`() {
        runBlocking {
            val start = Date()
            val end = Date()
            `when`(transactionDao.getTotalAmountByTypeAndDateRange(TransactionType.CREDIT, start, end)).thenReturn(10000.0)

            val repository = TransactionRepository(transactionDao)
            val total = repository.getTotalIncome(start, end)

            assertEquals(10000.0, total, 0.01)
            verify(transactionDao).getTotalAmountByTypeAndDateRange(TransactionType.CREDIT, start, end)
        }
    }

    @Test
    fun `repository gets all categories`() {
        val repository = TransactionRepository(transactionDao)
        repository.getAllCategories()
        verify(transactionDao).getAllCategories()
    }

    @Test
    fun `repository gets transaction count`() {
        runBlocking {
            `when`(transactionDao.getTransactionCount()).thenReturn(42)

            val repository = TransactionRepository(transactionDao)
            val count = repository.getTransactionCount()

            assertEquals(42, count)
            verify(transactionDao).getTransactionCount()
        }
    }
}
