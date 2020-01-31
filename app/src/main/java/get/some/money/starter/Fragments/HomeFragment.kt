package get.some.money.starter.Fragments


import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.Adapters.CategoryListAdapter
import get.some.money.starter.Util.Language
import get.some.money.starter.Models.Category
import get.some.money.starter.R
import get.some.money.starter.ViewModels.LevelViewModel
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */


class CompletedLevelByCategory(val name: String, val id: Long)

class HomeFragment : Fragment(R.layout.fragment_home), CategoryListAdapter.Interaction {


  lateinit var categoryRecyclerView: RecyclerView
  lateinit var categoryListAdapter: CategoryListAdapter
  val model: LevelViewModel by viewModels()
 val user: UserViewModel by viewModels()
  private val uuid = FirebaseAuth.getInstance().currentUser?.uid

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    categoryRecyclerView = categories_recycleview
    //recycleView.layoutManager = LinearLayoutManager(context)
    val orientation = resources.configuration.orientation
    if (orientation == Configuration.ORIENTATION_LANDSCAPE) { // In landscape
      categoryRecyclerView.layoutManager = GridLayoutManager(context, 3)
    } else { // In portrait
      categoryRecyclerView.layoutManager = GridLayoutManager(context, 2)
    }

    categoryListAdapter = CategoryListAdapter(this)


    //categoryListAdapter.submitList(model.getLevels().value)
    categoryRecyclerView.adapter = categoryListAdapter


    val currentLanguage = Language.getCurrentLanguage()


    var levelSize = 0

    var completedByCategory = mutableListOf<Long>()

    val completedSeparateByCategory = mutableMapOf<String, Long>()




    if (uuid != null) {
      user.getUser(uuid).observe(this, Observer {
        if(it != null) {
          levelSize = it.levels.size
          completedByCategory = it.levels as MutableList<Long>
        }
      })
    }




    model.getLevels().observe(this, Observer {

      val categoriesMap = mutableMapOf<String, Int>()

      val finalMapOfCompletedLevels = mutableListOf<CompletedLevelByCategory>()


      if (currentLanguage.equals("lt")) {
        for (down in it) {
          for (temp in completedByCategory) {
            if (temp.equals(down.id)) {
              finalMapOfCompletedLevels.add(CompletedLevelByCategory(down.categorylt, temp))
            }
          }
        }
      }else{
        for (down in it) {
          for (temp in completedByCategory) {
            if (temp.equals(down.id)) {
              finalMapOfCompletedLevels.add(CompletedLevelByCategory(down.category, temp))
            }
          }
        }
      }

      finalMapOfCompletedLevels.groupingBy { it.name }.eachCount().map {
        println("RIKIUOJAM CUSTOM GAL: " + it.key + "    " + it.value)
        completedSeparateByCategory.put(it.key, it.value.toLong())

      }







      categoriesMap.clear()

      if (currentLanguage.equals("lt")) {
        it.groupingBy { it.categorylt }.eachCount().map {
          //  println(it.key + it.value)
          categoriesMap.put(it.key, it.value)
        }

      } else {
        it.groupingBy { it.category }.eachCount().map {
          //  println(it.key + it.value)
          categoriesMap.put(it.key, it.value)
        }
      }

      //levelSize = it.size
      val list = mutableListOf<Category>()

      for (level in it) {

        if (currentLanguage.equals("lt")) {
          list.add(Category(level.categorylt, level.header, level.category))
        } else {
          list.add(Category(level.category, level.header, level.category))
        }
      }
      categoryListAdapter.swapData(list.distinct())


      categoryListAdapter.sendPercentageCompleted(
        categoriesMap,
        levelSize,
        completedSeparateByCategory
      )

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
    //Toast.makeText(context, cat.title, Toast.LENGTH_LONG).show()
    val action = HomeFragmentDirections.actionHomeFragmentToLevelChooseFragment(cat.titlelt)
    findNavController().navigate(action)
  }


}
