package com.managemywallet.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "currency")
    val currency: String = "INR",

    @ColumnInfo(name = "merchant")
    val merchant: String,

    @ColumnInfo(name = "category")
    val category: String = "Uncategorized",

    @ColumnInfo(name = "date")
    val date: java.util.Date,

    @ColumnInfo(name = "transaction_type")
    val type: TransactionType = TransactionType.DEBIT,

    @ColumnInfo(name = "sms_content")
    val smsContent: String = "",

    @ColumnInfo(name = "bank_name")
    val bankName: String? = null,

    @ColumnInfo(name = "account_number")
    val accountNumber: String? = null,

    @ColumnInfo(name = "reference_id")
    val referenceId: String? = null,

    @ColumnInfo(name = "is_flagged")
    val isFlagged: Boolean = false,

    @ColumnInfo(name = "notes")
    val notes: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: java.util.Date = java.util.Date()
)

enum class TransactionType {
    DEBIT, CREDIT
}
