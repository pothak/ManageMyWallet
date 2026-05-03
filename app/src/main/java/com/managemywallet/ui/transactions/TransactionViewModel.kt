package com.managemywallet.ui.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.managemywallet.data.entity.Transaction
import com.managemywallet.data.repository.TransactionRepository
import kotlinx.coroutines.launch

class TransactionViewModel(private val repository: TransactionRepository) : ViewModel() {

    private val _allTransactions = MutableLiveData<List<Transaction>>()
    val allTransactions: LiveData<List<Transaction>> = _allTransactions

    private val _selectedTransaction = MutableLiveData<Transaction?>()
    val selectedTransaction: LiveData<Transaction?> = _selectedTransaction

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            repository.getAllTransactions().observeForever { transactions ->
                _allTransactions.postValue(transactions)
            }
        }
    }

    fun getTransactionById(id: Long) {
        viewModelScope.launch {
            _selectedTransaction.postValue(repository.getTransactionById(id))
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
            loadTransactions() // Refresh after delete
        }
    }

    fun refreshData() {
        loadTransactions()
    }
}

class TransactionViewModelFactory(private val repository: TransactionRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
