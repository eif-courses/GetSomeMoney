package get.some.money.starter.Fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.Models.User
import get.some.money.starter.R
import get.some.money.starter.Util.Language
import get.some.money.starter.Util.SharedPreference
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_sigin_in.*

/**
 * A simple [Fragment] subclass.
 */
class SiginInFragment : Fragment(R.layout.fragment_sigin_in) {
  private val RC_SIGN_IN = 123
  val userModel: UserViewModel by viewModels()


  var uuid = FirebaseAuth.getInstance().currentUser?.uid
  val providers = arrayListOf(
    AuthUI.IdpConfig.EmailBuilder().build(),
    AuthUI.IdpConfig.GoogleBuilder().build()
  )

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    val sharedPreference = SharedPreference(view.context)

    if (sharedPreference.getValueString("COINS").equals("90000")) {


      var terms = "https://github.com/eif-courses/Logic-for-fun/blob/master/terms_and_conditions.md"
      var policy = "https://github.com/eif-courses/Logic-for-fun/blob/master/privacy_policy.md"
      if (Language.getCurrentLanguage().equals("lt")) {
        terms = "https://github.com/eif-courses/Logic-for-fun/blob/master/salygos_nuostatos.md"
        policy = "https://github.com/eif-courses/Logic-for-fun/blob/master/privatumo_politika.md"
      }

      startActivityForResult(
        AuthUI.getInstance()
          .createSignInIntentBuilder()
          .setAvailableProviders(providers)
          .setTosAndPrivacyPolicyUrls(
            terms,
            policy
          )
          .setLogo(R.mipmap.ic_launcher)
          .build(),
        RC_SIGN_IN
      )
    } else {
      val action = SiginInFragmentDirections.actionSiginInFragmentToHomeFragment()
      findNavController().navigate(action)
    }


    var count = 0
    var anim = imageView27.x
    imageView25.setOnClickListener {
      ViewCompat.animate(it)
        .y(imageView27.y)
        .x(anim)
        .duration = 1000

      anim += it.width + 20
      count++
    }
    imageView26.setOnClickListener {
      ViewCompat.animate(it)
        .y(imageView27.y)
        .x(anim)
        .duration = 1000
      count++
      if (count == 2) {
        imageView24.setBackgroundResource(R.drawable.light_on)
      }
      anim += it.width + 20
    }

    imageView27.setOnClickListener {

      if (count == 2) {
        val action = SiginInFragmentDirections.actionSiginInFragmentToHomeFragment()
        findNavController().navigate(action)
      }
    }
    imageView24.setOnClickListener {
      if (count == 2) {
        val action = SiginInFragmentDirections.actionSiginInFragmentToHomeFragment()
        findNavController().navigate(action)
      }
    }

    start_game_btn.setOnClickListener {
      val action = SiginInFragmentDirections.actionSiginInFragmentToHomeFragment()
      findNavController().navigate(action)
    }


//    if (uuid == null) {
//      startActivityForResult(
//        AuthUI.getInstance()
//          .createSignInIntentBuilder()
//          .setAvailableProviders(providers)
//          .build(),
//        RC_SIGN_IN
//      )
//    } else {
//      val action = SiginInFragmentDirections.actionSiginInFragmentToHomeFragment()
//      findNavController().navigate(action)
//    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == RC_SIGN_IN) {
      val response = IdpResponse.fromResultIntent(data)
      if (resultCode == Activity.RESULT_OK) {
        // Successfully signed in

        userModel.getUsers()
          .observe(this, Observer<List<User>> { profiles ->

            var exists = false

            for (i in profiles.indices) {
              if (profiles[i].uuid.equals(FirebaseAuth.getInstance().currentUser!!.uid)) {
                //Toast.makeText(this, "USERIS EGZISTUOJA JUNGIAMES", Toast.LENGTH_LONG).show()
                exists = true

              } else {
                println("Not exists!")
              }
            }
            if (!exists) {
              userModel.saveUser(
                User(
                  getString(R.string.startName),
                  0,
                  FirebaseAuth.getInstance().currentUser!!.uid,
                  0
                )
              )
            }

          })


      } else {
        println(response?.error)
        // Sign in failed. If response is null the user canceled the
        // sign-in flow using the back button. Otherwise check
        // response.getError().getErrorCode() and handle the error.
        // ...
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val action = SiginInFragmentDirections.actionSiginInFragmentToHomeFragment()
    val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
      findNavController().navigate(action)
    }
    callback.isEnabled
  }
}
