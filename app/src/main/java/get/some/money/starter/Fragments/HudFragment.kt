package get.some.money.starter.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import get.some.money.starter.R
import get.some.money.starter.ViewModels.LevelViewModel
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_hud.*

/**
 * A simple [Fragment] subclass.
 */
class HudFragment : Fragment() {
  lateinit var userModel: UserViewModel
  lateinit var levelModel: LevelViewModel
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_hud, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    userModel = ViewModelProviders.of(this)[UserViewModel::class.java]
    levelModel = ViewModelProviders.of(this)[LevelViewModel::class.java]

    levelModel.getLevels().observe(this, Observer {
      textView3.text = it.size.toString()
    })


    userModel.getUser(FirebaseAuth.getInstance().currentUser!!.uid).observe(this, Observer {
      textView7.text = it.coins.toString()
      textView9.text = it.score.toString()
      textView11.text = it.levels.size.toString()
    })







  }


}
