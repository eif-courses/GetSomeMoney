package get.some.money.starter.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.Models.User
import get.some.money.starter.R
import kotlinx.android.synthetic.main.high_scores_item.view.*

class HighScoresListAdapter(private val interaction: Interaction? = null) :
  ListAdapter<User, HighScoresListAdapter.HighScoresViewHolder>(UserDC()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HighScoresViewHolder(
    LayoutInflater.from(parent.context)
      .inflate(R.layout.high_scores_item, parent, false), interaction
  )

  override fun onBindViewHolder(holder: HighScoresViewHolder, position: Int) =
    holder.bind(getItem(position))

  fun swapData(data: List<User>) {
    submitList(data.toMutableList().sortedByDescending { it.score })
  }

  var recordSize = 0

  fun listSize(size: Int) {
    recordSize = size
  }

  inner class HighScoresViewHolder(
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


    fun bind(item: User) = with(itemView) {
      // TODO: Bind the data with View

      if(item.uuid.equals(FirebaseAuth.getInstance().currentUser?.uid)){
        itemView.setBackgroundColor(Color.GREEN)
      }

      itemView.id_text.text = String.format("%d", adapterPosition + 1)
      itemView.user_name_text.text = item.name
      itemView.user_score_text.text = String.format("%d", item.score)
      itemView.user_solved_tasks_txt.text = String.format("%d", item.levels.size)
    }
  }

  interface Interaction {

  }

  private class UserDC : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(
      oldItem: User,
      newItem: User
    ): Boolean {
      return oldItem == newItem

    }

    override fun areContentsTheSame(
      oldItem: User,
      newItem: User
    ): Boolean {
      return oldItem.equals(newItem)
    }
  }
}