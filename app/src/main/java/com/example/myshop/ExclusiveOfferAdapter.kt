package com.example.myshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemProductCartBinding

class ExclusiveOfferAdapter(
    private val productForExclusiveOffers: List<ProductForExclusiveOffer>,
    private val onRootClick: (String) -> Unit
) : RecyclerView.Adapter<ExclusiveOfferAdapter.ExclusiveOfferViewHolder>() {

    inner class ExclusiveOfferViewHolder(private var binding: ItemProductCartBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {

        fun bind(item: ProductForExclusiveOffer) = with(binding) {
            ivProductPicture.setImageResource(item.imageRes)
            tvProductTitle.text = item.title
            tvProductWeight.text = item.weight
            tvProductPrice.text = item.price

            binding.root.setOnClickListener {
                onRootClick (item.id)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExclusiveOfferViewHolder {
        val binding = ItemProductCartBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ExclusiveOfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExclusiveOfferViewHolder, position: Int) {
        holder.bind(productForExclusiveOffers[position])
    }

    override fun getItemCount(): Int {
        return productForExclusiveOffers.size

    }
}