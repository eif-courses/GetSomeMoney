package get.some.money.starter.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import get.some.money.starter.Models.Level
import get.some.money.starter.R
import get.some.money.starter.Util.Language
import kotlinx.android.synthetic.main.level_item.view.*
import kotlin.random.Random


class LevelListAdapter(private val interaction: Interaction? = null) :
  ListAdapter<Level, LevelListAdapter.LevelViewHolder>(LevelDC()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LevelViewHolder(
    LayoutInflater.from(parent.context)
      .inflate(R.layout.level_item, parent, false), interaction
  )

  override fun onBindViewHolder(holder: LevelViewHolder, position: Int) =
    holder.bind(getItem(position))

  fun swapData(data: List<Level>) {
    submitList(data.toMutableList())
  }

   lateinit var marker: List<Long>

  fun markCompletedLevels(levels: List<Long>) {
    marker = levels
  }

  inner class LevelViewHolder(
    itemView: View,
    private val interaction: Interaction?
  ) : RecyclerView.ViewHolder(itemView), OnClickListener {

    init {
      itemView.setOnClickListener(this)
    }


    override fun onClick(v: View?) {

      if (adapterPosition == RecyclerView.NO_POSITION) return

      val clicked = getItem(adapterPosition)
      interaction?.click(clicked)
    }

    fun bind(item: Level) = with(itemView) {
      // TODO: Bind the data with View
      Picasso.get().load(item.assets.get(Random.nextInt(item.assets.size))).into(level_imageViewChoose)
      val currentLocale = Language.getCurrentLanguage()


      for(level in marker){
        if (item.id.equals(level)){
         // itemView.setBackgroundColor(Color.parseColor("#cccccc"))
          itemView.checkBox.isChecked = true
        }
      }

      if(currentLocale.equals("lt")){
        level_title_chooseFragment.text = item.namelt
      }else{
        level_title_chooseFragment.text = item.name
      }
    }
  }

  interface Interaction {
    fun click(level: Level)
  }

  private class LevelDC : DiffUtil.ItemCallback<Level>() {
    override fun areItemsTheSame(
      oldItem: Level,
      newItem: Level
    ): Boolean {
      return oldItem == newItem
    }

    override fun areContentsTheSame(
      oldItem: Level,
      newItem: Level
    ): Boolean {
      return oldItem.equals(newItem)
    }
  }

}