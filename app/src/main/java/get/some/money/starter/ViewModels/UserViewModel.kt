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
  var equippedItems: MutableLiveData<Map<String, Inventory>> = MutableLiveData()

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
      Log.d(TAG, "Current users list: $user")
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


  fun levelComplete(uuid: String, id: Long) {
    repository.levelComplete(uuid).update("levels", FieldValue.arrayUnion(id))
  }

  fun addItemToInventory(uuid: String, inventory: Inventory) {
    repository.addItemToInventory(uuid).update("items", FieldValue.arrayUnion(inventory))
  }

  fun equipItems(uuid: String, inventory: Inventory) {
    when (inventory.type) {
      "CAP", "SHIRT", "BOOTS" -> repository.equipItems(uuid).update(
        "equipped.${inventory.type}",
        inventory
      )
    }
  }

  fun getEquippedItems(uuid: String): LiveData<Map<String, Inventory>> {

    repository.getEquippedItems(uuid).addSnapshotListener { value, e ->
      if (e != null) {
        Log.w(TAG, "Listen failed.", e)
        return@addSnapshotListener
      }

      val cities = ArrayList<String>()
      val map = HashMap<String, Inventory>()


      for (doc in value!!) {


        val item = doc.get("equipped.CAP", Inventory::class.java)
        println("QQQQQQQQQQQQQ"+item)

      }
      Log.d(TAG, "Current cites in CA: $cities")
    }


    return equippedItems
  }


}

