package get.some.money.starter.Repositories

import com.google.firebase.firestore.FirebaseFirestore
import get.some.money.starter.Models.Level

class LevelRepository private constructor(){

  private val db = FirebaseFirestore.getInstance()

  companion object {
    val getInstance: LevelRepository by lazy { LevelRepository() }
  }
  fun save(level: Level) = db.collection("levels").document().set(level)
  fun getLevels() = db.collection("levels")
}