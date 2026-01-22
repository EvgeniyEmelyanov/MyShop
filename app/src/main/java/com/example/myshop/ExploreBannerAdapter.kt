import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.ExploreBanner
import com.example.myshop.databinding.ItemExploreBannerBinding

class ExploreBannerAdapter(
    private val items: List<ExploreBanner>,
    private val onClick: (ExploreBanner) -> Unit
) : RecyclerView.Adapter<ExploreBannerAdapter.VH>() {

    inner class VH(private val binding: ItemExploreBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ExploreBanner) = with(binding) {
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
        val binding = ItemExploreBannerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
