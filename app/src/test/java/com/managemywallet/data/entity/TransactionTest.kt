package com.managemywallet.data.entity

import org.junit.Assert.*
import org.junit.Test
import java.util.Date

class TransactionTest {

    @Test
    fun `transaction creates with correct values`() {
        val date = Date()
        val transaction = Transaction(
            amount = 500.0,
            merchant = "Zomato",
            category = "Food Delivery",
            date = date,
            type = TransactionType.DEBIT,
            smsContent = "INR 500 debited",
            bankName = "HDFC Bank",
            accountNumber = "XX1234",
            referenceId = "REF123",
            notes = "Dinner"
        )

        assertEquals(500.0, transaction.amount, 0.01)
        assertEquals("Zomato", transaction.merchant)
        assertEquals("Food Delivery", transaction.category)
        assertEquals(date, transaction.date)
        assertEquals(TransactionType.DEBIT, transaction.type)
        assertEquals("INR 500 debited", transaction.smsContent)
        assertEquals("HDFC Bank", transaction.bankName)
        assertEquals("XX1234", transaction.accountNumber)
        assertEquals("REF123", transaction.referenceId)
        assertEquals("Dinner", transaction.notes)
        assertEquals("INR", transaction.currency)
        assertFalse(transaction.isFlagged)
    }

    @Test
    fun `transaction type enum values`() {
        assertEquals(TransactionType.DEBIT.ordinal, 0)
        assertEquals(TransactionType.CREDIT.ordinal, 1)
        assertEquals(2, TransactionType.values().size)
    }

    @Test
    fun `credit transaction has correct type`() {
        val transaction = Transaction(
            amount = 1000.0,
            merchant = "Salary",
            category = "Income",
            date = Date(),
            type = TransactionType.CREDIT,
            smsContent = "INR 1000 credited"
        )
        assertEquals(TransactionType.CREDIT, transaction.type)
    }

    @Test
    fun `transaction with default values`() {
        val transaction = Transaction(
            amount = 250.0,
            merchant = "Unknown",
            date = Date()
        )
        assertEquals("Uncategorized", transaction.category)
        assertEquals(TransactionType.DEBIT, transaction.type)
        assertEquals("INR", transaction.currency)
        assertFalse(transaction.isFlagged)
        assertNull(transaction.bankName)
        assertNull(transaction.accountNumber)
        assertNull(transaction.referenceId)
        assertNull(transaction.notes)
    }

    @Test
    fun `flagged transaction`() {
        val transaction = Transaction(
            amount = 5000.0,
            merchant = "Suspicious Shop",
            date = Date(),
            isFlagged = true
        )
        assertTrue(transaction.isFlagged)
    }
}
