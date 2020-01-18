package get.some.money.starter.Models

import com.google.firebase.Timestamp

data class Level(
  val assets: List<String> = emptyList(),
  val category: String = "",
  val categorylt: String = "",
  val header: String = "",
  val id: Long = 0,
  val name: String = "",
  val namelt: String = "",
  val question: String = "",
  val questionlt: String = "",
  val timestamp: Timestamp = Timestamp(java.util.Date())
)