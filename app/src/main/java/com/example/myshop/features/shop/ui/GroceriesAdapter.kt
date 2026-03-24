package com.example.myshop.features.shop.ui

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemGroceriesBinding
import com.example.myshop.features.shop.model.GroceriesCategoryUiModel

class GroceriesAdapter : ListAdapter<GroceriesCategoryUiModel, GroceriesAdapter.VH>(GroceriesDiffUtil()) {

    class VH(private val binding: ItemGroceriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroceriesCategoryUiModel) = with(binding) {
            ivGroceriesPicture.setImageResource(item.imageRes)
            tvGroceriesTitle.text = item.title
            
            val color = ContextCompat.getColor(
                root.context,
                item.backgroundColorRes
                )
            ViewCompat.setBackgroundTintList(groceryCardRoot, ColorStateList.valueOf(color))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemGroceriesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}

class GroceriesDiffUtil : DiffUtil.ItemCallback<GroceriesCategoryUiModel>() {
    override fun areItemsTheSame(oldItem: GroceriesCategoryUiModel, newItem: GroceriesCategoryUiModel): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: GroceriesCategoryUiModel, newItem: GroceriesCategoryUiModel): Boolean {
        return oldItem == newItem
    }
}
