package com.managemywallet.data.entity

import org.junit.Assert.*
import org.junit.Test

class AlertRuleTest {

    @Test
    fun `alert rule creates with correct values`() {
        val rule = AlertRule(
            id = 1,
            name = "Large Spend Alert",
            type = AlertType.SINGLE_TRANSACTION_LIMIT,
            thresholdAmount = 1000.0,
            category = "Food Delivery",
            timePeriod = TimePeriod.DAILY,
            isEnabled = true
        )

        assertEquals(1, rule.id)
        assertEquals("Large Spend Alert", rule.name)
        assertEquals(AlertType.SINGLE_TRANSACTION_LIMIT, rule.type)
        assertEquals(1000.0, rule.thresholdAmount ?: 0.0, 0.01)
        assertEquals("Food Delivery", rule.category)
        assertEquals(TimePeriod.DAILY, rule.timePeriod)
        assertTrue(rule.isEnabled)
    }

    @Test
    fun `alert rule without category`() {
        val rule = AlertRule(
            name = "Swiggy Alert",
            type = AlertType.SINGLE_TRANSACTION_LIMIT,
            thresholdAmount = 500.0,
            timePeriod = TimePeriod.DAILY,
            isEnabled = true
        )

        assertEquals("Swiggy Alert", rule.name)
        assertEquals(500.0, rule.thresholdAmount ?: 0.0, 0.01)
        assertNull(rule.category)
    }

    @Test
    fun `disabled alert rule`() {
        val rule = AlertRule(
            name = "Disabled Alert",
            type = AlertType.DAILY_SPENDING_LIMIT,
            thresholdAmount = 2000.0,
            timePeriod = TimePeriod.DAILY,
            isEnabled = false
        )

        assertFalse(rule.isEnabled)
    }

    @Test
    fun `alert rule with weekly period`() {
        val rule = AlertRule(
            name = "Weekly Limit",
            type = AlertType.WEEKLY_SPENDING_LIMIT,
            thresholdAmount = 15000.0,
            timePeriod = TimePeriod.WEEKLY,
            isEnabled = true
        )

        assertEquals(TimePeriod.WEEKLY, rule.timePeriod)
        assertEquals(15000.0, rule.thresholdAmount ?: 0.0, 0.01)
    }

    @Test
    fun `alert rule with monthly period`() {
        val rule = AlertRule(
            name = "Monthly Limit",
            type = AlertType.MONTHLY_SPENDING_LIMIT,
            thresholdAmount = 50000.0,
            timePeriod = TimePeriod.MONTHLY,
            isEnabled = true
        )

        assertEquals(TimePeriod.MONTHLY, rule.timePeriod)
        assertEquals(50000.0, rule.thresholdAmount ?: 0.0, 0.01)
    }

    @Test
    fun `alert rule with category limit`() {
        val rule = AlertRule(
            name = "Food Category Limit",
            type = AlertType.CATEGORY_LIMIT,
            category = "Food Delivery",
            thresholdAmount = 3000.0,
            timePeriod = TimePeriod.DAILY,
            isEnabled = true
        )

        assertEquals(AlertType.CATEGORY_LIMIT, rule.type)
        assertEquals("Food Delivery", rule.category)
    }
}
