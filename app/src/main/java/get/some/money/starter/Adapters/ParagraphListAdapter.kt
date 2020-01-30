package get.some.money.starter.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import get.some.money.starter.Models.Paragraph
import get.some.money.starter.R
import kotlinx.android.synthetic.main.paragraph_item.view.*

class ParagraphListAdapter(
  private val interaction: Interaction? = null
) :
  ListAdapter<Paragraph, ParagraphListAdapter.ParagraphViewHolder>(ParagraphDC()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ParagraphViewHolder(
    LayoutInflater.from(parent.context)
      .inflate(R.layout.paragraph_item, parent, false), interaction
  )

  override fun onBindViewHolder(holder: ParagraphViewHolder, position: Int) =
    holder.bind(getItem(position))

  fun swapData(data: List<Paragraph>) {
    submitList(data.toMutableList())
  }

  inner class ParagraphViewHolder(
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

    fun bind(item: Paragraph) = with(itemView) {
      // TODO: Bind the data with View
      text_for_paragraph.text = item.text
      title_for_paragraph.text = item.title
    }
  }

  interface Interaction {

  }

  private class ParagraphDC : DiffUtil.ItemCallback<Paragraph>() {
    override fun areItemsTheSame(
      oldItem: Paragraph,
      newItem: Paragraph
    ): Boolean {
      return oldItem == newItem

    }

    override fun areContentsTheSame(
      oldItem: Paragraph,
      newItem: Paragraph
    ): Boolean {
      return oldItem.equals(newItem)
    }
  }
}