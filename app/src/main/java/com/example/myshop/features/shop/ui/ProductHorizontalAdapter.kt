package com.example.myshop.features.shop.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemProductHorizontalBinding
import com.example.myshop.core.ui.CommonProductUiModel

class ProductHorizontalAdapter(
    private val onRootClick: (String) -> Unit, private val onAddBtnClick: (String) -> Unit
) : ListAdapter<CommonProductUiModel,
        ProductHorizontalAdapter.VH>(ProductDiffCallback()) {

    inner class VH(private var binding: ItemProductHorizontalBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

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
        val binding = ItemProductHorizontalBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}

class ProductDiffCallback : DiffUtil.ItemCallback<CommonProductUiModel>() {

    override fun areItemsTheSame(
        oldItem: CommonProductUiModel, newItem: CommonProductUiModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CommonProductUiModel, newItem: CommonProductUiModel
    ): Boolean {
        return oldItem == newItem
    }
}
