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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.R
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.reward_dialog.*
import kotlin.random.Random


class RewardDialog : DialogFragment(){

  lateinit var mediaPlayer: MediaPlayer
  lateinit var userViewModel: UserViewModel
  private var gold = 0
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
    userViewModel = ViewModelProviders.of(this)[UserViewModel::class.java]

    userViewModel.getUser(FirebaseAuth.getInstance().currentUser!!.uid).observe(this, Observer {
      gold = it.coins
    })
    next_level_btn.isVisible = false

    next_level_btn.setOnClickListener {
      activity?.onBackPressed()
      this.dismiss()
    }

    level_complete_img.setOnClickListener {
      it.background
      it.setBackgroundResource(R.drawable.chestopen)
      it.isClickable = false
      next_level_btn.isVisible = true
      mediaPlayer.start()
      val totalEarned = Random.nextInt(50,100)
      // Get the custom TOAST layout view.
      val toastView = getLayoutInflater().inflate(R.layout.toast_message, null);
      val toast = Toast(it.context)
      // Set custom view in toast.
      val rewardText = toastView.findViewById<TextView>(R.id.customToastText)
      val rewardMesageImage = toastView.findViewById<ImageView>(R.id.customToastImage)
        rewardText.text = totalEarned.toString()
        //rewardMesageImage =
      toast.view = toastView
      toast.duration = Toast.LENGTH_LONG;
      toast.setGravity(Gravity.CENTER, 0,0);
      toast.show()


      userViewModel.updateCoins(gold + totalEarned, FirebaseAuth.getInstance().currentUser!!.uid)
    }




  }


}