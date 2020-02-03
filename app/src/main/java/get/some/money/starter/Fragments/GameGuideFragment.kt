package get.some.money.starter.Fragments


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import get.some.money.starter.Adapters.IntroSliderAdapter
import get.some.money.starter.MainActivity
import get.some.money.starter.Models.IntroSlide
import get.some.money.starter.R
import kotlinx.android.synthetic.main.fragment_game_guide.*

class GameGuideFragment : Fragment(R.layout.fragment_game_guide) {

  private val introSliderAdapter = IntroSliderAdapter(
    listOf(
      IntroSlide(
        "Žaisti lengva",
        "Pakanka spustelėti ant paveikslėlio štai ir sudėti loginę seką.",
        R.drawable.chestopen
      ),
      IntroSlide(
        "Žaisti lengva",
        "Pakanka spustelėti ant paveikslėlio štai ir sudėti loginę seką.",
        R.drawable.chestclosed
      ),
      IntroSlide(
        "Žaisti lengva",
        "Pakanka spustelėti ant paveikslėlio štai ir sudėti loginę seką.",
        R.drawable.kepure
      )
    )
  )

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val slider = view.findViewById<ViewPager2>(R.id.introSLiderViewPager)
    slider.adapter = introSliderAdapter
    setupIndicators()
    setCurrentIndicator()
    introSLiderViewPager.registerOnPageChangeCallback(object  : ViewPager2.OnPageChangeCallback(){
      override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        setCurrentIndicator(position)
      }
    })
    buttonNext.setOnClickListener {
      if(introSLiderViewPager.currentItem + 1 < introSliderAdapter.itemCount){
        introSLiderViewPager.currentItem +=1
      }else{
        Intent(context, MainActivity::class.java).also {
          startActivity(it)
        }
      }
    }
    skipIntro.setOnClickListener{
      Intent(context, MainActivity::class.java).also {
        startActivity(it)
      }
    }
  }

  fun setupIndicators() {
    val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
    val layoutParams: LinearLayout.LayoutParams =
      LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    layoutParams.setMargins(8, 0, 8, 0)
    for (i in indicators.indices) {
      indicators[i] = ImageView(context)
      indicators[i].apply {
        this?.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.indicator_inactive))
        this?.layoutParams = layoutParams
      }
      indicatorsContainer.addView(indicators[i])
    }
  }

  fun setCurrentIndicator(index: Int = 0) {
    val childCount = indicatorsContainer.childCount
    for (i in 0 until childCount) {
      val imageView = indicatorsContainer[i] as ImageView
      if (i == index) {
        imageView.setImageDrawable(
          ContextCompat.getDrawable(requireContext(), R.drawable.indicator_active)
        )
      } else {
        imageView.setImageDrawable(
          ContextCompat.getDrawable(requireContext(), R.drawable.indicator_inactive)
        )
      }
    }
  }


}
