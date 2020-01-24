package get.some.money.starter.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import get.some.money.starter.Models.Item
import get.some.money.starter.R
import kotlinx.android.synthetic.main.shop_item.view.*

class ShopListAdapter(private val interaction: Interaction? = null) :
    androidx.recyclerview.widget.ListAdapter<Item, ShopListAdapter.ShopViewHolder>(ItemDC()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ShopViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.shop_item, parent, false), interaction
    )

    public override fun onBindViewHolder(holder: ShopViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun swapData(data: List<Item>) {
        submitList(data.toMutableList())
    }

    inner class ShopViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView), OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return
            val clicked = getItem(adapterPosition)
            interaction?.item_clicked(clicked)

        }

        fun bind(item: Item) = with(itemView) {
            shop_item_title.text = item.name
            shop_item_price.text = "${item.price}"
            val image:ImageView = this.findViewById(R.id.shop_item_image)
            Picasso.get().load(item.imageURL).into(image)
            // TODO: Bind the data with View
        }
    }

    interface Interaction {
        fun item_clicked(clicked: Item);
    }

    private class ItemDC : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(
            oldItem: Item,
            newItem: Item
        ): Boolean {
            return oldItem == newItem;
        }

        override fun areContentsTheSame(
            oldItem: Item,
            newItem: Item
        ): Boolean {
            return oldItem.equals(newItem);
        }
    }
}