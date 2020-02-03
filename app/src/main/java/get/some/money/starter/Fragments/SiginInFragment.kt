package get.some.money.starter.Fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.Models.User
import get.some.money.starter.R
import get.some.money.starter.ViewModels.UserViewModel

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

    if (uuid == null) {
      startActivityForResult(
        AuthUI.getInstance()
          .createSignInIntentBuilder()
          .setAvailableProviders(providers)
          .build(),
        RC_SIGN_IN
      )
    } else {
      val action = SiginInFragmentDirections.actionSiginInFragmentToHomeFragment()
      findNavController().navigate(action)
    }
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

        val action = SiginInFragmentDirections.actionSiginInFragmentToHomeFragment()
        findNavController().navigate(action)

      } else {
        println(response?.error)
        // Sign in failed. If response is null the user canceled the
        // sign-in flow using the back button. Otherwise check
        // response.getError().getErrorCode() and handle the error.
        // ...
      }
    }
  }
}
