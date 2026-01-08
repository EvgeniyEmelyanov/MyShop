package com.example.myshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemCartBannerBinding

class CartAdapter(
    private var items: List<CartUiModel>,
    private val onClickIncrease: (String) -> Unit,
    private val onClickDecrease: (String) -> Unit
) : RecyclerView.Adapter<CartAdapter.VH>() {


    inner class VH(private val binding: ItemCartBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartUiModel) = with(binding) {

            imageView.setImageResource(item.imageRes)
            tvProductTitle.text = item.title
            tvProductWeight.text = item.weightText
            tvProductCount.text = item.quantityText
            tvProductPrice.text = item.lineTotalText

            btnBtnProductIncrease.setOnClickListener {
                onClickIncrease(item.productId)
            }

            btnProductDecrease.setOnClickListener {
                onClickDecrease(item.productId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemCartBannerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(newItems: List<CartUiModel>) {
        items = newItems
        notifyDataSetChanged() // потом сделаем DiffUtil, пока ок
    }
}