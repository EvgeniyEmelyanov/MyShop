import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.ExploreBanner
import com.example.myshop.databinding.ItemExploreBannerBinding

class ExploreBannerAdapter(
    private val items: List<ExploreBanner>
) : RecyclerView.Adapter<ExploreBannerAdapter.ExploreBannerViewHolder>() {

    inner class ExploreBannerViewHolder(
        private val binding: ItemExploreBannerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ExploreBanner) = with(binding) {
            ivExploreBanner.setImageResource(item.image)
            tvExploreBanner.text = item.title

            exploreCardRoot.setCardBackgroundColor(
                ContextCompat.getColor(itemView.context, item.backgroundColorRes)
            )
            exploreCardRoot.strokeColor =
                ContextCompat.getColor(itemView.context, item.strokeColorRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreBannerViewHolder {
        val binding = ItemExploreBannerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ExploreBannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExploreBannerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}

