package get.some.money.starter.Repositories

import androidx.lifecycle.MutableLiveData
import get.some.money.starter.Models.Item

class ShopRepository {
   private val items = MutableLiveData<List<Item>>()

   fun loadItems(): MutableLiveData<List<Item>>{
       val temp = listOf(
           Item("Iki", "https://media.licdn.com/dms/image/C4D0BAQHTKs-y8576Xg/company-logo_200_200/0?e=2159024400&v=beta&t=hx1AfsbUYNRoopgwbgYhkFch1x7sJZOkAcG3Vry7qrg", 5),
           Item("Maxima", "https://www.vz.lt/apps/pbcsi.dll/storyimage/VZ/20190329/ARTICLE/190329538/AR/0/AR-190329538.jpg?exactW=800&AlignV=middle&imageversion=Horizontalus&lu=524", 200))
       items.value = temp
       return items
   }



}