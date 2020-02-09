package get.some.money.starter.Fragments


import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import get.some.money.starter.Adapters.IntroSliderAdapter
import get.some.money.starter.Models.IntroSlide
import get.some.money.starter.R
import get.some.money.starter.Util.Language
import get.some.money.starter.Util.SharedPreference
import kotlinx.android.synthetic.main.fragment_game_guide.*

class GameGuideFragment : Fragment(R.layout.fragment_game_guide) {


  private lateinit var introSliderAdapter: IntroSliderAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)










    if (Language.getCurrentLanguage().equals("lt")) {
      introSliderAdapter = IntroSliderAdapter(
        listOf(
          IntroSlide(
            "ŽAISTI LENGVA",
            "Vadovautis teiginiu arba pateiktu klausimu! Pvz. Spauskite ant paveikslėlių ir sudėkite seką, kad pradėtų groti muzika.",
            R.drawable.seq
          ),
          IntroSlide(
            "PRIZAI",
            "Prizai gaunami išsprendus užduotis arba peržiūrėjus vaizdo reklamą.",
            R.drawable.chestclosed
          ),
          IntroSlide(
            "PARDUOTUVĖ",
            "Parduotuvėje įsigyk daiktus, kurie suteiks galimybę gauti daugiau taškų ir monetų iš prizų.",
            R.drawable.shopfront
          ),
          IntroSlide(
            "APRANGA",
            "Parduotuvėje ar sprendžiant užduotis rastus daiktus gali užsidėti savo herojui profilio meniu punkte.",
            R.drawable.boy
          ),
          IntroSlide(
            "LYDERIŲ LENTELĖ",
            "Varžykis su kitais žaidėjais ir surinkt daugiau taškų, kad top lentelėje būtum pirmas!",
            R.drawable.prize
          ),
          IntroSlide(
            "PRIVATUMO POLITIKA",
            "Privalote susipažinti su privatumo politika: https://github.com/eif-courses/Logic-for-fun/blob/master/privatumo_politika.md. Ir sąlygomis bei nuostatomis: https://github.com/eif-courses/Logic-for-fun/blob/master/salygos_nuostatos.md. Programėlėje taip pat rasite šią informaciją.",
            R.drawable.terms
          ),
          IntroSlide(
            "REGISTRUOKIS",
            "Tik registruotas narys gauna prizus ir išsaugo žaidimo progresą. ",
            R.drawable.member
          )
        )
      )
    } else {
      introSliderAdapter = IntroSliderAdapter(
        listOf(
          IntroSlide(
            "EASY TO PLAY",
            "Answer simple question or follow instructions! For e.g. Click on images to make correct sequence to play music!",
            R.drawable.seq
          ),
          IntroSlide(
            "PRIZES",
            "You got prizes if you watch video ad or buy random item from shop or solve logic puzzles.",
            R.drawable.chestclosed
          ),
          IntroSlide(
            "SHOP",
            "In app shop you able to buy random item and get extra addition to your prizes more coins and score from prizes!",
            R.drawable.shopfront
          ),
          IntroSlide(
            "CLOTHES",
            "In app shop you able to buy random items. Items you earned able to equip to your character in profile.",
            R.drawable.boy
          ),
          IntroSlide(
            "HIGH SCORES",
            "Challenge with other players to get more coins and score points",
            R.drawable.prize
          ),
          IntroSlide(
            "PRIVACY POLICY",
            "You must read privacy policy: https://github.com/eif-courses/Logic-for-fun/blob/master/privacy_policy.md. Also you need read Terms and Conditions here: https://github.com/eif-courses/Logic-for-fun/blob/master/terms_and_conditions.md",
            R.drawable.terms
          ),
          IntroSlide(
            "REGISTER",
            "Only as registered user you start getting prizes and save game progress to database.",
            R.drawable.member
          )
        )
      )
    }


    val action = GameGuideFragmentDirections.actionGameGuideFragmentToHomeFragment()
    val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
      findNavController().navigate(action)
    }
    callback.isEnabled
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val slider = view.findViewById<ViewPager2>(R.id.introSLiderViewPager)
    slider.adapter = introSliderAdapter
    setupIndicators()
    setCurrentIndicator()
    introSLiderViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
      override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        setCurrentIndicator(position)
        if (introSLiderViewPager.currentItem != introSliderAdapter.itemCount - 1) {
          buttonNext.text = getString(R.string.next)
        } else {
          buttonNext.text = getString(R.string.sign_up)
        }
      }
    })

    val pref = SharedPreference(context!!)


    switch1.setOnClickListener {
      val preference = SharedPreference(context!!)
      preference.save("GUIDE2", true)
      it.isClickable = false
    }



    if (pref.getValueBoolien("GUIDE2", false)) {
      val action = GameGuideFragmentDirections.actionGameGuideFragmentToSiginInFragment()
      findNavController().navigate(action)
    }
    buttonNext.setOnClickListener {
      if (introSLiderViewPager.currentItem + 1 < introSliderAdapter.itemCount) {
        introSLiderViewPager.currentItem += 1
        if (introSLiderViewPager.currentItem == introSliderAdapter.itemCount - 1) {
          buttonNext.text = getString(R.string.sign_up)
        } else {
          buttonNext.text = getString(R.string.next)
        }
      } else {
        val action = GameGuideFragmentDirections.actionGameGuideFragmentToSiginInFragment()
        findNavController().navigate(action)
        //  val preference = SharedPreference(context!!)
        // preference.save("GUIDE2", true)
      }
    }
    skipIntro.setOnClickListener {
      val action = GameGuideFragmentDirections.actionGameGuideFragmentToSiginInFragment()
      findNavController().navigate(action)

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
