package get.some.money.starter.Dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import get.some.money.starter.R

class RewardDialog : DialogFragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // Inflate the layout to use as dialog or embedded fragment
    return inflater.inflate(R.layout.reward_dialog, container, false)
  }
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog = super.onCreateDialog(savedInstanceState)
    dialog.setCancelable(false)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    return dialog
  }



}