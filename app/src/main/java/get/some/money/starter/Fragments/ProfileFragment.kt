package get.some.money.starter.Fragments


import android.os.Bundle
import android.view.View
import android.widget.Toast
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

    userViewModel.getUser(FirebaseAuth.getInstance().currentUser!!.uid).observe(this, Observer {

//      coins_multiplier.append(it.multiplier.toString())
//      special_levels.append(it.specialLevels.toString())
//      game_tickets.append(it.gameTickets.toString())
//      feature_content.append(it.permanentDLC.toString())
      userNameProfileDetails.setText(it.name)
      }
    )
    saveUserProfileDetails.setOnClickListener {
      userViewModel.updateName(userNameProfileDetails.text.toString(), FirebaseAuth.getInstance().currentUser!!.uid)

      Toast.makeText(context, getString(R.string.name_updated), Toast.LENGTH_LONG).show()

      it.isClickable = false
      it.visibility = View.GONE
      userNameProfileDetails.visibility = View.GONE

//      val timer = object : CountDownTimer(30000, 10000) {
//      override fun onFinish() {
//        it.visibility = View.VISIBLE
//        it.isClickable = true
////        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//      }
//
//      override fun onTick(millisUntilFinished: Long) {
//  //      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//      }
//
//    }
//
//      timer.start()
    }
  }

}