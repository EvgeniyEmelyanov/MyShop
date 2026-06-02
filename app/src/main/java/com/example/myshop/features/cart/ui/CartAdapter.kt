package com.example.myshop.features.cart.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemCartProductBinding
import com.example.myshop.features.cart.presentation.CartUiModel

class CartAdapter(
    private val onClickIncrease: (String) -> Unit,
    private val onClickDecrease: (String) -> Unit,
    private val onClickDelete: (String) -> Unit
) : ListAdapter<CartUiModel, CartAdapter.VH>(CartDiffUtil()) {

    inner class VH(private val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartUiModel) = with(binding) {

            imageView.setImageResource(item.imageRes)
            tvProductTitle.text = item.titleText
            tvProductWeight.text = item.subtitleText
            tvProductCount.text = item.quantityText
            tvProductPrice.text = item.lineTotalText

            btnProductIncreaseItemCartBanner.setOnClickListener {
                onClickIncrease(item.productId)
            }

            btnProductDecreaseItemCartBanner.setOnClickListener {
                onClickDecrease(item.productId)
            }

            btnDeleteItem.setOnClickListener {
                onClickDelete(item.productId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemCartProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}

class CartDiffUtil : DiffUtil.ItemCallback<CartUiModel>() {

    override fun areItemsTheSame(
        p0: CartUiModel, p1: CartUiModel
    ): Boolean {
        return p0.productId == p1.productId
    }

    override fun areContentsTheSame(
        p0: CartUiModel, p1: CartUiModel
    ): Boolean {
        return p0 == p1

    }
}
