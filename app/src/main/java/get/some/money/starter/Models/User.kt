package get.some.money.starter.Models


data class User(
  var name: String = "",
  val score: Int = 0,
  val uuid: String = "",
  val coins: Int = 10,
  val experience: Int = 0,
  val completion: Int = 5,
  val levels: List<Long> = emptyList(),
  val multiplier: Int = 1,
  val specialLevels: Int = 0,
  val permanentDLC: Int = 0,
  val gameTickets: Int = 0,
  val items: List<Inventory> = emptyList()

)