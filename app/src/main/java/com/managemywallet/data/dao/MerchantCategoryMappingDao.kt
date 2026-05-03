package com.managemywallet.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.managemywallet.data.entity.MerchantCategoryMapping

@Dao
interface MerchantCategoryMappingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mapping: MerchantCategoryMapping): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mappings: List<MerchantCategoryMapping>)

    @Update
    suspend fun update(mapping: MerchantCategoryMapping)

    @Delete
    suspend fun delete(mapping: MerchantCategoryMapping)

    @Query("SELECT * FROM merchant_category_mappings WHERE merchant_pattern = :merchantPattern")
    suspend fun getMappingForMerchant(merchantPattern: String): MerchantCategoryMapping?

    @Query("SELECT * FROM merchant_category_mappings WHERE LOWER(:merchantName) LIKE '%' || LOWER(merchant_pattern) || '%' ORDER BY LENGTH(merchant_pattern) DESC LIMIT 1")
    suspend fun findMatchingMapping(merchantName: String): MerchantCategoryMapping?

    @Query("SELECT * FROM merchant_category_mappings ORDER BY match_count DESC")
    fun getAllMappings(): LiveData<List<MerchantCategoryMapping>>

    @Query("SELECT * FROM merchant_category_mappings WHERE is_user_defined = 1 ORDER BY match_count DESC")
    fun getUserDefinedMappings(): LiveData<List<MerchantCategoryMapping>>

    @Query("SELECT * FROM merchant_category_mappings WHERE category = :category ORDER BY match_count DESC")
    fun getMappingsForCategory(category: String): LiveData<List<MerchantCategoryMapping>>

    @Query("UPDATE merchant_category_mappings SET match_count = match_count + 1, updated_at = :now WHERE id = :id")
    suspend fun incrementMatchCount(id: Long, now: java.util.Date = java.util.Date())

    @Query("DELETE FROM merchant_category_mappings")
    suspend fun deleteAll()
}
