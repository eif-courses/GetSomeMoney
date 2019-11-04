package get.some.money.starter.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import get.some.money.starter.Models.Item
import get.some.money.starter.Repositories.ShopRepository

typealias Items = LiveData<List<Item>>

class ShopViewModel : ViewModel() {
    private val repository = ShopRepository()
    fun getItems(): Items = repository.loadItems()
}