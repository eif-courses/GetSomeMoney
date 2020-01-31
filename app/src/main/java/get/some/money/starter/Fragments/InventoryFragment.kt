package get.some.money.starter.Fragments


import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.Adapters.InventoryListAdapter
import get.some.money.starter.Models.Inventory
import get.some.money.starter.R
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_inventory.*
import kotlin.random.Random


/**
 * A simple [Fragment] subclass.
 */
class InventoryFragment : Fragment(R.layout.fragment_inventory), InventoryListAdapter.Interaction, InventoryListAdapter.LongClickInteraction {

  lateinit var recycleView: RecyclerView
  lateinit var inventoryListAdapter: InventoryListAdapter
  val userViewModel: UserViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    recycleView = inventory_recycleView
    recycleView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
    inventoryListAdapter = InventoryListAdapter(this, this)

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

  override fun long_click(inventory: Inventory) {

    var areyousure = "Would you like to sell item for?"
    var titleDialog ="Sell item!"
    var positiveBtn ="Sell"

    if(get.some.money.starter.Util.Language.getCurrentLanguage().equals("lt")){
      areyousure ="Ar tikrai norite parduoti daiktą už?"
      titleDialog = "Parduoti daiktą!"
      positiveBtn = "Parduoti"
    }


    val dialogBuilder = AlertDialog.Builder(context)
    val inflater = this.layoutInflater
    val dialogView: View = inflater.inflate(R.layout.custom_alert_dialog_view, null)
    dialogBuilder.setView(dialogView)
    val alertDialog: AlertDialog = dialogBuilder.create()

      alertDialog.setTitle(titleDialog)
      alertDialog.setMessage(areyousure)
      alertDialog.setCancelable(false)

    val sell = dialogView.findViewById<TextView>(R.id.sell_item_get_gold)
    val sellItemImage = dialogView.findViewById<ImageView>(R.id.sell_imageview_dialog)
    val coinsStatsItem = dialogView.findViewById<TextView>(R.id.coins_sell_dialog)
    val coinsScoreItem = dialogView.findViewById<TextView>(R.id.score_sell_dialog)
    val sellItemsButton = dialogView.findViewById<TextView>(R.id.sell_items_button)
    val generatedCoins = Random.nextInt(10, 50)

    sellItemImage?.setBackgroundResource(inventory.imageID)
    coinsScoreItem?.text = inventory.knowledge.toString()
    coinsStatsItem?.text = inventory.extraCoins.toString()
    sell?.text = generatedCoins.toString()
    sellItemsButton.text = positiveBtn
    sellItemsButton.setOnClickListener {
      Toast.makeText(context, "Item was removed", Toast.LENGTH_LONG).show()
      alertDialog.dismiss()
    }

    alertDialog.show()



  }
}

