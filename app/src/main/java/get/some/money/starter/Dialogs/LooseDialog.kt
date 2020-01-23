package get.some.money.starter.Dialogs

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.R
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.loose_dialog.*
import kotlin.random.Random

class LooseDialog : DialogFragment(){

  //var startTimer = false
  private lateinit var rewardedAd: RewardedAd
  lateinit var mediaPlayer: MediaPlayer
  lateinit var userViewModel: UserViewModel

  var gold = 0


  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    mediaPlayer = MediaPlayer.create(context, R.raw.openchest)
    rewardedAd = RewardedAd(activity, getString(R.string.ad_mob_rewarded_video_ad_test))
    userViewModel = ViewModelProviders.of(requireActivity())[UserViewModel::class.java]

    userViewModel.getUser(FirebaseAuth.getInstance().currentUser!!.uid).observe(viewLifecycleOwner, Observer {
      gold = it.coins
    })

    val adLoadCallback = object: RewardedAdLoadCallback() {
      override fun onRewardedAdLoaded() {
        // Ad successfully loaded.

        progressBarLooseReward.visibility = View.GONE
        restart_level_image.visibility = View.VISIBLE


      }
      override fun onRewardedAdFailedToLoad(errorCode: Int) {
        // Ad failed to load.
      }
    }
    rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)

    // Inflate the layout to use as dialog or embedded fragment
    return inflater.inflate(R.layout.loose_dialog, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    restart_level_image.setOnClickListener {
      //this.dismiss()

      if (rewardedAd.isLoaded) {
        //val activityContext: Activity = ...
        val adCallback = object: RewardedAdCallback() {
          override fun onRewardedAdOpened() {
            // Ad opened.
          }
          override fun onRewardedAdClosed() {
            it.setBackgroundResource(R.drawable.chestopen)
            it.isClickable = false
          }
          override fun onUserEarnedReward(@NonNull reward: RewardItem) {
            val toastView = getLayoutInflater().inflate(R.layout.toast_message, null);
            val toast = Toast(it.context)

            val totalEarned = reward.amount + Random.nextInt(30,70)

            // Set custom view in toast.
            val rewardText = toastView.findViewById<TextView>(R.id.customToastText)
            val rewardMesageImage = toastView.findViewById<ImageView>(R.id.customToastImage)
            rewardText.text = totalEarned.toString()
            //rewardMesageImage =
            toast.view = toastView
            toast.duration = Toast.LENGTH_LONG;
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show()
            mediaPlayer.start()
            userViewModel.updateCoins(gold + totalEarned, FirebaseAuth.getInstance().currentUser!!.uid)

          }
          override fun onRewardedAdFailedToShow(errorCode: Int) {
            // Ad failed to display.
          }
        }
        rewardedAd.show(activity, adCallback)
      }
      else {
        Log.d("TAG", "The rewarded ad wasn't loaded yet.")
      }


      //startTimer = true
    }
    level_restart.setOnClickListener {
      this.dismiss()
    }

//    next_level_btn.setOnClickListener {
//      activity?.onBackPressed()
//      this.dismiss()
//    }
//
//    level_complete_img.setOnClickListener {
//      it.background
//      it.setBackgroundResource(R.drawable.chestopen)
//      it.isClickable = false
//    }
  }




}