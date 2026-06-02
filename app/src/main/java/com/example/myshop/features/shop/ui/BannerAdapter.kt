package com.example.myshop.features.shop.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemShopBannerBinding
import com.example.myshop.features.shop.presentation.BannerUiModel

class BannerAdapter : ListAdapter<BannerUiModel, BannerAdapter.VH>(BannerDiffUtil()) {
    class VH(private val binding: ItemShopBannerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BannerUiModel) = with(binding) {
            firstTextTv.text = item.title
            secondTextTv.text = item.subtitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemShopBannerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}

class BannerDiffUtil : DiffUtil.ItemCallback<BannerUiModel>() {
    override fun areItemsTheSame(oldItem: BannerUiModel, newItem: BannerUiModel): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: BannerUiModel, newItem: BannerUiModel): Boolean {
        return oldItem == newItem
    }
}
