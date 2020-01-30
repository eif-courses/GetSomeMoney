package get.some.money.starter.Fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import get.some.money.starter.Adapters.HighScoresListAdapter
import get.some.money.starter.R
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_high_scores.*

/**
 * A simple [Fragment] subclass.
 */
class HighScoresFragment : Fragment(R.layout.fragment_high_scores) {
  lateinit var recycleView: RecyclerView
  lateinit var highScoresListAdapter: HighScoresListAdapter
  val userViewModel: UserViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    recycleView = high_scores_recycleView
    recycleView.layoutManager = LinearLayoutManager(context)
    highScoresListAdapter = HighScoresListAdapter()

    userViewModel.getUsers().observe(this, Observer {
      highScoresListAdapter.swapData(it)
      highScoresListAdapter.listSize(it.size)
    })
    recycleView.adapter = highScoresListAdapter


  }


}
