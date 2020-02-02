package get.some.money.starter.Fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import get.some.money.starter.Adapters.GuideListAdapter
import get.some.money.starter.Models.Guide
import get.some.money.starter.R
import get.some.money.starter.Util.Language
import kotlinx.android.synthetic.main.fragment_game_guide.*

class GameGuideFragment : Fragment(R.layout.fragment_game_guide){
  lateinit var recyclerView: RecyclerView
  lateinit var guideListAdapter: GuideListAdapter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    recyclerView = game_guide_recycleview
    recyclerView.layoutManager = LinearLayoutManager(context)
    guideListAdapter = GuideListAdapter()


    if (!Language.getCurrentLanguage().equals("lt")) {

      val list = ArrayList<Guide>()

      list.add(Guide("Kaip pradėti žaisti?", "weaweawea eawea weaweawe awea weawe aweaweaewawe", R.drawable.kepure, "aweaweaweaweaweaweaweawesdasdadsaqwdqweqweqweqweqweqeq"))
      list.add(Guide("Kaip pradėti žaisti?", "weaweawea eawea weaweawe awea weawe aweaweaewawe", R.drawable.kepure, "aweaweaweaweaweaweaweawesdasdadsaqwdqweqweqweqweqweqeq"))
      list.add(Guide("Kaip pradėti žaisti?", "weaweawea eawea weaweawe awea weawe aweaweaewawe", R.drawable.kepure, "aweaweaweaweaweaweaweawesdasdadsaqwdqweqweqweqweqweqeq"))
      list.add(Guide("Kaip pradėti žaisti?", "weaweawea eawea weaweawe awea weawe aweaweaewawe", R.drawable.kepure, "aweaweaweaweaweaweaweawesdasdadsaqwdqweqweqweqweqweqeq"))
      list.add(Guide("Kaip pradėti žaisti?", "weaweawea eawea weaweawe awea weawe aweaweaewawe", R.drawable.kepure, "aweaweaweaweaweaweaweawesdasdadsaqwdqweqweqweqweqweqeq"))


      guideListAdapter.swapData(list)
    } else {
      val list = ArrayList<Guide>()

      list.add(Guide("Kaip pradėti žaisti?", "weaweawea eawea weaweawe awea weawe aweaweaewawe", R.drawable.kepure, "aweaweaweaweaweaweaweawesdasdadsaqwdqweqweqweqweqweqeq"))
      list.add(Guide("Kaip pradėti žaisti?", "weaweawea eawea weaweawe awea weawe aweaweaewawe", R.drawable.kepure, "aweaweaweaweaweaweaweawesdasdadsaqwdqweqweqweqweqweqeq"))
      list.add(Guide("Kaip pradėti žaisti?", "weaweawea eawea weaweawe awea weawe aweaweaewawe", R.drawable.kepure, "aweaweaweaweaweaweaweawesdasdadsaqwdqweqweqweqweqweqeq"))
      list.add(Guide("Kaip pradėti žaisti?", "weaweawea eawea weaweawe awea weawe aweaweaewawe", R.drawable.kepure, "aweaweaweaweaweaweaweawesdasdadsaqwdqweqweqweqweqweqeq"))
      list.add(Guide("Kaip pradėti žaisti?", "weaweawea eawea weaweawe awea weawe aweaweaewawe", R.drawable.kepure, "aweaweaweaweaweaweaweawesdasdadsaqwdqweqweqweqweqweqeq"))


      guideListAdapter.swapData(list)
    }

    recyclerView.adapter = guideListAdapter



  }



}
