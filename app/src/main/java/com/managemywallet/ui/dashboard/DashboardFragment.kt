package com.managemywallet.ui.dashboard

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.managemywallet.WalletApplication
import com.managemywallet.data.repository.TransactionRepository
import com.managemywallet.databinding.FragmentDashboardBinding
import com.managemywallet.ui.transactions.TransactionAdapter
import com.managemywallet.sms.SmsImporter
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DashboardViewModel
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = TransactionRepository(
            (requireActivity().application as WalletApplication).database.transactionDao()
        )

        viewModel = ViewModelProvider(requireActivity(), DashboardViewModelFactory(repository)).get(DashboardViewModel::class.java)

        transactionAdapter = TransactionAdapter()
        binding.recyclerRecent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }

        binding.buttonImportSms.setOnClickListener {
            lifecycleScope.launch {
                SmsImporter.importExistingSms(requireContext(), repository)
                viewModel.refreshData()
            }
        }

        observeData()
    }

    private fun observeData() {
        viewModel.monthlySpend.observe(viewLifecycleOwner) { spend ->
            binding.textExpense.text = "₹%.2f".format(spend)
            updateBalance()
        }

        viewModel.monthlyIncome.observe(viewLifecycleOwner) { income ->
            binding.textIncome.text = "₹%.2f".format(income)
            updateBalance()
        }

        viewModel.recentTransactions.observe(viewLifecycleOwner) { transactions ->
            transactionAdapter.submitList(transactions.take(5))
            binding.textEmpty.visibility = if (transactions.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun updateBalance() {
        val income = viewModel.monthlyIncome.value ?: 0.0
        val spend = viewModel.monthlySpend.value ?: 0.0
        val balance = income - spend
        binding.textBalanceAmount.text = "₹%.2f".format(balance)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
