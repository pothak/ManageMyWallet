package com.managemywallet.ui.analytics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.managemywallet.data.entity.Transaction
import com.managemywallet.data.repository.TransactionRepository
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnalyticsViewModel(private val repository: TransactionRepository) : ViewModel() {

    private val _categorySpend = MutableLiveData<List<Transaction>>()
    val categorySpend: LiveData<List<Transaction>> = _categorySpend

    private val _dailySpending = MutableLiveData<Map<String, Double>>()
    val dailySpending: LiveData<Map<String, Double>> = _dailySpending

    init {
        loadCategoryData()
        loadDailyData()
    }

    private fun loadCategoryData() {
        CoroutineScope(Dispatchers.IO).launch {
            val transactions = repository.getDebitTransactions()
            CoroutineScope(Dispatchers.Main).launch {
                _categorySpend.value = transactions
            }
        }
    }

    fun loadDailyData() {
        CoroutineScope(Dispatchers.IO).launch {
            val calendar = Calendar.getInstance()
            val end = calendar.time
            calendar.add(Calendar.DAY_OF_MONTH, -7)
            val start = calendar.time

            val dailyMap = mutableMapOf<String, Double>()
            val dateFormat = SimpleDateFormat("dd", Locale.getDefault())

            var current = start.time
            while (current <= end.time) {
                val dayStart = Date(current)
                val dayEnd = Date(current + TimeUnit.DAYS.toMillis(1) - 1)
                val total = repository.getTotalSpent(dayStart, dayEnd)
                dailyMap[dateFormat.format(dayStart)] = total
                current += TimeUnit.DAYS.toMillis(1)
            }

            CoroutineScope(Dispatchers.Main).launch {
                _dailySpending.value = dailyMap
            }
        }
    }

    fun getCategorySpendingMap(transactions: List<Transaction>): Map<String, Double> {
        return transactions.groupBy { it.category }
            .mapValues { entry -> entry.value.sumOf { it.amount } }
    }
}

class AnalyticsViewModelFactory(private val repository: TransactionRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnalyticsViewModel::class.java)) {
            return AnalyticsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
