package get.some.money.starter.Fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.R
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(R.layout.fragment_profile) {
  lateinit var userViewModel: UserViewModel

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    userViewModel = ViewModelProviders.of(this)[UserViewModel::class.java]



    var knowledge = 0
    var coins = 0
    userViewModel.getEquipedItems(FirebaseAuth.getInstance().currentUser!!.uid)
      .observe(this, Observer {

       coins = 0
        knowledge = 0
        it.forEach { item ->
          when (item.type) {
            "CAP" -> {
              head_image_view.setImageResource(item.imageID)
            }
            "SHIRT" -> {
              shirt_imageview.setImageResource(item.imageID)
            }
            "JEANS" -> {
              jeans_imageView.setImageResource(item.imageID)
            }
            "GLASSES" -> {
              glasses_imageView.setImageResource(item.imageID)
            }
          }
          coins += item.extraCoins
          knowledge += item.knowledge
        }

        coins_profile.text = coins.toString()
        score_profile.text = knowledge.toString()


//      coins_multiplier.append(it.multiplier.toString())
//      special_levels.append(it.specialLevels.toString())
//      game_tickets.append(it.gameTickets.toString())
//      feature_content.append(it.permanentDLC.toString())
        // userNameProfileDetails.setText(it.name)
      }
      )
//    saveUserProfileDetails.setOnClickListener {
//      userViewModel.updateName(userNameProfileDetails.text.toString(), FirebaseAuth.getInstance().currentUser!!.uid)
//
//      Toast.makeText(context, getString(R.string.name_updated), Toast.LENGTH_LONG).show()
//
//      it.isClickable = false
//      it.visibility = View.GONE
//      userNameProfileDetails.visibility = View.GONE

  }
}

