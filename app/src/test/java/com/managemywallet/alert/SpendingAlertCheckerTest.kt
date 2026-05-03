package com.managemywallet.alert

import com.managemywallet.data.entity.*
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class SpendingAlertCheckerTest {

    @Test
    fun `single transaction limit rule structure`() {
        val rule = AlertRule(
            name = "Large Transaction",
            type = AlertType.SINGLE_TRANSACTION_LIMIT,
            thresholdAmount = 5000.0,
            timePeriod = TimePeriod.DAILY,
            isEnabled = true
        )

        assertEquals(AlertType.SINGLE_TRANSACTION_LIMIT, rule.type)
        assertEquals(5000.0, rule.thresholdAmount ?: 0.0, 0.01)
        assertNull(rule.category)
    }

    @Test
    fun `category limit rule structure`() {
        val rule = AlertRule(
            name = "Food Limit",
            type = AlertType.CATEGORY_LIMIT,
            category = "Food Delivery",
            thresholdAmount = 1000.0,
            timePeriod = TimePeriod.DAILY,
            isEnabled = true
        )

        assertEquals(AlertType.CATEGORY_LIMIT, rule.type)
        assertEquals("Food Delivery", rule.category)
        assertEquals(1000.0, rule.thresholdAmount ?: 0.0, 0.01)
    }

    @Test
    fun `daily spending limit rule structure`() {
        val rule = AlertRule(
            name = "Daily Limit",
            type = AlertType.DAILY_SPENDING_LIMIT,
            thresholdAmount = 3000.0,
            timePeriod = TimePeriod.DAILY,
            isEnabled = true
        )

        assertEquals(AlertType.DAILY_SPENDING_LIMIT, rule.type)
        assertEquals(3000.0, rule.thresholdAmount ?: 0.0, 0.01)
    }

    @Test
    fun `weekly spending limit rule structure`() {
        val rule = AlertRule(
            name = "Weekly Limit",
            type = AlertType.WEEKLY_SPENDING_LIMIT,
            thresholdAmount = 20000.0,
            timePeriod = TimePeriod.WEEKLY,
            isEnabled = true
        )

        assertEquals(AlertType.WEEKLY_SPENDING_LIMIT, rule.type)
        assertEquals(20000.0, rule.thresholdAmount ?: 0.0, 0.01)
    }

    @Test
    fun `monthly spending limit rule structure`() {
        val rule = AlertRule(
            name = "Monthly Limit",
            type = AlertType.MONTHLY_SPENDING_LIMIT,
            thresholdAmount = 50000.0,
            timePeriod = TimePeriod.MONTHLY,
            isEnabled = true
        )

        assertEquals(AlertType.MONTHLY_SPENDING_LIMIT, rule.type)
        assertEquals(50000.0, rule.thresholdAmount ?: 0.0, 0.01)
    }

    @Test
    fun `unusual activity rule structure`() {
        val rule = AlertRule(
            name = "Unusual Activity",
            type = AlertType.UNUSUAL_ACTIVITY,
            isEnabled = true
        )

        assertEquals(AlertType.UNUSUAL_ACTIVITY, rule.type)
        assertNull(rule.thresholdAmount)
    }

    @Test
    fun `disabled rule structure`() {
        val rule = AlertRule(
            name = "Disabled Rule",
            type = AlertType.SINGLE_TRANSACTION_LIMIT,
            thresholdAmount = 1000.0,
            timePeriod = TimePeriod.DAILY,
            isEnabled = false
        )

        assertFalse(rule.isEnabled)
    }

    @Test
    fun `alert type enum has all values`() {
        val types = AlertType.values()
        assertEquals(6, types.size)
        assertTrue(types.contains(AlertType.SINGLE_TRANSACTION_LIMIT))
        assertTrue(types.contains(AlertType.DAILY_SPENDING_LIMIT))
        assertTrue(types.contains(AlertType.WEEKLY_SPENDING_LIMIT))
        assertTrue(types.contains(AlertType.MONTHLY_SPENDING_LIMIT))
        assertTrue(types.contains(AlertType.CATEGORY_LIMIT))
        assertTrue(types.contains(AlertType.UNUSUAL_ACTIVITY))
    }

    @Test
    fun `time period enum has all values`() {
        val periods = TimePeriod.values()
        assertEquals(3, periods.size)
        assertTrue(periods.contains(TimePeriod.DAILY))
        assertTrue(periods.contains(TimePeriod.WEEKLY))
        assertTrue(periods.contains(TimePeriod.MONTHLY))
    }
}
