package com.managemywallet.ui.analytics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.managemywallet.WalletApplication
import com.managemywallet.R
import com.managemywallet.data.repository.TransactionRepository
import com.managemywallet.databinding.FragmentAnalyticsBinding
import java.util.Calendar

class AnalyticsFragment : Fragment() {

    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AnalyticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = TransactionRepository(
            (requireActivity().application as WalletApplication).database.transactionDao()
        )

        viewModel = ViewModelProvider(this, AnalyticsViewModelFactory(repository)).get(AnalyticsViewModel::class.java)

        viewModel.categorySpend.observe(viewLifecycleOwner) { data ->
            if (data.isNullOrEmpty()) {
                binding.textEmpty.visibility = View.VISIBLE
                binding.cardChart.visibility = View.GONE
                binding.cardBar.visibility = View.GONE
            } else {
                binding.textEmpty.visibility = View.GONE
                binding.cardChart.visibility = View.VISIBLE
                binding.cardBar.visibility = View.VISIBLE
                @Suppress("UNCHECKED_CAST")
                setupPieChart(data as Map<String, Double>)
            }
        }

        viewModel.dailySpending.observe(viewLifecycleOwner) { data ->
            if (data.isNotEmpty()) {
                setupBarChart(data)
            }
        }
    }

    private fun setupPieChart(categoryMap: Map<String, Double>) {
        val entries = categoryMap.map { PieEntry(it.value.toFloat(), it.key) }

        val dataSet = PieDataSet(entries, "Spending by Category").apply {
            colors = listOf(
                requireContext().getColor(R.color.card_1),
                requireContext().getColor(R.color.card_2),
                requireContext().getColor(R.color.card_3),
                requireContext().getColor(R.color.card_4),
                requireContext().getColor(R.color.primary),
                requireContext().getColor(R.color.income),
                requireContext().getColor(R.color.warning),
                requireContext().getColor(R.color.expense)
            )
            valueTextSize = 12f
            valueTextColor = Color.WHITE
            valueFormatter = PercentFormatter()
            sliceSpace = 2f
            selectionShift = 5f
        }

        binding.pieChart.apply {
            this.data = PieData(dataSet)
            centerText = "Categories"
            setCenterTextSize(14f)
            description.isEnabled = false
            legend.isEnabled = true
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            legend.textSize = 11f
            isDrawHoleEnabled = true
            holeRadius = 50f
            transparentCircleRadius = 55f
            animateY(800)
            invalidate()
        }
    }

    private fun setupBarChart(dailyMap: Map<String, Double>) {
        val entries = dailyMap.entries.mapIndexed { index, entry ->
            BarEntry(index.toFloat(), entry.value.toFloat())
        }

        val labels = dailyMap.keys.toList()

        val dataSet = BarDataSet(entries, "Daily Spending").apply {
            color = requireContext().getColor(R.color.primary)
            valueTextSize = 10f
            valueTextColor = requireContext().getColor(R.color.on_surface)
        }

        binding.barChart.apply {
            this.data = BarData(dataSet).apply { barWidth = 0.6f }
            description.isEnabled = false
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                labelCount = labels.size.coerceAtMost(7)
                valueFormatter = IndexAxisValueFormatter(labels)
                textSize = 9f
            }
            axisLeft.apply {
                setDrawGridLines(false)
                textSize = 10f
            }
            axisRight.isEnabled = false
            legend.isEnabled = false
            animateY(600)
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
