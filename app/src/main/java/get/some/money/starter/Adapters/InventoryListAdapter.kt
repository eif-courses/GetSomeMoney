package get.some.money.starter.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import get.some.money.starter.Models.Inventory
import get.some.money.starter.R
import get.some.money.starter.Util.Language
import kotlinx.android.synthetic.main.inventory_item.view.*


class InventoryListAdapter(private val interaction: Interaction? = null, private val longClickInteraction: LongClickInteraction? = null) :
  ListAdapter<Inventory, InventoryListAdapter.InventoryViewHolder>(InventoryDC()) {


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InventoryViewHolder(
    LayoutInflater.from(parent.context)
      .inflate(R.layout.inventory_item, parent, false), interaction, longClickInteraction
  )

  var selectedPosition = 0 // You have to set this globally in the Adapter class


  override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
    holder.bind(getItem(position))

    holder.itemView.setSelected(selectedPosition == position);

    //holder.itemView.setBackgroundColor(if(selectedPosition == position) Color.parseColor("#7FD1FF") else Color.TRANSPARENT)


  }
  fun swapData(data: List<Inventory>) {
    submitList(data.toMutableList())
  }

  inner class InventoryViewHolder(
    itemView: View,
    private val interaction: Interaction?,
    private val longClickInteraction: LongClickInteraction?
  ) : RecyclerView.ViewHolder(itemView), OnClickListener, OnLongClickListener {

    init {
      itemView.setOnClickListener(this)
      itemView.setOnLongClickListener(this)
    }

    override fun onLongClick(v: View?): Boolean {
      if (adapterPosition == RecyclerView.NO_POSITION) return false
      val clicked = getItem(adapterPosition)
      // Updating old as well as new positions
      notifyItemChanged(selectedPosition);
      selectedPosition = adapterPosition;
      notifyItemChanged(selectedPosition);
      longClickInteraction?.long_click(clicked)
      return true
    }

    override fun onClick(v: View?) {

      if (adapterPosition == RecyclerView.NO_POSITION) return
      val clicked = getItem(adapterPosition)

      // Updating old as well as new positions
      notifyItemChanged(selectedPosition);
      selectedPosition = adapterPosition;
      notifyItemChanged(selectedPosition);

      interaction?.click_item(clicked)
    }

    fun bind(item: Inventory) = with(itemView) {
      // TODO: Bind the data with View
      //inventory_item_image.setImageResource(R.id.)

      val language = Language.getCurrentLanguage()
      if(language.equals("lt")) {
        when(item.type){
          "CAP" -> {inventory_item_type.text = String.format("%s", "Kepurė")}
          "SHIRT" -> {inventory_item_type.text = String.format("%s", "Maikė")}
          "JEANS" -> {inventory_item_type.text = String.format("%s", "Džinsai")}
          "GLASSES" -> {inventory_item_type.text = String.format("%s", "Akiniai")}
        }
      }
      else{
        inventory_item_type.text = item.type
      }


      inventory_item_image.setImageResource(item.imageID)
      extra_coins_txt.text = String.format("%s%d", "+", item.extraCoins)
      knowloge_points_txt.text = String.format("%s%d", "+", item.knowledge)


    }
  }

  interface Interaction {
    fun click_item(inventory: Inventory)
  }
  interface LongClickInteraction{
    fun long_click(inventory: Inventory)
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