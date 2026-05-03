package com.managemywallet.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.managemywallet.data.entity.Transaction
import com.managemywallet.data.entity.TransactionType
import com.managemywallet.data.repository.TransactionRepository
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(private val repository: TransactionRepository) : ViewModel() {

    private val _monthlySpending = MutableLiveData<Double>()
    val monthlySpending: LiveData<Double> = _monthlySpending

    private val _yesterdaySpending = MutableLiveData<Double>()
    val yesterdaySpending: LiveData<Double> = _yesterdaySpending

    private val _dailyAverage = MutableLiveData<Double>()
    val dailyAverage: LiveData<Double> = _dailyAverage

    private val _trendWeek = MutableLiveData<Double>()
    val trendWeek: LiveData<Double> = _trendWeek

    private val _trendMonth = MutableLiveData<Double>()
    val trendMonth: LiveData<Double> = _trendMonth

    private val _topCategories = MutableLiveData<List<Pair<String, Double>>>()
    val topCategories: LiveData<List<Pair<String, Double>>> = _topCategories

    private val _recentTransactions = MutableLiveData<List<Transaction>>()
    val recentTransactions: LiveData<List<Transaction>> = _recentTransactions

    private val monthlyBudget = 18000.0

    init {
        loadData()
    }

    fun refreshData() {
        loadData()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val calendar = Calendar.getInstance()
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentYear = calendar.get(Calendar.YEAR)

            // Monthly spending (debit only)
            calendar.set(currentYear, currentMonth, 1, 0, 0, 0)
            val monthStart = calendar.time
            calendar.add(Calendar.MONTH, 1)
            calendar.add(Calendar.SECOND, -1)
            val monthEnd = calendar.time

            val monthlySpending = repository.getTotalSpent(monthStart, monthEnd)

            // Yesterday spending
            calendar.time = Date()
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            val yesterdayStart = calendar.time
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            val yesterdayEnd = calendar.time

            val yesterdaySpending = repository.getTotalSpent(yesterdayStart, yesterdayEnd)

            // Daily average (last 30 days)
            calendar.time = Date()
            calendar.add(Calendar.DAY_OF_YEAR, -30)
            val thirtyDaysAgo = calendar.time
            val totalLast30 = repository.getTotalSpent(thirtyDaysAgo, Date())
            val dailyAvg = totalLast30 / 30.0

            // Trends: this week vs last week
            calendar.time = Date()
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            val thisWeekStart = calendar.time
            val thisWeekSpending = repository.getTotalSpent(thisWeekStart, Date())

            calendar.time = thisWeekStart
            calendar.add(Calendar.WEEK_OF_YEAR, -1)
            val lastWeekStart = calendar.time
            calendar.add(Calendar.WEEK_OF_YEAR, 1)
            val lastWeekEnd = calendar.time
            val lastWeekSpending = repository.getTotalSpent(lastWeekStart, lastWeekEnd)

            val weekTrend = if (lastWeekSpending > 0) {
                ((thisWeekSpending - lastWeekSpending) / lastWeekSpending) * 100
            } else 0.0

            // Trends: this month vs last month
            calendar.time = Date()
            calendar.set(currentYear, currentMonth, 1, 0, 0, 0)
            val thisMonthStart = calendar.time
            val thisMonthSpending = repository.getTotalSpent(thisMonthStart, Date())

            calendar.time = thisMonthStart
            calendar.add(Calendar.MONTH, -1)
            val lastMonthStart = calendar.time
            calendar.add(Calendar.MONTH, 1)
            val lastMonthEnd = calendar.time
            val lastMonthSpending = repository.getTotalSpent(lastMonthStart, lastMonthEnd)

            val monthTrend = if (lastMonthSpending > 0) {
                ((thisMonthSpending - lastMonthSpending) / lastMonthSpending) * 100
            } else 0.0

            // Top categories (last 30 days)
            val debitTransactions = repository.getDebitTransactions()
            val last30Txns = debitTransactions.filter {
                it.date.after(thirtyDaysAgo) || it.date == thirtyDaysAgo
            }
            val categoryMap = last30Txns.groupBy { it.category }
                .mapValues { it.value.sumOf { txn -> txn.amount } }
                .toList()
                .sortedByDescending { it.second }
                .take(5)

            // Recent transactions (debit only)
            val recent = debitTransactions.take(5)

            CoroutineScope(Dispatchers.Main).launch {
                _monthlySpending.value = monthlySpending
                _yesterdaySpending.value = yesterdaySpending
                _dailyAverage.value = dailyAvg
                _trendWeek.value = weekTrend
                _trendMonth.value = monthTrend
                _topCategories.value = categoryMap
                _recentTransactions.value = recent
            }
        }
    }
}

class DashboardViewModelFactory(private val repository: TransactionRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
