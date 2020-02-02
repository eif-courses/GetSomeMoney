package get.some.money.starter.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import get.some.money.starter.Models.Guide
import get.some.money.starter.R

class GuideListAdapter(private val interaction: Interaction? = null) :
  ListAdapter<Guide, GuideListAdapter.GuideViewHolder>(GuideDC()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GuideViewHolder(
    LayoutInflater.from(parent.context)
      .inflate(R.layout.guide_item, parent, false), interaction
  )

  override fun onBindViewHolder(holder: GuideViewHolder, position: Int) =
    holder.bind(getItem(position))

  fun swapData(data: List<Guide>) {
    submitList(data.toMutableList())
  }

  inner class GuideViewHolder(
    itemView: View,
    private val interaction: Interaction?
  ) : RecyclerView.ViewHolder(itemView), OnClickListener {

    init {
      itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

      if (adapterPosition == RecyclerView.NO_POSITION) return

      val clicked = getItem(adapterPosition)
    }

    fun bind(item: Guide) = with(itemView) {

      val title = itemView.findViewById<TextView>(R.id.guide_title_text)
      val top = itemView.findViewById<TextView>(R.id.guide_paragraph_top)
      val image = itemView.findViewById<ImageView>(R.id.gude_image)
      val bottom = itemView.findViewById<TextView>(R.id.guide_paragraph_bottom)

      title.text = item.title
      top.text = item.textTop
      image.setImageResource(item.image)
      bottom.text = item.textBottom
    }
  }

  interface Interaction {

  }

  private class GuideDC : DiffUtil.ItemCallback<Guide>() {
    override fun areItemsTheSame(
      oldItem: Guide,
      newItem: Guide
    ): Boolean {
      return oldItem == newItem

    }

    override fun areContentsTheSame(
      oldItem: Guide,
      newItem: Guide
    ): Boolean {
      return oldItem.equals(newItem)
    }
  }
}