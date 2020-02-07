package get.some.money.starter.Fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.Adapters.LevelListAdapter
import get.some.money.starter.Models.Level
import get.some.money.starter.R
import get.some.money.starter.ViewModels.LevelViewModel
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_level_choose.*

/**
 * A simple [Fragment] subclass.
 */
class LevelChooseFragment : Fragment(R.layout.fragment_level_choose), LevelListAdapter.Interaction{

  val args: LevelChooseFragmentArgs by navArgs()
  lateinit var levelRecycleView: RecyclerView
  lateinit var levelListAdapter: LevelListAdapter
  val levelViewModel: LevelViewModel by viewModels()
  val userViewModel: UserViewModel by viewModels()
  val user = FirebaseAuth.getInstance().currentUser
  var score = 0
  var coins = 0
//  override fun onCreateView(
//    inflater: LayoutInflater, container: ViewGroup?,
//    savedInstanceState: Bundle?
//  ): View? {
//    // Inflate the layout for this fragment
//    return inflater.inflate(R.layout.fragment_level_choose, container, false)
//  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
   // level_textView.text = args.categoryname

    levelRecycleView = level_recycleview
    levelRecycleView.layoutManager = LinearLayoutManager(context)
    //levelRecycleView.layoutManager = GridLayoutManager(context,2)
    levelListAdapter = LevelListAdapter(this)

    if(user != null) {
      userViewModel.getUser(user.uid).observe(this, Observer {
       if(it !=null) {
         levelListAdapter.markCompletedLevels(it.levels)
         score = it.score
         coins = it.coins
       }
      })
    }

    levelViewModel.getLevels(args.categoryname).observe(this, Observer {

      val list = mutableListOf<Level>()
      for (item in it){
        list.add(item)
      }
      levelListAdapter.swapData(list)

    })


    //levelListAdapter.submitList(model.getLevels().value)
    levelRecycleView.adapter = levelListAdapter

  }

  override fun click(level: Level) {
    val temp: Array<String>
    temp = level.assets.toTypedArray()
    val action = LevelChooseFragmentDirections.actionLevelChooseFragmentToGameplayFragment(temp, level.name, level.question, level.namelt, level.questionlt, level.header, level.id, score, coins)
    findNavController().navigate(action)
  }


}
