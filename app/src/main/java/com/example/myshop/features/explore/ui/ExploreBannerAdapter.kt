package com.example.myshop.features.explore.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemExploreCategoryBinding
import com.example.myshop.features.explore.presentation.ExploreCategoryUiModel

class ExploreBannerAdapter(
    private val onClick: (ExploreCategoryUiModel) -> Unit
) : ListAdapter<ExploreCategoryUiModel, ExploreBannerAdapter.VH>(ExploreBannerDiffUtil()) {


    inner class VH(private val binding: ItemExploreCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ExploreCategoryUiModel) = with(binding) {
            ivExploreBanner.setImageResource(item.image)
            tvExploreBanner.text = item.title

            exploreCardRoot.setCardBackgroundColor(
                ContextCompat.getColor(itemView.context, item.backgroundColorRes)
            )
            exploreCardRoot.strokeColor =
                ContextCompat.getColor(itemView.context, item.strokeColorRes)

            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemExploreCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}

class ExploreBannerDiffUtil : DiffUtil.ItemCallback<ExploreCategoryUiModel>() {

    override fun areItemsTheSame(
        oldItem: ExploreCategoryUiModel,
        newItem: ExploreCategoryUiModel
    ): Boolean {
        return oldItem.category == newItem.category
    }

    override fun areContentsTheSame(
        oldItem: ExploreCategoryUiModel,
        newItem: ExploreCategoryUiModel
    ): Boolean {
        return oldItem == newItem
    }
}
