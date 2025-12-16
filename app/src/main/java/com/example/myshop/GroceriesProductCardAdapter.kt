package com.example.myshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroceriesProductCardAdapter(
    private val item: List<GroceriesProductCard>
) : RecyclerView.Adapter<GroceriesProductCardAdapter.GroceriesProductCardViewHolder>() {

    inner class GroceriesProductCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.ivProductPicture)
        private val productTitle: TextView = itemView.findViewById(R.id.tvProductTitle)
        private val productWeight: TextView = itemView.findViewById(R.id.tvProductWeight)
        private val productPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        private val btnProductAdd: ImageButton = itemView.findViewById(R.id.btnProductAdd)


        fun bind(groceriesProductCard: GroceriesProductCard) {
            imageView.setImageResource(groceriesProductCard.imageRes)
            productTitle.text = groceriesProductCard.name
            productWeight.text = groceriesProductCard.weight
            productPrice.text = groceriesProductCard.price

        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroceriesProductCardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_cart, parent, false)
        return GroceriesProductCardViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: GroceriesProductCardViewHolder, position: Int) {
        val product = item[position]
        holder.bind(product)

    }

    override fun getItemCount(): Int {
        return item.size


    }
}
