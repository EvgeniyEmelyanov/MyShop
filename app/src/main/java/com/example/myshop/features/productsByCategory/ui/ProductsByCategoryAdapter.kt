package com.example.myshop.features.productsByCategory.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.core.ui.CommonProductUiModel
import com.example.myshop.databinding.ItemProductCartForProdByCategBinding

class ProductsByCategoryAdapter(
    private val onRootClick: (String) -> Unit,
    private val onAddBtnClick: (String) -> Unit
) : ListAdapter<CommonProductUiModel, ProductsByCategoryAdapter.VH>(ProductDiffUtil()) {

    inner class VH(private val binding: ItemProductCartForProdByCategBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommonProductUiModel) = with(binding) {
            ivProductPicture.setImageResource(item.imageRes)
            tvProductTitle.text = item.title
            tvProductWeight.text = item.subtitle
            tvProductPrice.text = item.priceText

            binding.root.setOnClickListener {
                onRootClick(item.id)
            }

            binding.bntAddToCartFromItemCard.setOnClickListener {
                onAddBtnClick(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemProductCartForProdByCategBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductDiffUtil : DiffUtil.ItemCallback<CommonProductUiModel>() {

        override fun areItemsTheSame(
            oldItem: CommonProductUiModel,
            newItem: CommonProductUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            newItem: CommonProductUiModel,
            oldItem: CommonProductUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }

}




