package com.managemywallet.data.entity

import org.junit.Assert.*
import org.junit.Test
import java.util.Date

class MerchantCategoryMappingTest {

    @Test
    fun `merchant mapping creates with correct values`() {
        val now = Date()
        val mapping = MerchantCategoryMapping(
            id = 1,
            merchantPattern = "swiggy",
            category = "Food Delivery",
            isUserDefined = true,
            matchCount = 5,
            createdAt = now,
            updatedAt = now
        )

        assertEquals(1, mapping.id)
        assertEquals("swiggy", mapping.merchantPattern)
        assertEquals("Food Delivery", mapping.category)
        assertTrue(mapping.isUserDefined)
        assertEquals(5, mapping.matchCount)
        assertEquals(now, mapping.createdAt)
        assertEquals(now, mapping.updatedAt)
    }

    @Test
    fun `default mapping is not user defined`() {
        val now = Date()
        val mapping = MerchantCategoryMapping(
            merchantPattern = "zomato",
            category = "Food Delivery",
            createdAt = now,
            updatedAt = now
        )

        assertFalse(mapping.isUserDefined)
        assertEquals(1, mapping.matchCount)
        assertEquals(0, mapping.id)
    }

    @Test
    fun `mapping with high match count`() {
        val mapping = MerchantCategoryMapping(
            merchantPattern = "amazon",
            category = "Shopping",
            isUserDefined = false,
            matchCount = 50,
            createdAt = Date(),
            updatedAt = Date()
        )

        assertEquals(50, mapping.matchCount)
    }

    @Test
    fun `mapping equality based on id`() {
        val date1 = Date()
        val date2 = Date()
        val mapping1 = MerchantCategoryMapping(
            id = 1,
            merchantPattern = "test",
            category = "Test",
            createdAt = date1,
            updatedAt = date1
        )
        val mapping2 = MerchantCategoryMapping(
            id = 1,
            merchantPattern = "test",
            category = "Test",
            createdAt = date2,
            updatedAt = date2
        )

        assertEquals(mapping1.id, mapping2.id)
    }
}
