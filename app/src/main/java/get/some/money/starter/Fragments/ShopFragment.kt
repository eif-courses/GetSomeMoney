package get.some.money.starter.Fragments


import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.Adapters.ShopListAdapter
import get.some.money.starter.Models.Inventory
import get.some.money.starter.Models.Item
import get.some.money.starter.R
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
  private var gold = 0

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mediaPlayer = MediaPlayer.create(context, R.raw.openchest)

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
      gold = it.coins
    })
  }

  override fun item_clicked(clicked: Item) {
    shopping(clicked)
  }

  fun shopping(item: Item) {
    when (item.price) {
      0 -> {
        getReward()
      }
      1000 -> {
        randomItem(Random.nextInt(4))
      }
      10000 -> {
        userViewModel.updateMultiplier(2, FirebaseAuth.getInstance().currentUser!!.uid)
      }
      20000 -> {
        userViewModel.updateMultiplier(3, FirebaseAuth.getInstance().currentUser!!.uid)
      }
      30000 -> {
        userViewModel.updateSpecialLevels(1, FirebaseAuth.getInstance().currentUser!!.uid)
      }
    }

    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


  fun randomItem(random: Int = 0) {

    when (random) {
      0 -> {
        val caps = resources.getStringArray(R.array.caps)
        val cap = resources.getIdentifier(
          caps.toList().shuffled()[0],
          "drawable",
          context!!.getPackageName()
        )
        userViewModel.addItemToInventory(
          FirebaseAuth.getInstance().currentUser!!.uid,
          Inventory(cap, Random.nextInt(1, 50), Random.nextInt(1, 50), "CAP")
        )
      }
      1 -> {
        val shirts = resources.getStringArray(R.array.shirts)
        val shirt = resources.getIdentifier(
          shirts.toList().shuffled()[0],
          "drawable",
          context!!.getPackageName()
        )
        userViewModel.addItemToInventory(
          FirebaseAuth.getInstance().currentUser!!.uid,
          Inventory(shirt, Random.nextInt(1, 50), Random.nextInt(1, 50), "SHIRT")
        )
      }
      2 -> {
        val jeans = resources.getStringArray(R.array.jeans)
        val jean = resources.getIdentifier(
          jeans.toList().shuffled()[0],
          "drawable",
          context!!.getPackageName()
        )
        userViewModel.addItemToInventory(
          FirebaseAuth.getInstance().currentUser!!.uid,
          Inventory(jean, Random.nextInt(1, 50), Random.nextInt(1, 50), "JEANS")
        )
      }
      3 -> {
        val glasses = resources.getStringArray(R.array.glasses)
        val glass = resources.getIdentifier(
          glasses.toList().shuffled()[0],
          "drawable",
          context!!.getPackageName()
        )
        userViewModel.addItemToInventory(
          FirebaseAuth.getInstance().currentUser!!.uid,
          Inventory(glass, Random.nextInt(1, 50), Random.nextInt(1, 50), "GLASSES")
        )
      }
    }
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
          val rewardMesageImage = toastView.findViewById<ImageView>(R.id.customToastImage)
          rewardText.text = totalEarned.toString()
          //rewardMesageImage =
          toast.view = toastView
          toast.duration = Toast.LENGTH_LONG;
          toast.setGravity(Gravity.CENTER, 0, 0);
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


}
