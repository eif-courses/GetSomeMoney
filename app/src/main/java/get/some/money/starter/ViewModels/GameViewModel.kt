package get.some.money.starter.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import get.some.money.starter.Models.GameObject
import get.some.money.starter.Repositories.GameRepository

class GameViewModel : ViewModel(){
  private val repository = GameRepository()
  fun loadGameObjects():LiveData<List<GameObject>> = repository.loadGameObjects()

}