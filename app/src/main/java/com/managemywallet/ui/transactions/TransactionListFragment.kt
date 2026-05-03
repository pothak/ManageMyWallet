package com.managemywallet.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.managemywallet.WalletApplication
import com.managemywallet.data.repository.TransactionRepository
import com.managemywallet.databinding.FragmentTransactionListBinding

class TransactionListFragment : Fragment() {

    private var _binding: FragmentTransactionListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TransactionViewModel
    private lateinit var adapter: TransactionAdapter

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

        viewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
            adapter.submitList(transactions)
            binding.textEmpty.visibility = if (transactions.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
