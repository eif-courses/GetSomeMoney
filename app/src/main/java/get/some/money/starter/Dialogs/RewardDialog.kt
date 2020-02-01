package get.some.money.starter.Dialogs

import android.media.MediaPlayer
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.Models.Inventory
import get.some.money.starter.R
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.reward_dialog.*
import kotlin.random.Random


class RewardDialog(val changeUIForOtherRewards: Boolean=false) : DialogFragment(){

  lateinit var mediaPlayer: MediaPlayer
  val userViewModel: UserViewModel by viewModels()
  private var gold = 0
  var knowledge: Int = 0
  var coins: Int = 0
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // Inflate the layout to use as dialog or embedded fragment
    return inflater.inflate(R.layout.reward_dialog, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    mediaPlayer = MediaPlayer.create(view.context, R.raw.openchest)

    userViewModel.getUser(FirebaseAuth.getInstance().currentUser!!.uid).observe(this, Observer {
      gold = it.coins
    })
    next_level_btn.isVisible = false

    if(changeUIForOtherRewards){
      level_name_rewardFragment.visibility = View.GONE
      next_level_btn.text = resources.getString(R.string.back)
      next_level_btn.setOnClickListener {
        this.dismiss()
      }
    }else{
      next_level_btn.setOnClickListener {
        activity?.onBackPressed()
        this.dismiss()
      }

    }




    userViewModel.getEquipedItems(FirebaseAuth.getInstance().currentUser!!.uid)
      .observe(this, Observer {

        coins = 0
        knowledge = 0
        it.forEach { item ->
          coins += item.knowledge
          knowledge += item.extraCoins
        }
      })




    level_complete_img.setOnClickListener {

        it.background
        it.setBackgroundResource(R.drawable.chestopen)
        it.isClickable = false

        next_level_btn.isVisible = true
        mediaPlayer.start()
        val totalEarned = Random.nextInt(50, 100)
        // Get the custom TOAST layout view.
        val toastView = getLayoutInflater().inflate(R.layout.toast_message, null);
        val toast = Toast(it.context)
        // Set custom view in toast.
        val rewardText = toastView.findViewById<TextView>(R.id.customToastText)
        val rewardMesageImage = toastView.findViewById<ImageView>(R.id.customToastImage)
        val goldImageIcon = toastView.findViewById<ImageView>(R.id.imageView13)

        val item_toast_coins = toastView.findViewById<TextView>(R.id.item_coins_toast)
        val item_toast_score = toastView.findViewById<TextView>(R.id.item_score_toast)

        when (Random.nextInt(4)) {
          0 -> {
            val caps = resources.getStringArray(R.array.caps)
            val cap = resources.getIdentifier(
              caps.toList().shuffled()[0],
              "drawable",
              context!!.getPackageName()
            )

            rewardMesageImage.setImageResource(cap)
            val rcoins = Random.nextInt(1, 50)
            val rscore = Random.nextInt(1, 50)
            item_toast_coins.text = rcoins.toString()
            item_toast_score.text = rscore.toString()

            userViewModel.addItemToInventory(
              FirebaseAuth.getInstance().currentUser!!.uid,
              Inventory(cap, rcoins, rscore, "CAP")
            )
          }
          1 -> {
            val shirts = resources.getStringArray(R.array.shirts)
            val shirt = resources.getIdentifier(
              shirts.toList().shuffled()[0],
              "drawable",
              context!!.getPackageName()
            )
            rewardMesageImage.setImageResource(shirt)
            val rcoins = Random.nextInt(1, 50)
            val rscore = Random.nextInt(1, 50)
            item_toast_coins.text = rcoins.toString()
            item_toast_score.text = rscore.toString()

            userViewModel.addItemToInventory(
              FirebaseAuth.getInstance().currentUser!!.uid,
              Inventory(shirt, rcoins, rscore, "SHIRT")
            )
          }
          2 -> {
            val jeans = resources.getStringArray(R.array.jeans)
            val jean = resources.getIdentifier(
              jeans.toList().shuffled()[0],
              "drawable",
              context!!.getPackageName()
            )
            rewardMesageImage.setImageResource(jean)
            val rcoins = Random.nextInt(1, 50)
            val rscore = Random.nextInt(1, 50)
            item_toast_coins.text = rcoins.toString()
            item_toast_score.text = rscore.toString()

            userViewModel.addItemToInventory(
              FirebaseAuth.getInstance().currentUser!!.uid,
              Inventory(jean, rcoins, rscore, "JEANS")
            )
          }
          3 -> {
            val glasses = resources.getStringArray(R.array.glasses)
            val glass = resources.getIdentifier(
              glasses.toList().shuffled()[0],
              "drawable",
              context!!.getPackageName()
            )
            rewardMesageImage.setImageResource(glass)
            val rcoins = Random.nextInt(1, 50)
            val rscore = Random.nextInt(1, 50)
            item_toast_coins.text = rcoins.toString()
            item_toast_score.text = rscore.toString()

            userViewModel.addItemToInventory(
              FirebaseAuth.getInstance().currentUser!!.uid,
              Inventory(glass, rcoins, rscore, "GLASSES")
            )
          }
        }

        rewardText.text = String.format("%d", totalEarned + knowledge)
        //rewardMesageImage =
        toast.view = toastView
        toast.duration = Toast.LENGTH_LONG;
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show()



        if (changeUIForOtherRewards) {
          rewardText.visibility = View.GONE
          goldImageIcon.visibility = View.GONE
        } else {
          userViewModel.updateCoins(
            gold + totalEarned + knowledge,
            FirebaseAuth.getInstance().currentUser!!.uid
          )
        }
    }
    
  }

}