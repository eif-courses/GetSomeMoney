package get.some.money.starter.Repositories

import androidx.lifecycle.MutableLiveData
import get.some.money.starter.Models.Item

class ShopRepository private constructor(){
    private val items = MutableLiveData<List<Item>>()
    companion object {
        val instance: ShopRepository by lazy { ShopRepository() }
    }
    fun loadItems(): MutableLiveData<List<Item>> {
        val temp = listOf(
            Item(
                "G3 Gaming",
                "https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/page/campaign/26214/26214-notebook-g-15-3579-169x149.png?fmt=jpg&amp;wid=124",
                1000
            ),
            Item(
                "G5 Gaming",
                "https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/page/campaign/26214/26214-notebook-g-15-5590-169x149.png?fmt=jpg&amp;wid=124",
                1500
            ),
            Item(
                "Free gift",
                "https://i1.wp.com/www.wtrf.com/wp-content/uploads/sites/25/2018/11/money-hundred-dollar-bills-cash_1519336993846_345031_ver1.0_35024789_ver1.0.jpg?resize=2560%2C1440&ssl=1",
                0
            ),
            Item(
                "Alienware m15",
                "https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/page/campaign/26214/26214-notebook-alienware-m15-169x149.png?fmt=jpg&amp;wid=124",
                1900
            ),
            Item(
                "G7 Gaming",
                "https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/page/campaign/26214/26214-notebook-g-15-7590-169x149.png?fmt=jpg&amp;wid=124",
                2000
            ),
            Item(
                "Alienware m15",
                "https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/page/campaign/26214/26214-notebook-alienware-m15-169x149.png?fmt=jpg&amp;wid=124",
                1500
            )
        )
        items.value = temp
        return items
    }


}