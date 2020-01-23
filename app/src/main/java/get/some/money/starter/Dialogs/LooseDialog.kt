package get.some.money.starter.Dialogs

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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import get.some.money.starter.R
import kotlinx.android.synthetic.main.loose_dialog.*
import kotlin.random.Random

class LooseDialog : DialogFragment(){

  //var startTimer = false
  private lateinit var rewardedAd: RewardedAd
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    rewardedAd = RewardedAd(activity, getString(R.string.ad_mob_rewarded_video_ad_test))
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
            // Set custom view in toast.
            val rewardText = toastView.findViewById<TextView>(R.id.customToastText)
            val rewardMesageImage = toastView.findViewById<ImageView>(R.id.customToastImage)
            rewardText.text = Random.nextInt(500).toString()
            //rewardMesageImage =
            toast.view = toastView
            toast.duration = Toast.LENGTH_LONG;
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show()
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