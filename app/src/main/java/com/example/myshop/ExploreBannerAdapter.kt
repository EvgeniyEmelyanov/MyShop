package com.example.myshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExploreBannerAdapter(
    val item: List<ExploreBanner>

): RecyclerView.Adapter<ExploreBannerAdapter.ExploreBannerViewHolder>() {

    inner class ExploreBannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivExploreBanner)
        val textView: TextView = itemView.findViewById(R.id.tvExploreBanner)

        val root: View = itemView.findViewById(R.id.exploreCardRoot)

        fun bind(item: ExploreBanner) {
            imageView.setImageResource(item.image)
            textView.text = item.title

            val tintBgColor = ContextCompat.getColor(itemView.context, item.backgroundColorRes)
            ViewCompat.setBackgroundTintList(root, ColorStateList.valueOf(tintBgColor))
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExploreBannerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_explore_banner, parent, false)
        return ExploreBannerViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ExploreBannerViewHolder,
        position: Int
    ) {
        holder.bind(item[position])
    }


    override fun getItemCount(): Int {
        return item.size

    }
}