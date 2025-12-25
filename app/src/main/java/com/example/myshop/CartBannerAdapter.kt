package com.example.myshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemCartBannerBinding

class CartBannerAdapter(
    private val cartBannerList: List<CartCategory>
) : RecyclerView.Adapter<CartBannerAdapter.CartBannerViewHolder>() {


    inner class CartBannerViewHolder(private val binding: ItemCartBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartBanner: CartCategory) = with(binding) {

            imageView.setImageResource(cartBanner.imageRes)
            tvProductTitle.text = cartBanner.title
            tvProductWeight.text = cartBanner.weight
            tvProductCount.text = cartBanner.count
            tvProductPrice.text = cartBanner.price

            btnProductAdd.setOnClickListener {
                cartBanner.count = (cartBanner.count.toInt() + 1).toString()
                tvProductCount.text = cartBanner.count
            }

            btnProductDecrease.setOnClickListener {
                if (cartBanner.count.toInt() > 1)
                    cartBanner.count = (cartBanner.count.toInt() - 1).toString()
                tvProductCount.text = cartBanner.count
            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartBannerViewHolder {
        val binding = ItemCartBannerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CartBannerViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CartBannerViewHolder, position: Int) {
        holder.bind(cartBannerList[position])

    }

    override fun getItemCount(): Int {
        return cartBannerList.size

    }

}