package get.some.money.starter.Repositories

import androidx.lifecycle.MutableLiveData
import get.some.money.starter.Models.Item
import get.some.money.starter.R
import get.some.money.starter.Util.Language

class ShopRepository private constructor() {
  private val items = MutableLiveData<List<Item>>()

  companion object {
    val instance: ShopRepository by lazy { ShopRepository() }
  }

  //fun Int.toLocalizedString(): String = ChainMaster.getAppContext().resources.getString(this)

  fun loadItems(): MutableLiveData<List<Item>> {

    if (Language.getCurrentLanguage().equals("lt")) {
      val temp = listOf(
        Item(
          "Nemokama dovana",
          R.drawable.gift,
          0
        ),
        Item(
          "Atsitiktinis daiktas",
          R.drawable.random,
          1000
        ),
        Item(
          "Dvigubai monetų už prizus",
          R.drawable.daugiklis2,
          10000
        ),
        Item(
          "Trigubai monetų už prizus",
          R.drawable.daugiklis3,
          20000
        )
      )
      items.value = temp
    } else {
      val temp = listOf(
        Item(
          "Free gift",
          R.drawable.gift,
          0
        ),
        Item(
          "Random item",
          R.drawable.random,
          1000
        ),
        Item(
          "Coins multiplier x2",
          R.drawable.daugiklis2,
          10000
        ),
        Item(
          "Coins multiplier x3",
          R.drawable.daugiklis3,
          20000
        )
      )
      items.value = temp
    }

    return items
  }
}