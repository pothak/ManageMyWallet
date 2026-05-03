package com.managemywallet.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.managemywallet.WalletApplication
import com.managemywallet.R
import com.managemywallet.data.repository.TransactionRepository
import com.managemywallet.databinding.FragmentTransactionDetailBinding
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionDetailFragment : Fragment() {

    private var _binding: FragmentTransactionDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = TransactionRepository(
            (requireActivity().application as WalletApplication).database.transactionDao()
        )

        viewModel = ViewModelProvider(this, TransactionViewModelFactory(repository)).get(TransactionViewModel::class.java)

        val transactionId = arguments?.getLong(ARG_ID) ?: 0L
        viewModel.getTransactionById(transactionId)

        viewModel.selectedTransaction.observe(viewLifecycleOwner, Observer { transaction ->
            transaction?.let { t ->
                val prefix = if (t.type == com.managemywallet.data.entity.TransactionType.DEBIT) "-" else "+"
                binding.textAmount.text = "$prefix₹%.2f".format(t.amount)
                binding.textMerchant.text = t.merchant
                binding.textCategory.text = t.category
                binding.textDate.text = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(t.date)
                binding.textBank.text = t.bankName ?: "-"
                binding.textRef.text = t.referenceId ?: "-"

                val colorRes = if (t.type == com.managemywallet.data.entity.TransactionType.DEBIT) R.color.expense else R.color.income
                binding.textAmount.setTextColor(resources.getColor(colorRes, requireContext().theme))
            }
        })

        binding.btnDelete.setOnClickListener {
            viewModel.selectedTransaction.value?.let { transaction ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Transaction")
                    .setMessage("Are you sure you want to delete this transaction?")
                    .setPositiveButton("Delete") { _, _ ->
                        viewModel.deleteTransaction(transaction)
                        parentFragmentManager.popBackStack()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_ID = "transaction_id"

        fun newInstance(id: Long): TransactionDetailFragment {
            return TransactionDetailFragment().apply {
                arguments = Bundle().apply { putLong(ARG_ID, id) }
            }
        }
    }
}
