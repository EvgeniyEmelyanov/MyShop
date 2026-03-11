package com.example.myshop.features.shop.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.Product
import com.example.myshop.core.ui.image.ImageKeyResolver
import com.example.myshop.databinding.ItemProductCartBinding

class ProductHorizontalAdapter(
    private val item: List<Product>,
    private val onRootClick: (String) -> Unit,
    private val onAddBtnClick: (String) -> Unit
) : RecyclerView.Adapter<ProductHorizontalAdapter.VH>() {

    inner class VH(private var binding: ItemProductCartBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {

        fun bind(item: Product) = with(binding) {
            ivProductPicture.setImageResource(ImageKeyResolver.resolve(item.imageKey))
            tvProductTitle.text = item.title
            tvProductWeight.text = item.weight
            tvProductPrice.text = item.price


            binding.root.setOnClickListener {
                onRootClick (item.id)
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
        holder.bind(item[position])
    }

    override fun getItemCount(): Int {
        return item.size

    }
}