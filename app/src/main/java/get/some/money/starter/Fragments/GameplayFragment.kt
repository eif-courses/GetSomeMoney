package get.some.money.starter.Fragments


import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import get.some.money.starter.Dialogs.LooseDialog
import get.some.money.starter.Dialogs.RewardDialog
import get.some.money.starter.Functions.Language
import get.some.money.starter.R
import get.some.money.starter.ViewModels.LevelViewModel
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_gameplay.*
import kotlinx.android.synthetic.main.fragment_gameplay.view.*
import kotlin.random.Random


/**
 * A simple [Fragment] subclass.
 */
class GameplayFragment : Fragment() {

  val args: GameplayFragmentArgs by navArgs()
  var isCompleted = false
  var isLoose = false
  lateinit var mediaPlayer: MediaPlayer
  lateinit var clickSound: MediaPlayer
  lateinit var levelModel: LevelViewModel
  lateinit var userModel: UserViewModel
  lateinit var time: CountDownTimer
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

    val currentLanguage = Language.getCurrentLanguage()

    if(currentLanguage.equals("lt")){
      levelQuestion.text = args.questionlt
    }else{
      levelQuestion.text = args.question
    }

    time = gameTimer().start()

    levelModel = ViewModelProviders.of(this)[LevelViewModel::class.java]
    userModel = ViewModelProviders.of(this)[UserViewModel::class.java]
    mediaPlayer = MediaPlayer.create(view.context, R.raw.win)
    clickSound = MediaPlayer.create(view.context, R.raw.click)
    images = listOf(view.house, view.house2, view.house3, view.house4, view.house5)

    Picasso.get().load(args.background).into(level_background)

    //gameBoard.setBackgroundResource(R.drawable.forest)

    val staticImages = args.imageURL.asList()

    var i = 0
    for (img in staticImages.shuffled()) {
      Picasso.get().load(img).into(images[i])
      images[i].tag = img
      i++
    }

    var count = 0
    val location = IntArray(2)

    val sequence = mutableListOf<String>()

    for (house in images) {
      house.setOnClickListener {
        it.getLocationOnScreen(location)
        moveObject(it, -location[0].toFloat() + 50 + (count * 190))
        count++
       // mediaPlayer.start()

        if(!clickSound.isPlaying)
          clickSound.start()

        house.isClickable = false

        sequence.add(house.tag.toString())

        if(count > 4){

          var complete = 0
            //  1 4 5 3 2
          for (index in 0..4){
            if(sequence[index] == staticImages[index]){
              complete++
            }
          }
          if(complete == 5){
            Toast.makeText(context, "VALIO Jus laimejote!", Toast.LENGTH_LONG).show()
            val fragmentManager = getFragmentManager()
            val reward = RewardDialog()
            reward.isCancelable = false
            if (fragmentManager != null) {
              reward.show(fragmentManager, "REWARD_DIALOG")
              mediaPlayer.start()

              val ft: FragmentTransaction = fragmentManager.beginTransaction()
              if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false)
              }
              ft.detach(this).attach(this).commit()
              isCompleted = true
            }


          }else{

            Toast.makeText(context, "Deja jus pralaimejote!", Toast.LENGTH_LONG).show()
            isLoose = true

            val fragmentManager = getFragmentManager()
            val reward = LooseDialog()
            reward.isCancelable = false
            if (fragmentManager != null) {
              reward.show(fragmentManager, "LOOSE_DIALOG")
              mediaPlayer.start()

              val ft: FragmentTransaction = fragmentManager.beginTransaction()
              if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false)
              }
              ft.detach(this).attach(this).commit()
              isLoose = false
              time.start()
              }

          }
        }

      }
    }
  }

  fun moveObject(v: View, x: Float, y: Float = -500f, duration: Long = 1000) {
    v.animate()
      .translationY(y)
      .translationX(x)
      .duration = duration
  }
  override fun onPause() {
    super.onPause()

    time.cancel()

  }

  override fun onResume() {
    super.onResume()
    time.start()
  }

  override fun onStop() {
    super.onStop()
    time.cancel()
  }

  override fun onDestroy() {
    super.onDestroy()
    time.cancel()
  }



  fun gameTimer(): CountDownTimer {
    val timer = object : CountDownTimer(Random.nextLong(30000) + 20000, 1000) {
      override fun onFinish() {
        //v?.visibility = View.GONE
        //val uuid = FirebaseAuth.getInstance().currentUser?.uid
        userModel.updateScore(your_score.text.toString().toInt() + 500, uuid!!)
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }

      override fun onTick(millisUntilFinished: Long) {
        time_remaining.text = (millisUntilFinished/1000).toString()
        your_score.text = (millisUntilFinished / 125).toString()
        if(isCompleted || isLoose){
          this.cancel()
        }

        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }
    }
    return timer
  }


}
