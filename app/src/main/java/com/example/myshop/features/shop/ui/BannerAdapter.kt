package com.example.myshop.features.shop.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.R
import com.example.myshop.features.shop.model.BannerUiModel

class BannerAdapter(
    private var items: List<BannerUiModel>
) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView = itemView.findViewById(R.id.firstText_tv)
        val subtitleTv: TextView = itemView.findViewById(R.id.secondText_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val item = items[position]
        holder.titleTv.text = item.title
        holder.subtitleTv.text = item.subtitle
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<BannerUiModel>) {
        items = newItems
        notifyDataSetChanged()

    }

}