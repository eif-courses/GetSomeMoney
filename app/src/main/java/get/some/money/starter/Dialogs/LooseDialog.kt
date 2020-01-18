package get.some.money.starter.Dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import get.some.money.starter.R
import kotlinx.android.synthetic.main.loose_dialog.*

class LooseDialog : DialogFragment(){

  //var startTimer = false

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // Inflate the layout to use as dialog or embedded fragment
    return inflater.inflate(R.layout.loose_dialog, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    restart_level_image.setOnClickListener {
      this.dismiss()
      //startTimer = true
    }
    level_restart.setOnClickListener {
      this.dismiss()
    }

//    next_level_btn.setOnClickListener {
//      activity?.onBackPressed()
//      this.dismiss()
//    }
//
//    level_complete_img.setOnClickListener {
//      it.background
//      it.setBackgroundResource(R.drawable.chestopen)
//      it.isClickable = false
//    }
  }




}