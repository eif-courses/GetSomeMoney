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
import com.squareup.picasso.Picasso
import get.some.money.starter.Models.Category
import get.some.money.starter.R
import kotlinx.android.synthetic.main.category_item.view.*

class CategoryListAdapter(private val interaction: Interaction? = null) :
  ListAdapter<Category, CategoryListAdapter.CategoryViewHolder>(CategoryDC()) {

  var categories = mutableMapOf<String, Int>()
  var completeLevels = 0
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
    LayoutInflater.from(parent.context)
      .inflate(R.layout.category_item, parent, false), interaction
  )

  override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
    holder.bind(getItem(position))

  fun swapData(data: List<Category>) {
    submitList(data.toMutableList())
  }


  fun sendPercentageCompleted(
    a: MutableMap<String, Int>,
    levelSize: Int
  ) {
    categories = a
    completeLevels = levelSize
  }

  inner class CategoryViewHolder(
    itemView: View,
    private val interaction: Interaction?
  ) : RecyclerView.ViewHolder(itemView), OnClickListener {

    init {
      itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

      if (adapterPosition == RecyclerView.NO_POSITION) return

      val clicked = getItem(adapterPosition)

      interaction?.clickCategory(clicked)
    }

    fun bind(item: Category) = with(itemView) {
      // TODO: Bind the data with View
      val title:TextView = itemView.findViewById(R.id.category_title)
      val image: ImageView = itemView.findViewById(R.id.category_imageview)
      title.text = item.title


      for(cat in categories) {
        if (item.title.equals(cat.key)) {
          itemView.categoryProgress.progress = ((completeLevels.toDouble()) * 100 / cat.value.toDouble()).toInt()
          itemView.currentProgressPercentage.text = "${(( completeLevels.toDouble() / cat.value.toDouble()) * 100).toInt()} %"
        }
      }

      //itemView.currentProgressPercentage.text = "$percent %"
      Picasso.get().load(item.imageUrl).into(image)
    }
  }

  interface Interaction {
    fun clickCategory(cat: Category)

  }

  private class CategoryDC : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(
      oldItem: Category,
      newItem: Category
    ): Boolean {
      return oldItem == newItem;
    }

    override fun areContentsTheSame(
      oldItem: Category,
      newItem: Category
    ): Boolean {
      return oldItem.equals(newItem);
    }
  }
}