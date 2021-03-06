package get.some.money.starter.Fragments


import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.core.view.ViewCompat.animate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import get.some.money.starter.Dialogs.LooseDialog
import get.some.money.starter.Dialogs.RewardDialog
import get.some.money.starter.R
import get.some.money.starter.Util.Language
import get.some.money.starter.ViewModels.LevelViewModel
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_gameplay.*
import kotlinx.android.synthetic.main.fragment_gameplay.view.*
import kotlin.random.Random


/**
 * A simple [Fragment] subclass.
 */
class GameplayFragment : Fragment(R.layout.fragment_gameplay) {

  private val args: GameplayFragmentArgs by navArgs()
  private var isCompleted = false
  private var isLoose = false


  lateinit var mediaPlayer: MediaPlayer
  lateinit var clickSound: MediaPlayer
  lateinit var looseSound: MediaPlayer
  val levelModel: LevelViewModel by viewModels()
  val userModel: UserViewModel by viewModels()
  lateinit var time: CountDownTimer
  private val uuid = FirebaseAuth.getInstance().currentUser?.uid
  private var images = listOf<ImageView>()


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    val currentLanguage = Language.getCurrentLanguage()

    if (currentLanguage.equals("lt")) {
      levelQuestion.text = args.questionlt
    } else {
      levelQuestion.text = args.question
    }

    time = gameTimer().start()

    mediaPlayer = MediaPlayer.create(view.context, R.raw.win)
    looseSound = MediaPlayer.create(view.context, R.raw.failed)
    clickSound = MediaPlayer.create(view.context, R.raw.click)
    images = listOf(view.house, view.house2, view.house3, view.house4, view.house5)
    init(args.userscore)
  }

  fun init(score: Int) {

    Picasso.get().load(args.background).into(level_background)

    val staticImages = args.imageURL.asList().sortedBy {
      it
    }

    var i = 0
    for (img in staticImages.shuffled()) {
      Picasso.get().load(img).into(images[i])
      images[i].tag = img
      i++
    }

    var count = 0
    val location = IntArray(2)

    val sequence = mutableListOf<String>()

    var anim = imageView2.x
    for (house in images) {
      house.setOnClickListener {
        it.getLocationOnScreen(location)

        animate(house)
          .y(imageView2.y)
          .x(anim)
          .duration = 1000

        anim += house.width + 20


        count++
        // mediaPlayer.start()

        if (!clickSound.isPlaying)
          clickSound.start()

        house.isClickable = false

        sequence.add(house.tag.toString())

        if (count > 4) {

          var complete = 0
          //  1 4 5 3 2
          for (index in 0..4) {
            if (sequence[index] == staticImages[index]) {
              complete++
            }
          }
          if (complete == 5) {
            // Toast.makeText(context, "VALIO Jus laimejote!", Toast.LENGTH_LONG).show()
            val fragmentManager = parentFragmentManager
            val reward = RewardDialog()
            reward.isCancelable = false

            reward.show(fragmentManager, "REWARD_DIALOG")
            mediaPlayer.start()

            val ft: FragmentTransaction = fragmentManager.beginTransaction()
            if (Build.VERSION.SDK_INT >= 26) {
              ft.setReorderingAllowed(false)
            }
            ft.detach(this).attach(this).commit()
            isCompleted = true

            userModel.levelComplete(uuid.toString(), args.levelid)


            val result = your_score.text.toString().toInt() + score
            //textView9.text = score.toString()
            userModel.updateScore(result, uuid!!)


          } else {

            //Toast.makeText(context, "Deja jus pralaimejote!", Toast.LENGTH_LONG).show()
            isLoose = true

            val fragmentManager = parentFragmentManager
            val reward = LooseDialog()
            reward.isCancelable = false

            reward.show(fragmentManager, "LOOSE_DIALOG")
            looseSound.start()

            val ft: FragmentTransaction = fragmentManager.beginTransaction()
            if (Build.VERSION.SDK_INT >= 26) {
              ft.setReorderingAllowed(false)
            }
            ft.detach(this).attach(this).commit()
            isLoose = false
            time.cancel()


          }
        }

      }
    }
  }


  fun moveObject(v: View, x: Float, y: Float = -420f, duration: Long = 1000) {
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



  fun gameTimer(): CountDownTimer {
    val randomized = Random.nextLong(30000) + 20000

    val max = randomized / 1000
    progressBarTimer?.max = max.toInt()
    progressBarTimer?.setInterpolator(FastOutLinearInInterpolator())


    val timer = object : CountDownTimer(randomized, 1000) {
      override fun onFinish() {
        //v?.visibility = View.GONE
        //val uuid = FirebaseAuth.getInstance().currentUser?.uid
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

      }

      override fun onTick(millisUntilFinished: Long) {
        ObjectAnimator.ofInt(progressBarTimer, "progress", (millisUntilFinished / 1000).toInt())
          .setDuration(1000)
          .start();
        //time_remaining.text = (millisUntilFinished / 1000).toString()
        your_score.text = (millisUntilFinished / 125).toString()
        if (isLoose) {
          this.cancel()
        }
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }
    }
    return timer
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
      val builder = AlertDialog.Builder(context)



      var tt = "Back to home"
      var mm = "Are you sure would like back to home menu?"
      if (Language.getCurrentLanguage().equals("lt")) {
        tt = "Grįžti atgal"
        mm = "Ar tikrai norite grįžti atgal į lygių pasirinkimo langą?"
      }
      with(builder)
      {

        setTitle(tt)
        setMessage(mm)
        setPositiveButton(android.R.string.yes) { dialog, id ->
          activity?.onBackPressed()
//          val action = GameplayFragmentDirections.actionGameplayFragmentToHomeFragment()
//          findNavController().navigate(action)
        }
        setNegativeButton(android.R.string.no) { dialog, id ->
          dialog.dismiss()
        }
        show()

      }
      this.remove()


    }
    callback.isEnabled

  }

}
