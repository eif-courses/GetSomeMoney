package get.some.money.starter.Fragments


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat

import get.some.money.starter.R
import kotlinx.android.synthetic.main.fragment_gameplay.*
import kotlinx.android.synthetic.main.fragment_gameplay.view.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 */
class GameplayFragment : Fragment() {

    lateinit var mediaPlayer: MediaPlayer

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

        mediaPlayer = MediaPlayer.create(view.context, R.raw.collect)
        images = listOf(view.house, view.house2, view.house3, view.house3, view.house4, view.house5, view.house6, view.house7, view.house8, view.house9)
        gameBoard.setBackgroundResource(R.drawable.level1)

        var stars = listOf(R.drawable.star_01,
            R.drawable.star_02,
            R.drawable.star_03,
            R.drawable.star_04,
            R.drawable.star_05,
            R.drawable.star_06)

        var i = 3

        object : CountDownTimer(3000, 1000){
            override fun onFinish() {
                time_remaining.text = "GO"
                for (house in images){
                    house.visibility = View.VISIBLE
                    house.setImageDrawable(ResourcesCompat.getDrawable(resources, stars.get(Random.nextInt(stars.size)), null))
                    //house.setBackgroundResource()
                }
            }
            override fun onTick(millisUntilFinished: Long) {
                time_remaining.text = "${i--}"
            }
        }.start()



        for (house in images){
            house.setOnClickListener { v -> handleTouch(house) }
        }








//        ObjectAnimator.ofFloat(view.house, "translationX", 100f).apply {
//            duration = 2000
//            start()
//            addListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
//                    super.onAnimationEnd(animation, isReverse)
//                    // view.house.visibility = View.GONE
//
//                }
//            })
//        }


//        view.house.setOnClickListener {
//            ObjectAnimator.ofFloat(view.house, "alpha", 1f, 0f).apply {
//                duration = 250
//                addListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator) {
//                        Toast.makeText(view.context, "HELLO", Toast.LENGTH_LONG).show()
//                        //balls.remove((animation as ObjectAnimator).target)
//                    }
//                })
//            }
//        }


    }

    private fun handleTouch(image: ImageView) {
        mediaPlayer.start()
        collectItemTimer(image).start()
    }


    private fun collectItemTimer(v: View?): CountDownTimer {
        val timer = object : CountDownTimer(100, 100) {
            override fun onFinish() {
                v?.visibility = View.GONE
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTick(millisUntilFinished: Long) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        return timer
    }


}
