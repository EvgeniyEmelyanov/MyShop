package com.example.myshop.features.favourite.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemFavouriteBannerBinding
import com.example.myshop.features.favourite.presentation.FavouriteUiModel

class FavouriteAdapter(
    private val onClickItem: (String) -> Unit
) : ListAdapter<FavouriteUiModel, FavouriteAdapter.VH>(FavouriteDiffUtil()) {
    inner class VH(private val binding: ItemFavouriteBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavouriteUiModel) = with(binding) {
            imageView.setImageResource(item.imageRes)
            tvProductTitle.text = item.title
            tvProductWeight.text = item.subtitle
            tvProductPrice.text = item.priceText

            root.setOnClickListener {
                onClickItem(item.productId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemFavouriteBannerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}

class FavouriteDiffUtil : DiffUtil.ItemCallback<FavouriteUiModel>() {
    override fun areItemsTheSame(
        oldItem: FavouriteUiModel,
        newItem: FavouriteUiModel
    ): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(
        oldItem: FavouriteUiModel,
        newItem: FavouriteUiModel
    ): Boolean {
        return oldItem == newItem

    }

}