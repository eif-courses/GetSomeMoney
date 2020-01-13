package get.some.money.starter.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import get.some.money.starter.Models.Level
import get.some.money.starter.Repositories.LevelRepository

class LevelViewModel : ViewModel(){

  private val tempList = MutableLiveData<List<Level>>()
  private val levelByCategry = MutableLiveData<List<Level>>()

  private val repository = LevelRepository.getInstance

  fun save(level: Level) = repository.save(level)

  fun getLevels(): LiveData<List<Level>> {
    repository.getLevels().get().addOnCompleteListener {
      val list = ArrayList<Level>()
      for (document in it.result!!) {
        val level = document.toObject(Level::class.java)
        list.add(level)
      }
      tempList.value = list
    }
    return tempList
  }

  fun getLevels(category: String): LiveData<List<Level>> {
    repository.getLevels().
      orderBy("timestamp").
      whereEqualTo("category", category).
      get().addOnCompleteListener {
      val list = ArrayList<Level>()
      for (document in it.result!!) {
        val level = document.toObject(Level::class.java)
        list.add(level)
      }
      levelByCategry.value = list
    }
    return levelByCategry
  }
}