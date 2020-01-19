package get.some.money.starter.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import get.some.money.starter.Models.User
import get.some.money.starter.Repositories.UserRepository

class UserViewModel : ViewModel(){
  private val repository = UserRepository.instance
  var user : MutableLiveData<User> = MutableLiveData()

  fun saveUser(user: User) = repository.saveUser(user)

  fun getUser(uuid: String): LiveData<User> {

    repository.getUser(uuid).addSnapshotListener { documentSnapshot, e -> run{
      val u = documentSnapshot?.toObject(User::class.java)
      user.value = u
    }
    }
    return user
  }
  fun updateScore(score: Int, uuid: String) = repository.updateScore(score, uuid)

  fun completedLevels(uuid: String, id: Long) {
    repository.completedLevels(uuid).update("levels", FieldValue.arrayUnion(id))
  }


}

