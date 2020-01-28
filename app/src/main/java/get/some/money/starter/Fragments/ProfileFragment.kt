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

class ProfileFragment : Fragment(R.layout.fragment_profile) {
  lateinit var userViewModel: UserViewModel

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    userViewModel = ViewModelProviders.of(this)[UserViewModel::class.java]


    var knowledge: Int
    var coins: Int
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
        coins_profile.text = String.format("%s%d", "+", coins)
        score_profile.text = String.format("%s%d", "+", knowledge)
      }
      )
  }
}

