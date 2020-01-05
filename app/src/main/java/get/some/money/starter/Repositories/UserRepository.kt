package get.some.money.starter.Repositories

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import get.some.money.starter.Models.User

class UserRepository{

  private val db = FirebaseFirestore.getInstance()

  fun saveUser(user: User): Task<Void> = db.collection("users").document(user.uuid).set(user)
  fun getUser(uuid: String) = db.collection("users").document(uuid)


}