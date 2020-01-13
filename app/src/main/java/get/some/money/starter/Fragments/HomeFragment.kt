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
import get.some.money.starter.Adapters.CategoryListAdapter
import get.some.money.starter.Models.Category
import get.some.money.starter.R
import get.some.money.starter.ViewModels.LevelViewModel
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), CategoryListAdapter.Interaction{


  lateinit var categoryRecyclerView: RecyclerView
  lateinit var categoryListAdapter: CategoryListAdapter

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
    categoryRecyclerView.layoutManager = GridLayoutManager(context,2)
    categoryListAdapter = CategoryListAdapter(this)

    val model = ViewModelProviders.of(this)[LevelViewModel::class.java]
    //categoryListAdapter.submitList(model.getLevels().value)
    categoryRecyclerView.adapter = categoryListAdapter



    model.getLevels().observe(this, Observer {

      val list = mutableListOf<Category>()
      for (level in it){
        list.add(Category(level.category, "https://wallpapercave.com/wp/wp2724675.jpg"))
        list.add(Category(level.category, "https://media.istockphoto.com/vectors/smart-home-appliances-cartoon-icons-in-set-collection-for-design-vector-id958410866"))

      }
      categoryListAdapter.swapData(list.distinct())
    })

//    model.getItems().observe(this, Observer {
//      shopListAdapter.notifyDataSetChanged()
//    })



  }
  override fun clickCategory(cat: Category) {
    Toast.makeText(context, "${cat.title}", Toast.LENGTH_LONG).show()
    val action = HomeFragmentDirections.actionHomeFragmentToLevelChooseFragment(cat.title)
    findNavController().navigate(action)
  }


}
