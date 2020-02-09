package get.some.money.starter.Dialogs

import android.app.Dialog
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
import androidx.fragment.app.viewModels
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import get.some.money.starter.Models.User
import get.some.money.starter.R
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.loose_dialog.*
import kotlin.random.Random

class LooseDialog : DialogFragment(){
  companion object {
    fun newInstance() = LooseDialog()
  }
  //var startTimer = false
  private lateinit var rewardedAd: RewardedAd
  lateinit var mediaPlayer: MediaPlayer
  val userViewModel: UserViewModel by viewModels()
  lateinit var db: FirebaseFirestore

  var gold = 0


  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {


    // Inflate the layout to use as dialog or embedded fragment
    return inflater.inflate(R.layout.loose_dialog, container, false)
  }


  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return super.onCreateDialog(savedInstanceState)

  }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    mediaPlayer = MediaPlayer.create(context, R.raw.openchest)
    rewardedAd = RewardedAd(activity, "ca-app-pub-9087133931125731/7265997106")


    //val coins = view.findViewById<TextView>(R.id.coins_profile)



    //userViewModel.getUser(FirebaseAuth.getInstance().currentUser!!.uid).observe(this, Observer {
     // gold = coins.toString().toInt()
    ///})




    val adLoadCallback = object: RewardedAdLoadCallback() {
      override fun onRewardedAdLoaded() {
        // Ad successfully loaded.
        db = FirebaseFirestore.getInstance()
        val ref = db.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid)

        // val ref = db.collection("cities").document("BJ")
        ref.get().addOnSuccessListener { documentSnapshot ->
          val user = documentSnapshot.toObject(User::class.java)
          println("USERISSSSSSSSSSSSSSSSSSSSS: $user")
          gold = user!!.coins
        }
        progressBarLooseReward.visibility = View.GONE
        restart_level_image.visibility = View.VISIBLE


      }
      override fun onRewardedAdFailedToLoad(errorCode: Int) {
        // Ad failed to load.
      }
    }
    rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)



    restart_level_image.setOnClickListener {
      //this.dismiss()

      if (rewardedAd.isLoaded) {
        //val activityContext: Activity = ...
        val adCallback = object: RewardedAdCallback() {
          override fun onRewardedAdOpened() {
            // Ad opened.
          }
          override fun onRewardedAdClosed() {
            it.setBackgroundResource(R.drawable.chestcopen)
            it.isClickable = false
            //it.visibility = View.GONE
            textView.visibility= View.GONE
              textView5.visibility = View.GONE
          }
          override fun onUserEarnedReward(@NonNull reward: RewardItem) {
            val toastView = getLayoutInflater().inflate(R.layout.toast_message, null);
            val toast = Toast(it.context)

            val totalEarned = reward.amount + Random.nextInt(30,70)

            // Set custom view in toast.
            val rewardText = toastView.findViewById<TextView>(R.id.customToastText)
           // val rewardMesageImage = toastView.findViewById<ImageView>(R.id.customToastImage)
            rewardText.text = totalEarned.toString()
            //rewardMesageImage =
//

           // toastView.findViewById<TextView>(R.id.imageView16).visibility = View.GONE
            //toastView.findViewById<TextView>(R.id.imageView17).visibility = View.GONE

            toast.view = toastView

//            toast.view.findViewById<TextView>(R.id.item_coins_toast).visibility = View.GONE
//            toast.view.findViewById<TextView>(R.id.item_score_toast).visibility = View.GONE
//            toast.view.findViewById<ImageView>(R.id.customToastImage).visibility = View.GONE


            toast.duration = Toast.LENGTH_LONG;
            toast.setGravity(Gravity.CENTER, 0,0)


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
            userViewModel.updateCoins(gold + totalEarned, FirebaseAuth.getInstance().currentUser!!.uid)

//            val timer = object : CountDownTimer(3000, 1000){
//              override fun onFinish() {
//                level_restart.performClick()
//              }
//              override fun onTick(millisUntilFinished: Long) {
//              }
//            }
//            timer.start()


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