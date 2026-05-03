package com.managemywallet.ui.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.managemywallet.R
import com.managemywallet.data.entity.Transaction
import com.managemywallet.data.entity.TransactionType
import com.managemywallet.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionAdapter : ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(DiffCallback()) {

    var onItemClick: ((Transaction) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TransactionViewHolder(
        private val binding: ItemTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
        }

        fun bind(transaction: Transaction) {
            binding.textMerchant.text = transaction.merchant
            binding.textCategory.text = transaction.category

            val prefix = if (transaction.type == TransactionType.DEBIT) "-" else "+"
            binding.textAmount.text = "$prefix₹%.2f".format(transaction.amount)

            val colorRes = if (transaction.type == TransactionType.DEBIT) R.color.expense else R.color.income
            binding.textAmount.setTextColor(binding.root.context.getColor(colorRes))

            val indicatorColors = mapOf(
                "Food" to R.color.card_1,
                "Shopping" to R.color.card_2,
                "Transport" to R.color.card_3,
                "Bills" to R.color.card_4,
                "Entertainment" to R.color.card_1,
                "Health" to R.color.card_2,
                "Grocery" to R.color.card_3,
                "Investment" to R.color.card_4,
                "ATM" to R.color.card_1,
                "Transfer" to R.color.card_2,
                "Other" to R.color.on_surface_light
            )
            binding.categoryIndicator.setBackgroundColor(
                binding.root.context.getColor(indicatorColors[transaction.category] ?: R.color.on_surface_light)
            )
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem == newItem
    }
}
