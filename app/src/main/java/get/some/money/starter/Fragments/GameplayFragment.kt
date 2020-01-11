package get.some.money.starter.Fragments


import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.R
import get.some.money.starter.ViewModels.GameViewModel
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_gameplay.*
import kotlinx.android.synthetic.main.fragment_gameplay.view.*

/**
 * A simple [Fragment] subclass.
 */
class GameplayFragment : Fragment() {

  lateinit var mediaPlayer: MediaPlayer
  lateinit var model: GameViewModel
  lateinit var userModel: UserViewModel
  private val uuid = FirebaseAuth.getInstance().currentUser?.uid


  var images = listOf<ImageView>()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_gameplay, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    model = ViewModelProviders.of(this)[GameViewModel::class.java]
    userModel = ViewModelProviders.of(this)[UserViewModel::class.java]
    mediaPlayer = MediaPlayer.create(view.context, R.raw.object_anim)
    images = listOf(view.house, view.house2, view.house3, view.house4, view.house5)
    gameBoard.setBackgroundResource(R.drawable.forest)

    var count = 0
    val location = IntArray(2)

    for (house in images) {
      house.setOnClickListener {
        it.getLocationOnScreen(location)
        moveObject(it, -location[0].toFloat() + 50 + (count * 190))
        count++
        mediaPlayer.start()
        house.isClickable = false
      }


    }
  }


  fun moveObject(v: View, x: Float, y: Float = -600f, duration: Long = 1000) {
    v.animate()
      .translationY(y)
      .translationX(x)
      .duration = duration
  }


  private fun handleTouch(image: ImageView) {
    mediaPlayer.start()
    collectItemTimer(image).start()

  }


  private fun collectItemTimer(v: View?): CountDownTimer {
    val timer = object : CountDownTimer(100, 100) {
      override fun onFinish() {
        v?.visibility = View.GONE
        //val uuid = FirebaseAuth.getInstance().currentUser?.uid
        userModel.updateScore(your_score.text.toString().toInt() + 500, uuid!!)
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }

      override fun onTick(millisUntilFinished: Long) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }
    }
    return timer
  }


}
