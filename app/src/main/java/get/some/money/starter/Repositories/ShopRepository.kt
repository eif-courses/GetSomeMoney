package get.some.money.starter.Repositories

import androidx.lifecycle.MutableLiveData
import get.some.money.starter.Configuration.ChainMaster
import get.some.money.starter.Models.Item
import get.some.money.starter.R

class ShopRepository private constructor(){
    private val items = MutableLiveData<List<Item>>()
    companion object {
        val instance: ShopRepository by lazy { ShopRepository() }
    }

    fun Int.toLocalizedString(): String = ChainMaster.getAppContext().resources.getString(this)

    fun loadItems(): MutableLiveData<List<Item>> {

        val temp = listOf(
            Item(
                R.string.free_gift.toLocalizedString(),
                R.drawable.gift,
                0
            ),
            Item(
                R.string.random_item.toLocalizedString(),
                R.drawable.random,
                1000
            ),
            Item(
                R.string.multiplier2x.toLocalizedString(),
                R.drawable.daugiklis2,
                10000
            ),
            Item(
                R.string.multiplier3x.toLocalizedString(),
                R.drawable.daugiklis3,
                20000
            ),

            Item(
                R.string.special_levels.toLocalizedString(),
                R.drawable.feature,
                30000
            )
        )
        items.value = temp
        return items
    }
}