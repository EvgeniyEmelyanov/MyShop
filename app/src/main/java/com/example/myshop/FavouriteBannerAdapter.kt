package com.example.myshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemFavouriteBannerBinding

class FavouriteBannerAdapter(
    private val items: List<FavouriteCategory>
) : RecyclerView.Adapter<FavouriteBannerAdapter.FavouriteBannerViewHolder>() {

    inner class FavouriteBannerViewHolder(private val binding: ItemFavouriteBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favouriteBanner: FavouriteCategory) = with(binding) {
            imageView.setImageResource(favouriteBanner.imageRes)
            tvProductTitle.text = favouriteBanner.title
            tvProductWeight.text = favouriteBanner.weight
            tvProductPrice.text = favouriteBanner.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteBannerViewHolder {
        val binding = ItemFavouriteBannerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FavouriteBannerViewHolder(binding)

    }

    override fun onBindViewHolder(holder: FavouriteBannerViewHolder, position: Int) {
        holder.bind(items[position])

    }

    override fun getItemCount(): Int {
        return items.size
    
    }
}