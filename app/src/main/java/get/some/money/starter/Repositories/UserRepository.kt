package get.some.money.starter.Repositories

import com.google.firebase.firestore.FirebaseFirestore
import get.some.money.starter.Models.User

class UserRepository private constructor(){

  private val db = FirebaseFirestore.getInstance()
  companion object {
    val instance: UserRepository by lazy { UserRepository() }
  }
  fun saveUser(user: User) = db.collection("users").document(user.uuid).set(user)
  fun getUser(uuid: String) = db.collection("users").document(uuid)
  fun updateScore(score: Int, uuid: String) = db.collection("users").document(uuid).update("score", score)
  fun completedLevels(uuid: String) = db.collection("users").document(uuid)

}