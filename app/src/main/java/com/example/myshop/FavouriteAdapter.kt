package com.example.myshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemFavouriteBannerBinding

class FavouriteAdapter(
    private var items: List<FavouriteUiModel>,
    private val onClickBtnArrow: (String) -> Unit
) : RecyclerView.Adapter<FavouriteAdapter.VH>() {
    inner class VH(private val binding: ItemFavouriteBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavouriteUiModel) = with(binding) {
            imageView.setImageResource(item.imageRes)
            tvProductTitle.text = item.titleText
            tvProductWeight.text = item.weightText
            tvProductPrice.text = item.priceText

            root.setOnClickListener {
                onClickBtnArrow(item.productId)
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
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(newItems: List<FavouriteUiModel>) {
        items = newItems
        notifyDataSetChanged() // потом сделаем DiffUtil, пока ок
    }
}
