package get.some.money.starter.Repositories

import androidx.lifecycle.MutableLiveData
import get.some.money.starter.Models.GameObject
import get.some.money.starter.Models.Item
import get.some.money.starter.R
import kotlin.random.Random



class GameRepository private constructor(){
  private val gameObjects = MutableLiveData<List<GameObject>>()

  companion object {
    val instance: GameRepository by lazy { GameRepository() }
  }


  fun loadGameObjects(): MutableLiveData<List<GameObject>> {
    val temp = listOf(
      GameObject("Star", R.drawable.star_02, Random.nextInt(300)),
      GameObject("Star", R.drawable.star_03, Random.nextInt(300)),
      GameObject("Star", R.drawable.star_04, Random.nextInt(140)),
      GameObject("Star", R.drawable.star_05, Random.nextInt(300)),
      GameObject("Star", R.drawable.star_06, Random.nextInt(200)),
      GameObject("Star", R.drawable.star_02, Random.nextInt(500)),
      GameObject("Star", R.drawable.star_03, Random.nextInt(900)),
      GameObject("Star", R.drawable.star_04, Random.nextInt(300)),
      GameObject("Star", R.drawable.star_05, Random.nextInt(400))
    )
    temp.shuffled()
    gameObjects.value = temp
    return gameObjects
  }
}
