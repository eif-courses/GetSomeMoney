package get.some.money.starter.Fragments


import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import get.some.money.starter.R
import get.some.money.starter.Util.SharedPreference
import kotlinx.android.synthetic.main.fragment_splash_screen.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * A simple [Fragment] subclass.
 */
class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen), CoroutineScope {
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + Job()
  private lateinit var mInterstitialAd: InterstitialAd
  private var time = 4000L


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
      Log.d("tag","back button pressed")    // Handle the back button event
    }
    callback.isEnabled
  }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    progressBar.isIndeterminate = true

//    val kf0 = Keyframe.ofFloat(0f, 0f)
//    val kf1 = Keyframe.ofFloat(.5f, 360f)
//    val kf2 = Keyframe.ofFloat(1f, 0f)
//    val pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2)
//    ObjectAnimator.ofPropertyValuesHolder(imageView18, pvhRotation).apply {
//      duration = 3000
//    }.start()
    val sharedPreference = SharedPreference(view.context)





    if(sharedPreference.getValueBoolien("FIRST_TIME", false)){
      time = 1000
    }


    val animator = ObjectAnimator.ofFloat(textView13, View.TRANSLATION_X, 50f)
    animator.repeatCount = 10
    animator.repeatMode = ObjectAnimator.REVERSE
    animator.start()

    ObjectAnimator.ofFloat(imageView18, View.TRANSLATION_Y, 400f).apply{
      duration = time
      this.doOnEnd {
        loading_back.setBackgroundResource(R.drawable.headligth)
      }
    }.start()





    MobileAds.initialize(context,
      "ca-app-pub-9087133931125731~3617917200")
    mInterstitialAd = InterstitialAd(context)
    mInterstitialAd.adUnitId = "ca-app-pub-9087133931125731/3426345517"
    mInterstitialAd.loadAd(AdRequest.Builder().build())

    launch {
      delay(time+200)
      withContext(Dispatchers.Main){
        //(activity as MainActivity)
//
        if (mInterstitialAd.isLoaded) {
          mInterstitialAd.show()
        } else {

          val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToGameGuideFragment()
          findNavController().navigate(action)

        }
          mInterstitialAd.adListener = object: AdListener() {
            override fun onAdLoaded() {
             // mInterstitialAd.show()
            }

            override fun onAdFailedToLoad(errorCode: Int) {
              // Code to be executed when an ad request fails.
              val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToGameGuideFragment()
              findNavController().navigate(action)
            }

            override fun onAdOpened() {
              // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
              // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
              // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
              // Code to be executed when the interstitial ad is closed.
              val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToGameGuideFragment()
              findNavController().navigate(action)
            }
          }



          sharedPreference.save("FIRST_TIME", true)


          //val action = LevelChooseFragmentDirections.actionLevelChooseFragmentToGameplayFragment(temp, level.name, level.question, level.namelt, level.questionlt, level.header, level.id, score, coins)
         // findNavController().navigate(action)
        }
      }
    }

  }

