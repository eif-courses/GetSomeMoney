package get.some.money.starter.Fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.Adapters.InventoryListAdapter
import get.some.money.starter.Models.Inventory
import get.some.money.starter.R
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_inventory.*

/**
 * A simple [Fragment] subclass.
 */
class InventoryFragment : Fragment(R.layout.fragment_inventory), InventoryListAdapter.Interaction {

  lateinit var recycleView: RecyclerView
  lateinit var inventoryListAdapter: InventoryListAdapter
  lateinit var userViewModel: UserViewModel

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    recycleView = inventory_recycleView
    recycleView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
    inventoryListAdapter = InventoryListAdapter(this)
    userViewModel = ViewModelProviders.of(this)[UserViewModel::class.java]

    userViewModel.getUser(FirebaseAuth.getInstance().currentUser!!.uid).observe(viewLifecycleOwner, Observer {
      inventoryListAdapter.swapData(it.items.sortedWith(compareBy({ it.extraCoins }, { it.knowledge })).sortedByDescending {
        it.extraCoins
      })
    })
    recycleView.adapter = inventoryListAdapter
  }
  override fun click_item(inventory: Inventory) {
    userViewModel.equipItems(FirebaseAuth.getInstance().currentUser!!.uid, inventory)
  }
}

