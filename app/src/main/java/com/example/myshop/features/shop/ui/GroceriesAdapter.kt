package com.example.myshop.features.shop.ui

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.R
import com.example.myshop.features.shop.model.GroceriesCategoryUiModel

class GroceriesAdapter(
    private var groceries: List<GroceriesCategoryUiModel>
) : RecyclerView.Adapter<GroceriesAdapter.GroceriesViewHolder>() {


    inner class GroceriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.ivGroceriesPicture)
        private val title: TextView = itemView.findViewById(R.id.tvGroceriesTitle)
        private val root: View = itemView.findViewById(R.id.groceryCardRoot)

        fun bind(groceriesCategoryUiModel: GroceriesCategoryUiModel) {
            imageView.setImageResource(groceriesCategoryUiModel.imageRes)
            title.text = groceriesCategoryUiModel.title
            val color = ContextCompat.getColor(
                itemView.context,
                groceriesCategoryUiModel.backgroundColorRes
            )
            ViewCompat.setBackgroundTintList(root, ColorStateList.valueOf(color))

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroceriesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_groceries, parent, false)
        return GroceriesViewHolder(itemView)

    }

    override fun onBindViewHolder(
        holder: GroceriesViewHolder,
        position: Int
    ) {
        holder.bind(groceries[position])

    }

    override fun getItemCount(): Int {
        return groceries.size

    }

    fun submitList(newGroceries: List<GroceriesCategoryUiModel>) {
        groceries = newGroceries
        notifyDataSetChanged()

    }

}