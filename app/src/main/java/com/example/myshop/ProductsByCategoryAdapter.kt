package com.example.myshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemProductCartBinding
import com.example.myshop.databinding.ItemProductCartForProdByCategBinding

class ProductsByCategoryAdapter(
    private val onRootClick: (String) -> Unit,
    private val onAddBtnClick: (String) -> Unit
) : ListAdapter<Product, ProductsByCategoryAdapter.VH>(ProductDiffUtil()) {

    inner class VH(private val binding: ItemProductCartForProdByCategBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) = with(binding) {
            ivProductPicture.setImageResource(item.imageRes)
            tvProductTitle.text = item.title
            tvProductWeight.text = item.weight
            tvProductPrice.text = item.price

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

    class ProductDiffUtil : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

}




