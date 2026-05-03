package com.managemywallet.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert_rules")
data class AlertRule(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "rule_type")
    val type: AlertType,

    @ColumnInfo(name = "threshold_amount")
    val thresholdAmount: Double? = null,

    @ColumnInfo(name = "category")
    val category: String? = null,

    @ColumnInfo(name = "time_period")
    val timePeriod: TimePeriod = TimePeriod.DAILY,

    @ColumnInfo(name = "is_enabled")
    val isEnabled: Boolean = true,

    @ColumnInfo(name = "created_at")
    val createdAt: java.util.Date = java.util.Date()
)

enum class AlertType {
    SINGLE_TRANSACTION_LIMIT,
    DAILY_SPENDING_LIMIT,
    WEEKLY_SPENDING_LIMIT,
    MONTHLY_SPENDING_LIMIT,
    CATEGORY_LIMIT,
    UNUSUAL_ACTIVITY
}

enum class TimePeriod {
    DAILY,
    WEEKLY,
    MONTHLY
}
