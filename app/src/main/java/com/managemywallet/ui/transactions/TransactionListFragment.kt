package com.managemywallet.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.managemywallet.WalletApplication
import com.managemywallet.data.entity.TransactionType
import com.managemywallet.data.repository.TransactionRepository
import com.managemywallet.databinding.FragmentTransactionListBinding

class TransactionListFragment : Fragment() {

    private var _binding: FragmentTransactionListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TransactionViewModel
    private lateinit var adapter: TransactionAdapter
    private var currentFilter: String = "all"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = TransactionRepository(
            (requireActivity().application as WalletApplication).database.transactionDao()
        )

        viewModel = ViewModelProvider(requireActivity(), TransactionViewModelFactory(repository)).get(TransactionViewModel::class.java)

        adapter = TransactionAdapter()
        binding.recyclerTransactions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@TransactionListFragment.adapter
        }

        adapter.onItemClick = { transaction ->
            val fragment = TransactionDetailFragment.newInstance(transaction.id)
            parentFragmentManager.beginTransaction()
                .replace(com.managemywallet.R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        // Filter chips
        binding.chipGroupFilter.setOnCheckedChangeListener { _, checkedId ->
            currentFilter = when (checkedId) {
                binding.chipDebit.id -> "debit"
                binding.chipCredit.id -> "credit"
                else -> "all"
            }
            filterTransactions()
        }

        // Search
        binding.editSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterTransactions()
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        viewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
            filterTransactions()
        }
    }

    private fun filterTransactions() {
        val allTransactions = viewModel.allTransactions.value ?: return
        val query = binding.editSearch.text.toString().lowercase()

        var filtered = allTransactions

        // Apply debit/credit filter
        filtered = when (currentFilter) {
            "debit" -> filtered.filter { it.type == TransactionType.DEBIT }
            "credit" -> filtered.filter { it.type == TransactionType.CREDIT }
            else -> filtered
        }

        // Apply search filter
        if (query.isNotEmpty()) {
            filtered = filtered.filter {
                it.merchant.lowercase().contains(query) ||
                it.category.lowercase().contains(query) ||
                it.smsContent?.lowercase()?.contains(query) == true
            }
        }

        adapter.submitList(filtered)
        binding.textEmpty.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
