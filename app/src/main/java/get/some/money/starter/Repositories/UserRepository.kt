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
  fun getUsers() = db.collection("users")
  fun updateScore(score: Int, uuid: String) = db.collection("users").document(uuid).update("score", score)
  fun updateCoins(coins: Int, uuid: String) = db.collection("users").document(uuid).update("coins", coins)
  fun updateName(name: String, uuid: String) = db.collection("users").document(uuid).update("name", name)
  fun updateSpecialLevels(pass: Int, uuid: String) = db.collection("users").document(uuid).update("specialLevels", pass)
  fun updateMultiplier(multiplier: Int, uuid: String) = db.collection("users").document(uuid).update("multiplier", multiplier)
  fun levelComplete(uuid: String) = db.collection("users").document(uuid)
  fun addItemToInventory(uuid: String) = db.collection("users").document(uuid)
  fun equipItems(uuid: String) = db.collection("users").document(uuid).collection("equipped_items")
  fun getEquipedItems(uuid: String) = db.collection("users").document(uuid).collection("equipped_items")


}