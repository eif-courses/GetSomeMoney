package get.some.money.starter.Util

import android.os.Build
import android.os.LocaleList
import java.util.*

class Language {
  companion object {
    fun getCurrentLanguage(): String? {
      return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        LocaleList.getDefault()[0].language
      } else {
        Locale.getDefault().getLanguage()
      }
    }
  }
}