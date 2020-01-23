package get.some.money.starter.Fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.Models.User
import get.some.money.starter.R
import get.some.money.starter.ViewModels.LevelViewModel
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_hud.*

/**
 * A simple [Fragment] subclass.
 */
class HudFragment : Fragment(R.layout.fragment_hud) {
  lateinit var userModel: UserViewModel
  lateinit var levelModel: LevelViewModel

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    userModel = ViewModelProviders.of(this)[UserViewModel::class.java]
    levelModel = ViewModelProviders.of(this)[LevelViewModel::class.java]


    levelModel.getLevels().observe(this, Observer {
      textView3.text = it.size.toString()
    })

    userModel.getUsers()
      .observe(this, Observer<List<User>> { profiles ->
        for (i in profiles.indices) {
          if (profiles[i].uuid.equals(FirebaseAuth.getInstance().currentUser?.uid)) {
            textView7.text = profiles[i].coins.toString()
            textView9.text = profiles[i].score.toString()
            textView11.text = profiles[i].levels.size.toString()
          }
        }
      })

  }



}
