package com.example.myshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BestSellingAdapter(
    private val productForBestSelling: List<ProductForBestSelling>
): RecyclerView.Adapter<BestSellingAdapter.BestSellingViewHolder>(){

    inner class BestSellingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val imageView: ImageView = itemView.findViewById(R.id.ivProductPicture)
        private val productTitle: TextView = itemView.findViewById(R.id.tvProductTitle)
        private val productWeight: TextView = itemView.findViewById(R.id.tvProductWeight)
        private val productPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        private val btnProductAdd: ImageButton = itemView.findViewById(R.id.btnProductAdd)

        fun bind(productForBestSelling: ProductForBestSelling){
            imageView.setImageResource(productForBestSelling.imageRes)
            productTitle.text = productForBestSelling.title
            productWeight.text = productForBestSelling.weight
            productPrice.text = productForBestSelling.price

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellingViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_cart, parent, false)
        return BestSellingViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: BestSellingViewHolder, position: Int) {
        val product = productForBestSelling[position]
        holder.bind(product)

    }

    override fun getItemCount(): Int {
        return productForBestSelling.size

    }


}
