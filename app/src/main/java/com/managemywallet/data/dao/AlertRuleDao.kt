package com.managemywallet.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.managemywallet.data.entity.AlertRule

@Dao
interface AlertRuleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alertRule: AlertRule): Long

    @Update
    suspend fun update(alertRule: AlertRule)

    @Delete
    suspend fun delete(alertRule: AlertRule)

    @Query("SELECT * FROM alert_rules ORDER BY created_at DESC")
    fun getAllAlertRules(): LiveData<List<AlertRule>>

    @Query("SELECT * FROM alert_rules WHERE is_enabled = 1 ORDER BY created_at DESC")
    fun getEnabledAlertRules(): LiveData<List<AlertRule>>

    @Query("SELECT * FROM alert_rules WHERE id = :id")
    suspend fun getAlertRuleById(id: Long): AlertRule?

    @Query("UPDATE alert_rules SET is_enabled = :isEnabled WHERE id = :id")
    suspend fun toggleAlertRule(id: Long, isEnabled: Boolean)

    @Query("DELETE FROM alert_rules")
    suspend fun deleteAll()
}
