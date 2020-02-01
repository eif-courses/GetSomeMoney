package get.some.money.starter.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import get.some.money.starter.Models.Inventory
import get.some.money.starter.Models.User
import get.some.money.starter.Repositories.UserRepository

class UserViewModel : ViewModel() {
  private val repository = UserRepository.instance
  var user: MutableLiveData<User> = MutableLiveData()
  var equippedItems: MutableLiveData<List<Inventory>> = MutableLiveData()

  val TAG = "GET_USERS"
  var currentUsers: MutableLiveData<List<User>> = MutableLiveData()


  fun saveUser(user: User) = repository.saveUser(user)

  fun getUser(uuid: String): LiveData<User> {

    repository.getUser(uuid).addSnapshotListener { value, e ->
      if (e != null) {
        Log.w(TAG, "Listen failed.", e)
        return@addSnapshotListener
      }
      user.value = value?.toObject(User::class.java)
      //Log.d(TAG, "Current users list: $user")
    }
    return user
  }

  fun getUsers(): LiveData<List<User>> {

    repository.getUsers().addSnapshotListener { value, e ->
      if (e != null) {
        Log.w(TAG, "Listen failed.", e)
        return@addSnapshotListener
      }
      val users = ArrayList<User>()
      for (doc in value!!) {
        users.add(doc.toObject(User::class.java))
      }
      currentUsers.value = users
      Log.d(TAG, "Current users list: $users")
    }
    return currentUsers
  }


  fun updateScore(score: Int, uuid: String) = repository.updateScore(score, uuid)
  fun updateCoins(coins: Int, uuid: String) = repository.updateCoins(coins, uuid)
  fun updateName(name: String, uuid: String) = repository.updateName(name, uuid)
  fun updateMultiplier(multiplier: Int, uuid: String) = repository.updateMultiplier(multiplier, uuid)
  fun updateSpecialLevels(pass: Int, uuid: String) = repository.updateSpecialLevels(pass, uuid)
  fun levelComplete(uuid: String, id: Long) {
    repository.levelComplete(uuid).update("levels", FieldValue.arrayUnion(id))
  }
  fun sellItemFromUser(uuid: String, index: Int, inventory: Inventory){
    repository.sellItemFromUser(uuid).update("items", FieldValue.arrayRemove(inventory))
  }

  fun addItemToInventory(uuid: String, inventory: Inventory) {
    repository.addItemToInventory(uuid).update("items", FieldValue.arrayUnion(inventory))
  }

  fun equipItems(uuid: String, inventory: Inventory) {
    when (inventory.type) {
      "CAP", "SHIRT", "JEANS", "GLASSES" -> repository.equipItems(uuid).document(inventory.type).set(
        inventory
      )
    }
  }

  fun getEquipedItems(uuid: String): LiveData<List<Inventory>> {

    repository.getEquipedItems(uuid).addSnapshotListener { value, e ->
      if (e != null) {
        Log.w(TAG, "Listen failed.", e)
        return@addSnapshotListener
      }
      val itemsEQuipped = ArrayList<Inventory>()
      for (doc in value!!) {
        itemsEQuipped.add(doc.toObject(Inventory::class.java))
      }
      equippedItems.value = itemsEQuipped
     // Log.d(TAG, "Current items EQUIPPED list: $itemsEQuipped")
    }
    return equippedItems
  }





}

