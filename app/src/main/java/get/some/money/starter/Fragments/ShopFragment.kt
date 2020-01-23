package get.some.money.starter.Fragments


import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import get.some.money.starter.Adapters.ShopListAdapter
import get.some.money.starter.R
import get.some.money.starter.ViewModels.ShopViewModel
import kotlinx.android.synthetic.main.fragment_shop.*

/**
 * A simple [Fragment] subclass.
 */
class ShopFragment : Fragment(R.layout.fragment_shop) {

  lateinit var recycleView: RecyclerView
  lateinit var shopListAdapter: ShopListAdapter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    recycleView = fragment_shop_recycleview

    val orientation = resources.configuration.orientation
    if (orientation == Configuration.ORIENTATION_LANDSCAPE) { // In landscape
      recycleView.layoutManager = GridLayoutManager(context, 3) as RecyclerView.LayoutManager?

    } else {
      recycleView.layoutManager = GridLayoutManager(context, 2) as RecyclerView.LayoutManager?
    }
    shopListAdapter = ShopListAdapter()

    val model = ViewModelProviders.of(this)[ShopViewModel::class.java]
    shopListAdapter.submitList(model.getItems().value)
    recycleView.adapter = shopListAdapter

    model.getItems().observe(this, Observer {
      shopListAdapter.notifyDataSetChanged()
    })
  }
}
