package com.managemywallet.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "merchant_category_mappings")
data class MerchantCategoryMapping(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "merchant_pattern")
    val merchantPattern: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "is_user_defined")
    val isUserDefined: Boolean = false,

    @ColumnInfo(name = "match_count")
    val matchCount: Int = 1,

    @ColumnInfo(name = "created_at")
    val createdAt: java.util.Date = java.util.Date(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: java.util.Date = java.util.Date()
)
