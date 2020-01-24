package get.some.money.starter.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import get.some.money.starter.Models.Inventory
import get.some.money.starter.R
import kotlinx.android.synthetic.main.inventory_item.view.*

class InventoryListAdapter(private val interaction: Interaction? = null) :
  ListAdapter<Inventory, InventoryListAdapter.InventoryViewHolder>(InventoryDC()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InventoryViewHolder(
    LayoutInflater.from(parent.context)
      .inflate(R.layout.inventory_item, parent, false), interaction
  )

  override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) =
    holder.bind(getItem(position))

  fun swapData(data: List<Inventory>) {
    submitList(data.toMutableList())
  }

  inner class InventoryViewHolder(
    itemView: View,
    private val interaction: Interaction?
  ) : RecyclerView.ViewHolder(itemView), OnClickListener {

    init {
      itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

      if (adapterPosition == RecyclerView.NO_POSITION) return
      val clicked = getItem(adapterPosition)
      interaction?.click_item(clicked)
    }

    fun bind(item: Inventory) = with(itemView) {
      // TODO: Bind the data with View
      //inventory_item_image.setImageResource(R.id.)
      Picasso.get().load(item.imageURL).into(itemView.inventory_item_image)
      extra_coins_txt.text = item.extraCoins.toString()
      knowloge_points_txt.text = item.knowledge.toString()


    }
  }

  interface Interaction {
    fun click_item(inventory: Inventory)
  }

  private class InventoryDC : DiffUtil.ItemCallback<Inventory>() {
    override fun areItemsTheSame(
      oldItem: Inventory,
      newItem: Inventory
    ): Boolean {
      return oldItem == newItem

    }

    override fun areContentsTheSame(
      oldItem: Inventory,
      newItem: Inventory
    ): Boolean {
      return oldItem.equals(newItem)
    }
  }
}