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
                 R.string.multiplier2x.toLocalizedString(),
                "https://firebasestorage.googleapis.com/v0/b/getsomemoney-f79c8.appspot.com/o/shop%2Fdaugiklis2.png?alt=media&token=9bab5789-d4f4-419b-ab6d-8ed47834dcb8",
                100000
            ),
            Item(
                R.string.multiplier3x.toLocalizedString(),
                "https://firebasestorage.googleapis.com/v0/b/getsomemoney-f79c8.appspot.com/o/shop%2Fdaugiklis3.png?alt=media&token=4db277e7-8ca6-4c8f-aec5-92680806c91b",
                200000
            ),
            Item(
               R.string.free_gift.toLocalizedString(),
                "https://firebasestorage.googleapis.com/v0/b/getsomemoney-f79c8.appspot.com/o/shop%2Fgift.png?alt=media&token=5ceb7f83-b1ac-41d2-810d-5f1b55ca70b3",
                0
            ),
            Item(
                R.string.special_levels.toLocalizedString(),
                "https://firebasestorage.googleapis.com/v0/b/getsomemoney-f79c8.appspot.com/o/shop%2Ffeature.png?alt=media&token=383c52da-e4e8-4446-a12f-262312edc274",
                300000
            ),
            Item(
                R.string.random_item.toLocalizedString(),
                "https://firebasestorage.googleapis.com/v0/b/getsomemoney-f79c8.appspot.com/o/shop%2Fkeys.png?alt=media&token=3ba6b370-bc1c-4964-aad8-412f29e4896d",
                10000
            ),
            Item(
              R.string.game_ticket.toLocalizedString(),
                "https://firebasestorage.googleapis.com/v0/b/getsomemoney-f79c8.appspot.com/o/shop%2Ftickets.png?alt=media&token=2c995efe-a4e1-4ef0-9810-2880a97c80e2",
                1000
            )
        )
        items.value = temp
        return items
    }
}