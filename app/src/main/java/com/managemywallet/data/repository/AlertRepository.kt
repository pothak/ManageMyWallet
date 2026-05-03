package com.managemywallet.data.repository

import com.managemywallet.data.dao.AlertRuleDao
import com.managemywallet.data.entity.AlertRule

class AlertRepository(private val alertRuleDao: AlertRuleDao) {

    fun getAllAlertRules() = alertRuleDao.getAllAlertRules()

    fun getEnabledAlertRules() = alertRuleDao.getEnabledAlertRules()

    suspend fun insertAlertRule(alertRule: AlertRule): Long {
        return alertRuleDao.insert(alertRule)
    }

    suspend fun updateAlertRule(alertRule: AlertRule) {
        alertRuleDao.update(alertRule)
    }

    suspend fun deleteAlertRule(alertRule: AlertRule) {
        alertRuleDao.delete(alertRule)
    }

    suspend fun getAlertRuleById(id: Long): AlertRule? {
        return alertRuleDao.getAlertRuleById(id)
    }

    suspend fun toggleAlertRule(id: Long, isEnabled: Boolean) {
        alertRuleDao.toggleAlertRule(id, isEnabled)
    }
}
