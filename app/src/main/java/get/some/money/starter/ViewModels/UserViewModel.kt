package get.some.money.starter.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QueryDocumentSnapshot
import get.some.money.starter.Models.User
import get.some.money.starter.Repositories.UserRepository
import java.util.*

class UserViewModel : ViewModel(){
  private val repository = UserRepository()
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


}

