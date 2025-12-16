package com.example.myshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExclusiveOfferAdapter(
    private val productForExclusiveOffers: List<ProductForExclusiveOffer>
) : RecyclerView.Adapter<ExclusiveOfferAdapter.ExclusiveOfferViewHolder>() {

    inner class ExclusiveOfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.ivProductPicture)
        val productTitle: TextView = itemView.findViewById(R.id.tvProductTitle)
        val productWeight: TextView = itemView.findViewById(R.id.tvProductWeight)
        val productPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        val btnProductAdd: ImageButton = itemView.findViewById(R.id.btnProductAdd)

        fun bind(productForExclusiveOffer: ProductForExclusiveOffer) {
            imageView.setImageResource(productForExclusiveOffer.imageRes)
            productTitle.text = productForExclusiveOffer.name
            productWeight.text = productForExclusiveOffer.weight
            productPrice.text = productForExclusiveOffer.price
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExclusiveOfferViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_cart, parent, false)
        return ExclusiveOfferViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExclusiveOfferViewHolder, position: Int) {
        val product = productForExclusiveOffers[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productForExclusiveOffers.size

    }
}