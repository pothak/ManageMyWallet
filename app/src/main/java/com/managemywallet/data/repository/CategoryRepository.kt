package com.managemywallet.data.repository

import com.managemywallet.data.dao.MerchantCategoryMappingDao
import com.managemywallet.data.entity.MerchantCategoryMapping

class CategoryRepository(private val mappingDao: MerchantCategoryMappingDao) {

    fun getAllMappings() = mappingDao.getAllMappings()

    fun getUserDefinedMappings() = mappingDao.getUserDefinedMappings()

    fun getMappingsForCategory(category: String) = mappingDao.getMappingsForCategory(category)

    suspend fun getMappingForMerchant(merchantPattern: String): MerchantCategoryMapping? {
        return mappingDao.getMappingForMerchant(merchantPattern)
    }

    suspend fun findMatchingMapping(merchantName: String): MerchantCategoryMapping? {
        return mappingDao.findMatchingMapping(merchantName)
    }

    suspend fun addMapping(merchantPattern: String, category: String): Long {
        val existing = mappingDao.findMatchingMapping(merchantPattern)
        return if (existing != null) {
            mappingDao.update(existing.copy(category = category, updatedAt = java.util.Date()))
            existing.id
        } else {
            mappingDao.insert(
                MerchantCategoryMapping(
                    merchantPattern = merchantPattern.lowercase().trim(),
                    category = category,
                    isUserDefined = true
                )
            )
        }
    }

    suspend fun removeMapping(id: Long) {
        val mapping = mappingDao.getUserDefinedMappings().value?.find { it.id == id }
        mapping?.let { mappingDao.delete(it) }
    }

    suspend fun incrementMatchCount(id: Long) {
        mappingDao.incrementMatchCount(id)
    }
}
