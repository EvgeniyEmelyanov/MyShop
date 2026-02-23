package com.example.myshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemAccountMenuBinding

class AccountMenuAdapter(
    private val onRootClick: (Int) -> Unit
) : ListAdapter<AccountMenuItem, AccountMenuAdapter.VH>(AccountDiffUtil()) {

    inner class VH(private val binding: ItemAccountMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AccountMenuItem) = with(binding) {
            ivIcon.setImageResource(item.iconRes)
            tvTitle.setText(item.title)


            root.setOnClickListener {
                onRootClick(item.id)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemAccountMenuBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}

class AccountDiffUtil : DiffUtil.ItemCallback<AccountMenuItem>() {
    override fun areItemsTheSame(
        oldItem: AccountMenuItem,
        newItem: AccountMenuItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: AccountMenuItem,
        newItem: AccountMenuItem
    ): Boolean {
        return oldItem == newItem
    }

}