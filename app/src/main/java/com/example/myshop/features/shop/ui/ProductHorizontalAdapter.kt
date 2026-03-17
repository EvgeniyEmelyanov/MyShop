package com.example.myshop.features.shop.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemProductCartBinding
import com.example.myshop.features.shop.model.ProductCardUiModel

class ProductHorizontalAdapter(
    private val onRootClick: (String) -> Unit,
    private val onAddBtnClick: (String) -> Unit
) : ListAdapter<ProductCardUiModel, ProductHorizontalAdapter.VH>(ProductDiffCallback()) {

    inner class VH(private var binding: ItemProductCartBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {

        fun bind(item: ProductCardUiModel) = with(binding) {
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
        val binding = ItemProductCartBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}

class ProductDiffCallback : DiffUtil.ItemCallback<ProductCardUiModel>() {

    override fun areItemsTheSame(oldItem: ProductCardUiModel, newItem: ProductCardUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductCardUiModel, newItem: ProductCardUiModel): Boolean {
        return oldItem == newItem
    }
}