package get.some.money.starter.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import get.some.money.starter.Models.IntroSlide
import get.some.money.starter.R

class IntroSliderAdapter(private val introSlides: List<IntroSlide>) :
  RecyclerView.Adapter<IntroSliderAdapter.IntroSliderViewHolder>() {
  inner class IntroSliderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val title = view.findViewById<TextView>(R.id.slide_item_title);
    private val description = view.findViewById<TextView>(R.id.slide_item_description);
    private val icon = view.findViewById<ImageView>(R.id.slide_item_image)

    fun bind(introSlide: IntroSlide) {
      title.text = introSlide.title
      description.text = introSlide.descroption
      icon.setImageResource(introSlide.icon)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSliderViewHolder {
    return IntroSliderViewHolder(
      LayoutInflater.from(parent.context).inflate(
        R.layout.slide_item_container,
        parent,
        false
      )
    )
  }

  override fun getItemCount(): Int {
    return introSlides.size
  }

  override fun onBindViewHolder(holder: IntroSliderViewHolder, position: Int) {
    holder.bind(introSlides[position])
  }
}