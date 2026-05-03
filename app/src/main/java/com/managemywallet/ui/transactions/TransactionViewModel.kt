package com.managemywallet.ui.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.managemywallet.data.entity.Transaction
import com.managemywallet.data.repository.TransactionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(private val repository: TransactionRepository) : ViewModel() {

    val allTransactions = repository.getRecentTransactions(200)

    private val _selectedTransaction = MutableLiveData<Transaction?>()
    val selectedTransaction: LiveData<Transaction?> = _selectedTransaction

    fun getTransactionById(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            _selectedTransaction.postValue(repository.getTransactionById(id))
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteTransaction(transaction)
        }
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
