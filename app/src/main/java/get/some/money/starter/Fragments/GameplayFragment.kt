package get.some.money.starter.Fragments


import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import get.some.money.starter.Dialogs.RewardDialog
import get.some.money.starter.Models.Level
import get.some.money.starter.R
import get.some.money.starter.ViewModels.LevelViewModel
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_gameplay.*
import kotlinx.android.synthetic.main.fragment_gameplay.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class GameplayFragment : Fragment() {

  val args: GameplayFragmentArgs by navArgs()

  lateinit var mediaPlayer: MediaPlayer
  lateinit var levelModel: LevelViewModel
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


    for(str in args.imageURL){
      println("$str")
    }
    levelQuestion.text = args.question



    levelModel = ViewModelProviders.of(this)[LevelViewModel::class.java]
    userModel = ViewModelProviders.of(this)[UserViewModel::class.java]
    mediaPlayer = MediaPlayer.create(view.context, R.raw.object_anim)
    images = listOf(view.house, view.house2, view.house3, view.house4, view.house5)
    gameBoard.setBackgroundResource(R.drawable.forest)

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
        mediaPlayer.start()

        //println("${house.getTag()} ir ${chain.listImages[0]}")

        house.isClickable = false

        sequence.add(house.tag.toString())

        println("------------------------------------------")
        println("$sequence")
        println("------------------------------------------")
        println("$staticImages")
        println("------------------------------------------")

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
            levelModel.save(Level(1, "Gamta", staticImages, "Auto","Kas per masina?", Timestamp(Date())))

          }else{

            val fragmentManager = getFragmentManager()
            val reward = RewardDialog()
            reward.showsDialog = true
            //args.name

            Toast.makeText(context, "Deja jus pralaimejote!", Toast.LENGTH_LONG).show()
            levelModel.getLevels().observe(this, androidx.lifecycle.Observer {

              for(level in it){
                println(level.name)
              }


            })
          }
        }

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
