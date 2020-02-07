package get.some.money.starter.Fragments


import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
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
import get.some.money.starter.Util.Language
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_inventory.*
import kotlin.random.Random


/**
 * A simple [Fragment] subclass.
 */
class InventoryFragment : Fragment(R.layout.fragment_inventory), InventoryListAdapter.Interaction, InventoryListAdapter.LongClickInteraction {

  lateinit var recycleView: RecyclerView
  lateinit var inventoryListAdapter: InventoryListAdapter
  lateinit var mediaPlayer: MediaPlayer
  val userViewModel: UserViewModel by viewModels()

  var levels = emptyArray<Inventory>()
  var currentCoins = 0

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    mediaPlayer = MediaPlayer.create(context, R.raw.openchest)
    recycleView = inventory_recycleView
    recycleView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
    inventoryListAdapter = InventoryListAdapter(this, this)

    userViewModel.getUser(FirebaseAuth.getInstance().currentUser!!.uid).observe(viewLifecycleOwner, Observer {
      levels = it.items.toTypedArray()
      currentCoins = it.coins
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

    if(Language.getCurrentLanguage().equals("lt")){
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
    val cancel = dialogView.findViewById<Button>(R.id.cancel_sell_items_button)
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

      if(!Language.getCurrentLanguage().equals("lt")) {
        Toast.makeText(context, "The item was solded for $generatedCoins coins!", Toast.LENGTH_LONG).show()
      }else{
        Toast.makeText(context, "Jūsų daiktas buvo parduotas už $generatedCoins monetas!", Toast.LENGTH_LONG).show()
      }

    var index = 0
      for (i in levels){

        if(inventory.equals(i))
        {
          //println("inventory: ${inventory.imageID}, levelLIST: ${i.imageID} : $index")
          userViewModel.sellItemFromUser(FirebaseAuth.getInstance().currentUser!!.uid, index, inventory)
          userViewModel.updateCoins(currentCoins+generatedCoins, FirebaseAuth.getInstance().currentUser!!.uid)
          mediaPlayer.start()
        }
        index++

      }
      alertDialog.dismiss()
    }
    cancel.setOnClickListener {
      alertDialog.dismiss()
    }

    alertDialog.show()



  }


}

