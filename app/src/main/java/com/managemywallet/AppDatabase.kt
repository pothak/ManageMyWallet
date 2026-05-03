package com.managemywallet

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.managemywallet.data.dao.TransactionDao
import com.managemywallet.data.dao.AlertRuleDao
import com.managemywallet.data.dao.MerchantCategoryMappingDao
import com.managemywallet.data.entity.Transaction
import com.managemywallet.data.entity.AlertRule
import com.managemywallet.data.entity.MerchantCategoryMapping
import com.managemywallet.security.AndroidKeystoreManager
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [Transaction::class, AlertRule::class, MerchantCategoryMapping::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    abstract fun alertRuleDao(): AlertRuleDao
    abstract fun merchantCategoryMappingDao(): MerchantCategoryMappingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS merchant_category_mappings (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        merchant_pattern TEXT NOT NULL,
                        category TEXT NOT NULL,
                        is_user_defined INTEGER NOT NULL DEFAULT 1,
                        match_count INTEGER NOT NULL DEFAULT 1,
                        created_at INTEGER NOT NULL,
                        updated_at INTEGER NOT NULL
                    )
                """)
                database.execSQL("CREATE INDEX IF NOT EXISTS idx_merchant_mapping_pattern ON merchant_category_mappings(merchant_pattern)")
                database.execSQL("CREATE INDEX IF NOT EXISTS idx_merchant_mapping_category ON merchant_category_mappings(category)")
            }
        }

        private val CALLBACK = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("CREATE INDEX IF NOT EXISTS idx_transaction_date ON transactions(date DESC)")
                db.execSQL("CREATE INDEX IF NOT EXISTS idx_transaction_category ON transactions(category)")
                db.execSQL("CREATE INDEX IF NOT EXISTS idx_merchant_mapping_pattern ON merchant_category_mappings(merchant_pattern)")
                db.execSQL("CREATE INDEX IF NOT EXISTS idx_merchant_mapping_category ON merchant_category_mappings(category)")
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val passphrase = AndroidKeystoreManager.getDatabasePassphrase()
                val passphraseBytes = net.sqlcipher.database.SQLiteDatabase.getBytes(passphrase.toCharArray())
                val factory = SupportFactory(passphraseBytes)

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "wallet.db"
                )
                    .openHelperFactory(factory)
                    .addMigrations(MIGRATION_1_2)
                    .addCallback(CALLBACK)
                    .build()

                INSTANCE = instance
                instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): java.util.Date? {
        return value?.let { java.util.Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: java.util.Date?): Long? {
        return date?.time
    }
}
