package get.some.money.starter.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.Adapters.CategoryListAdapter
import get.some.money.starter.Functions.Language
import get.some.money.starter.Models.Category
import get.some.money.starter.R
import get.some.money.starter.ViewModels.LevelViewModel
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), CategoryListAdapter.Interaction {


  lateinit var categoryRecyclerView: RecyclerView
  lateinit var categoryListAdapter: CategoryListAdapter
  lateinit var model: LevelViewModel
  lateinit var user: UserViewModel
  private val uuid = FirebaseAuth.getInstance().currentUser?.uid

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_home, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    categoryRecyclerView = categories_recycleview
    //recycleView.layoutManager = LinearLayoutManager(context)
    categoryRecyclerView.layoutManager = GridLayoutManager(context, 2)
    categoryListAdapter = CategoryListAdapter(this)

    user = ViewModelProviders.of(this)[UserViewModel::class.java]
    model = ViewModelProviders.of(this)[LevelViewModel::class.java]
    //categoryListAdapter.submitList(model.getLevels().value)
    categoryRecyclerView.adapter = categoryListAdapter



    val currentLanguage = Language.getCurrentLanguage()


    var levelSize = 0

    if (uuid != null) {
      user.getUser(uuid).observe(this, Observer {
        levelSize = it.levels.size
      })
    }


    model.getLevels().observe(this, Observer {

      //levelSize = it.size
      val list = mutableListOf<Category>()

        for (level in it) {

          if (currentLanguage.equals("lt")) {
            list.add(Category(level.categorylt, level.header, level.category))
          }else{
            list.add(Category(level.category, level.header, level.category))
          }
        }
      categoryListAdapter.swapData(list.distinct())

      val percentage = (levelSize.toDouble()/ it.size.toDouble()) * 100
      categoryListAdapter.sendPercentageCompleted(percentage.toInt())

    })

//    if (uuid != null) {
//      user.getUser(uuid).observe(this, Observer {
//        val percentage = (it.levels.size.toDouble() / levelSize.toDouble()) * 100
//        categoryListAdapter.sendPercentageCompleted(percentage.toInt())
//      })
//
//    }

  }

  override fun clickCategory(cat: Category) {
    Toast.makeText(context, cat.title, Toast.LENGTH_LONG).show()
    val action = HomeFragmentDirections.actionHomeFragmentToLevelChooseFragment(cat.titlelt)
    findNavController().navigate(action)
  }



}
