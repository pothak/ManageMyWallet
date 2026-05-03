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
        val monthlyBudget = 18000.0

        viewModel.monthlySpending.observe(viewLifecycleOwner) { spending ->
            val percentage = if (monthlyBudget > 0) (spending / monthlyBudget * 100).toInt().coerceAtMost(100) else 0
            binding.textSpentAmount.text = "₹%.2f / ₹%.0f".format(spending, monthlyBudget)
            binding.progressBudget.progress = percentage
            val remaining = monthlyBudget - spending
            binding.textRemaining.text = requireContext().getString(com.managemywallet.R.string.dashboard_remaining, remaining.coerceAtLeast(0.0))
        }

        viewModel.yesterdaySpending.observe(viewLifecycleOwner) { amount ->
            binding.textYesterday.text = "₹%.2f".format(amount)
        }

        viewModel.dailyAverage.observe(viewLifecycleOwner) { avg ->
            binding.textDailyAvg.text = "₹%.2f".format(avg)
        }

        viewModel.trendWeek.observe(viewLifecycleOwner) { trend ->
            val symbol = if (trend >= 0) "↑" else "↓"
            val color = if (trend >= 0) requireContext().getColor(com.managemywallet.R.color.expense) else requireContext().getColor(com.managemywallet.R.color.income)
            binding.textTrendWeek.text = "%s %.0f%%".format(symbol, Math.abs(trend))
            binding.textTrendWeek.setTextColor(color)
        }

        viewModel.trendMonth.observe(viewLifecycleOwner) { trend ->
            val symbol = if (trend >= 0) "↑" else "↓"
            val color = if (trend >= 0) requireContext().getColor(com.managemywallet.R.color.expense) else requireContext().getColor(com.managemywallet.R.color.income)
            binding.textTrendMonth.text = "%s %.0f%%".format(symbol, Math.abs(trend))
            binding.textTrendMonth.setTextColor(color)
        }

        viewModel.topCategories.observe(viewLifecycleOwner) { categories ->
            binding.layoutCategories.removeAllViews()
            categories.take(5).forEachIndexed { index, (category, amount) ->
                val textView = android.widget.TextView(requireContext()).apply {
                    text = "${index + 1}. $category  ₹%.2f".format(amount)
                    textSize = 14f
                    setTextColor(requireContext().getColor(com.managemywallet.R.color.on_surface))
                    setPadding(0, 8, 0, 8)
                }
                binding.layoutCategories.addView(textView)
            }
        }

        viewModel.recentTransactions.observe(viewLifecycleOwner) { transactions ->
            transactionAdapter.submitList(transactions)
            binding.textEmpty.visibility = if (transactions.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
