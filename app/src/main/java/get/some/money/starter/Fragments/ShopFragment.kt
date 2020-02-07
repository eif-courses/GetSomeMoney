package get.some.money.starter.Fragments


import android.app.AlertDialog
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.Adapters.ShopListAdapter
import get.some.money.starter.Dialogs.RewardDialog
import get.some.money.starter.Models.Item
import get.some.money.starter.R
import get.some.money.starter.Util.Language
import get.some.money.starter.ViewModels.ShopViewModel
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_shop.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 */
class ShopFragment : Fragment(R.layout.fragment_shop), ShopListAdapter.Interaction {

  lateinit var recycleView: RecyclerView
  lateinit var shopListAdapter: ShopListAdapter
  val userViewModel: UserViewModel by viewModels()
  val shopViewModel: ShopViewModel by viewModels()
  private lateinit var rewardedAd: RewardedAd
  private lateinit var mediaPlayer: MediaPlayer
  lateinit var buyOperationSound: MediaPlayer

  private var gold = 0

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mediaPlayer = MediaPlayer.create(context, R.raw.openchest)

    buyOperationSound = MediaPlayer.create(context, R.raw.click)

    rewardedAd = RewardedAd(activity, getString(R.string.ad_mob_rewarded_video_ad_test))

    val adLoadCallback = object : RewardedAdLoadCallback() {
      override fun onRewardedAdLoaded() {
        // Ad successfully loaded.

      }

      override fun onRewardedAdFailedToLoad(errorCode: Int) {
        // Ad failed to load.
      }
    }
    rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)

    recycleView = fragment_shop_recycleview
    val orientation = resources.configuration.orientation
    if (orientation == Configuration.ORIENTATION_LANDSCAPE) { // In landscape
      recycleView.layoutManager = GridLayoutManager(context, 3) as RecyclerView.LayoutManager?

    } else {
      recycleView.layoutManager = GridLayoutManager(context, 2) as RecyclerView.LayoutManager?
    }
    shopListAdapter = ShopListAdapter(this)

//    shopViewModel = ViewModelProviders.of(this)[ShopViewModel::class.java]
//    userViewModel = ViewModelProviders.of(this)[UserViewModel::class.java]

    shopViewModel.getItems().observe(this, Observer {
      shopListAdapter.swapData(it)
    })

    recycleView.adapter = shopListAdapter


    userViewModel.getUser(FirebaseAuth.getInstance().currentUser!!.uid).observe(this, Observer {
    if(it!= null)
      gold = it.coins
    })
  }

  override fun item_clicked(clicked: Item) {
    shopping(clicked)
  }

  fun shopping(item: Item) {

    var multiplier2="Buy Coins Mutiplier x2!"
    var multiplier3="Buy Coins Mutiplier x3!"
    var itemRandom="Buy Random Item!"
    var special="Unlock Special Levels!"
    var areyousure="Are you sure to spend"
    var postfix="coins?"
    var success ="You successfully payed for service :)"

    var yes = "Yes"
    var no = "No"
    var sad = "Not enough coins :("
    var back = "Have a nice day :)"

    if(Language.getCurrentLanguage().equals("lt")){
      multiplier2="Padauginti gaunamų monetų skaičių 2x!"
      multiplier3="Padauginti gaunamų monetų skaičių 3x!"
      itemRandom="Įsigyti atsitiktinį daiktą!"
      special="Atrakinti slaptus žaidimo lygius!"
      areyousure="Ar sutinkate išleisti"
      postfix ="monetų?"
      success = "Jūs sėkmingai apmokėjote už pirkinį :)"
      yes = "Taip"
      no = "Ne"
      sad = "Dėja nepakanka lėšų :("
      back = "Gražios dienos :)"
    }


    when (item.price) {
      0 -> {
        getReward()
      }
      1000 -> {

        val mAlertDialog = buyDialog(itemRandom, "$areyousure ${item.price} $postfix", R.drawable.coins)
        mAlertDialog.setPositiveButton(yes) { dialog, id ->
          //perform some tasks here
          val temp = gold - item.price
          if(temp >=0){
            userViewModel.updateCoins(temp, FirebaseAuth.getInstance().currentUser!!.uid)

            //Toast.makeText(context, success, Toast.LENGTH_LONG).show()
            randomItem(Random.nextInt(4))
            //userViewModel.updateMultiplier(2, FirebaseAuth.getInstance().currentUser!!.uid)
            buyOperationSound.start()
          }else{
            Toast.makeText(context, sad, Toast.LENGTH_SHORT).show()
          }
        }
        mAlertDialog.setNegativeButton(no) { dialog, id ->
          Toast.makeText(context, back, Toast.LENGTH_SHORT).show()
        }
        mAlertDialog.show()


      }
      10000 -> {
        val mAlertDialog = buyDialog(multiplier2, "$areyousure ${item.price} $postfix", R.drawable.coins)
        mAlertDialog.setPositiveButton(yes) { dialog, id ->
          //perform some tasks here
          val temp = gold - item.price
          if(temp >=0){
            userViewModel.updateCoins(temp, FirebaseAuth.getInstance().currentUser!!.uid)
            Toast.makeText(context, success, Toast.LENGTH_LONG).show()
            userViewModel.updateMultiplier(2, FirebaseAuth.getInstance().currentUser!!.uid)
            buyOperationSound.start()
          }else{
            Toast.makeText(context, sad, Toast.LENGTH_SHORT).show()
          }
        }
        mAlertDialog.setNegativeButton(no) { dialog, id ->
          Toast.makeText(context, back, Toast.LENGTH_SHORT).show()
        }
        mAlertDialog.show()
      }
      20000 -> {
        val mAlertDialog = buyDialog(multiplier3, "$areyousure ${item.price} $postfix", R.drawable.coins)
        mAlertDialog.setPositiveButton(yes) { dialog, id ->
          //perform some tasks here
          val temp = gold - item.price
          if(temp >=0){
            userViewModel.updateCoins(temp, FirebaseAuth.getInstance().currentUser!!.uid)
            Toast.makeText(context, success, Toast.LENGTH_LONG).show()
            userViewModel.updateMultiplier(3, FirebaseAuth.getInstance().currentUser!!.uid)
            buyOperationSound.start()
          }else{
            Toast.makeText(context, sad, Toast.LENGTH_SHORT).show()
          }
        }
        mAlertDialog.setNegativeButton(no) { dialog, id ->
          Toast.makeText(context, back, Toast.LENGTH_SHORT).show()
        }
        mAlertDialog.show()
      }
      30000 -> {
        val mAlertDialog = buyDialog(special, "$areyousure ${item.price} $postfix", R.drawable.coins)
        mAlertDialog.setPositiveButton(yes) { dialog, id ->
          //perform some tasks here
          val temp = gold - item.price
          if(temp >=0){
            userViewModel.updateCoins(temp, FirebaseAuth.getInstance().currentUser!!.uid)
            Toast.makeText(context, success, Toast.LENGTH_LONG).show()
            userViewModel.updateSpecialLevels(1, FirebaseAuth.getInstance().currentUser!!.uid)
            buyOperationSound.start()
          }else{
            Toast.makeText(context, sad, Toast.LENGTH_SHORT).show()
          }
        }
        mAlertDialog.setNegativeButton(no) { dialog, id ->
          Toast.makeText(context, back, Toast.LENGTH_SHORT).show()
        }
        mAlertDialog.show()
      }
    }

    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }






  fun buyDialog(title:String, message: String, icon: Int): AlertDialog.Builder{
    val mAlertDialog = AlertDialog.Builder(context)
    mAlertDialog.setIcon(icon) //set alertdialog icon
    mAlertDialog.setTitle(title) //set alertdialog title
    mAlertDialog.setMessage(message) //set alertdialog message
    return mAlertDialog
  }


  fun randomItem(random: Int = 0) {
    val totalEarned = Random.nextInt(50,100)
    // Get the custom TOAST layout view.

    var you_get_message = "Successfully completed transaction!!!"
    if(Language.getCurrentLanguage().equals("lt")){
      you_get_message = "Sėkmingai atliktas mokėjimas!!!"
    }




    val fragmentManager = parentFragmentManager
    val reward = RewardDialog(true)
    reward.isCancelable = false

    reward.show(fragmentManager, "REWARD_DIALOG")
    mediaPlayer.start()

    val ft: FragmentTransaction = fragmentManager.beginTransaction()
    if (Build.VERSION.SDK_INT >= 26) {
      ft.setReorderingAllowed(false)
    }
    ft.detach(this).attach(this).commit()






    //userModel.levelComplete(uuid.toString(), args.levelid)


    //val result = your_score.text.toString().toInt() + score
    //textView9.text = score.toString()
    //userModel.updateScore(result, uuid!!)



//    when (random) {
//      0 -> {
//        val caps = resources.getStringArray(R.array.caps)
//        val cap = resources.getIdentifier(
//          caps.toList().shuffled()[0],
//          "drawable",
//          context!!.getPackageName()
//        )
//        userViewModel.addItemToInventory(
//          FirebaseAuth.getInstance().currentUser!!.uid,
//          Inventory(cap, Random.nextInt(1, 50), Random.nextInt(1, 50), "CAP")
//        )
//      }
//      1 -> {
//        val shirts = resources.getStringArray(R.array.shirts)
//        val shirt = resources.getIdentifier(
//          shirts.toList().shuffled()[0],
//          "drawable",
//          context!!.getPackageName()
//        )
//        userViewModel.addItemToInventory(
//          FirebaseAuth.getInstance().currentUser!!.uid,
//          Inventory(shirt, Random.nextInt(1, 50), Random.nextInt(1, 50), "SHIRT")
//        )
//      }
//      2 -> {
//        val jeans = resources.getStringArray(R.array.jeans)
//        val jean = resources.getIdentifier(
//          jeans.toList().shuffled()[0],
//          "drawable",
//          context!!.getPackageName()
//        )
//        userViewModel.addItemToInventory(
//          FirebaseAuth.getInstance().currentUser!!.uid,
//          Inventory(jean, Random.nextInt(1, 50), Random.nextInt(1, 50), "JEANS")
//        )
//      }
//      3 -> {
//        val glasses = resources.getStringArray(R.array.glasses)
//        val glass = resources.getIdentifier(
//          glasses.toList().shuffled()[0],
//          "drawable",
//          context!!.getPackageName()
//        )
//        userViewModel.addItemToInventory(
//          FirebaseAuth.getInstance().currentUser!!.uid,
//          Inventory(glass, Random.nextInt(1, 50), Random.nextInt(1, 50), "GLASSES")
//        )
//      }
//    }
    Toast.makeText(context, you_get_message, Toast.LENGTH_LONG).show()
  }


  fun getReward() {
    if (rewardedAd.isLoaded) {
      //val activityContext: Activity = ...
      val adCallback = object : RewardedAdCallback() {
        override fun onRewardedAdOpened() {
          // Ad opened.
        }

        override fun onRewardedAdClosed() {
          //it.setBackgroundResource(R.drawable.chestopen)

        }

        override fun onUserEarnedReward(@NonNull reward: RewardItem) {
          val toastView = getLayoutInflater().inflate(R.layout.toast_message, null);
          val toast = Toast(context)

          val totalEarned = reward.amount + Random.nextInt(10, 50)

          // Set custom view in toast.
          val rewardText = toastView.findViewById<TextView>(R.id.customToastText)
         // val rewardMesageImage = toastView.findViewById<ImageView>(R.id.customToastImage)
          rewardText.text = totalEarned.toString()
          //rewardMesageImage =
          toast.view = toastView
          toast.duration = Toast.LENGTH_LONG;
          toast.setGravity(Gravity.CENTER, 0, 0);






          val rewardMesageImage = toastView.findViewById<ImageView>(R.id.customToastImage)
          val img17 = toastView.findViewById<ImageView>(R.id.imageView17)
          val img186= toastView.findViewById<ImageView>(R.id.imageView16)
          val item_toast_coins = toastView.findViewById<TextView>(R.id.item_coins_toast)
          val item_toast_score = toastView.findViewById<TextView>(R.id.item_score_toast)
          val divider = toastView.findViewById<View>(R.id.divider8)
          val divider2 = toastView.findViewById<View>(R.id.divider9)
          val divider3 = toastView.findViewById<View>(R.id.divider7)
          rewardMesageImage.visibility = View.GONE
          item_toast_coins.visibility = View.GONE
          item_toast_score.visibility = View.GONE
          img17.visibility = View.GONE
          img186.visibility = View.GONE
          divider.visibility = View.GONE
          divider2.visibility = View.GONE
          divider3.visibility = View.GONE
          toast.show()
          mediaPlayer.start()
          userViewModel.updateCoins(
            gold + totalEarned,
            FirebaseAuth.getInstance().currentUser!!.uid
          )

        }

        override fun onRewardedAdFailedToShow(errorCode: Int) {
          // Ad failed to display.
        }
      }
      rewardedAd.show(activity, adCallback)
    } else {
      Log.d("TAG", "The rewarded ad wasn't loaded yet.")
    }

  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val action = ShopFragmentDirections.actionShopFragmentToHomeFragment()
    val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
      findNavController().navigate(action)
    }
    callback.isEnabled
  }

}
