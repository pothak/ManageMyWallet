package com.managemywallet.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.managemywallet.data.entity.Transaction
import com.managemywallet.data.repository.TransactionRepository
import kotlinx.coroutines.launch
import java.util.Calendar

class DashboardViewModel(private val repository: TransactionRepository) : ViewModel() {

    val monthlySpend = MutableLiveData<Double>(0.0)
    val monthlyIncome = MutableLiveData<Double>(0.0)
    val recentTransactions = MutableLiveData<List<Transaction>>(emptyList())
    val categorySpend = MutableLiveData<Map<String, Double>>(emptyMap())

    private var _transactionsLiveData: LiveData<List<Transaction>>? = null

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val startOfMonth = calendar.apply {
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time

            val endOfMonth = calendar.apply {
                set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
            }.time

            monthlySpend.value = repository.getTotalSpent(startOfMonth, endOfMonth)
            monthlyIncome.value = repository.getTotalIncome(startOfMonth, endOfMonth)
        }

        _transactionsLiveData = repository.getRecentTransactions(10)
        _transactionsLiveData?.observeForever { transactions ->
            recentTransactions.value = transactions
        }

        loadCategoryData()
    }

    private fun loadCategoryData() {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val startOfMonth = calendar.apply {
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time

            val endOfMonth = calendar.apply {
                set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
            }.time

            val categories = repository.getAllCategories().value ?: emptyList()
            val categoryMap = mutableMapOf<String, Double>()

            for (category in categories) {
                val total = repository.getTotalByCategory(category, startOfMonth, endOfMonth)
                if (total > 0) {
                    categoryMap[category] = total
                }
            }

            categorySpend.value = categoryMap
        }
    }

    fun refreshData() {
        loadDashboardData()
    }
}
