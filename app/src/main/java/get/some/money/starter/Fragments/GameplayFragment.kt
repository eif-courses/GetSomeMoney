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
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
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

    levelModel = ViewModelProviders.of(this)[LevelViewModel::class.java]
    userModel = ViewModelProviders.of(this)[UserViewModel::class.java]
    mediaPlayer = MediaPlayer.create(view.context, R.raw.object_anim)
    images = listOf(view.house, view.house2, view.house3, view.house4, view.house5)
    gameBoard.setBackgroundResource(R.drawable.forest)

    val staticImages = listOf(
      "https://previews.123rf.com/images/ylivdesign/ylivdesign1801/ylivdesign180103155/93837186-electric-outlet-icon-cartoon-illustration-of-electric-outlet-vector-icon-for-web-.jpg",
      "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRbvFMFTqeccYcmZtq9sFGL75VXx4aexMM0rytzx8ZFNR-iOujS&s",
      "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTyLMIRmp43eRF0qcX4rFOXjw8__Ktcr63fcA1BRun60I8W_xsE&s",
      "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9U-eltGPl6h-sDHLIULom_zvHaWHAesM8y0rUy1YlBIiVqsOXMw&s",
      "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3YObYJXEzl1sXgSVbTkJYbsvhRHcxY8aniT3NQKbf7EfrY_rM&s"
    )
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
