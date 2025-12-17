import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.ExploreBanner
import com.example.myshop.R
import com.google.android.material.card.MaterialCardView

class ExploreBannerAdapter(
    private val items: List<ExploreBanner>
) : RecyclerView.Adapter<ExploreBannerAdapter.ExploreBannerViewHolder>() {

    inner class ExploreBannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.ivExploreBanner)
        private val textView: TextView = itemView.findViewById(R.id.tvExploreBanner)
        private val root: MaterialCardView =
            itemView.findViewById(R.id.exploreCardRoot)

        fun bind(item: ExploreBanner) {
            imageView.setImageResource(item.image)
            textView.text = item.title

            root.setCardBackgroundColor(
                ContextCompat.getColor(itemView.context, item.backgroundColorRes)
            )

            root.strokeColor =
                ContextCompat.getColor(itemView.context, item.strokeColorRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreBannerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_explore_banner, parent, false)
        return ExploreBannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExploreBannerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
